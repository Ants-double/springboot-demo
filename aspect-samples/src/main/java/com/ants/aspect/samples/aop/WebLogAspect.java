package com.ants.aspect.samples.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Aspect
@Component
@Slf4j
@Profile({"dev", "test","prod"})
public class WebLogAspect {
	/**
	 * 换行符
	 */
	private static final String LINE_SEPARATOR = System.lineSeparator();
	/**
	 * 以自定义 @WebLog 注解为切点
	 */
	@Pointcut("@annotation(com.ants.aspect.samples.aop.WebLog)")
	public void webLog() {
	}
	/**
	 * 在切点之前织入
	 *
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 开始打印请求日志
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 获取 @WebLog 注解的描述信息
		String methodDescription = getAspectLogDescription(joinPoint);

		// 打印请求相关参数
		log.info("========================================== Start ==========================================");
		// 打印请求 url
		log.info("URL            : {}", request.getRequestURL().toString());
		// 打印描述信息
		log.info("Description    : {}", methodDescription);
		// 打印 Http method
		log.info("HTTP Method    : {}", request.getMethod());
		// 打印调用 controller 的全路径以及执行方法
		log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
		// 打印请求的 IP
		log.info("IP             : {}", request.getRemoteAddr());
		// 打印请求入参
		StringBuilder stringBuilder=new StringBuilder();
		request.getParameterMap().entrySet().forEach(x->{

			stringBuilder.append(x.getKey()+":"+ Arrays.toString( x.getValue()));
		});
		log.info(stringBuilder.toString());
		log.info("Request Args   : {}",stringBuilder.toString() );
	}
	/**
	 * 在切点之后织入
	 *
	 * @throws Throwable
	 */
	@After("webLog()")
	public void doAfter() throws Throwable {
		// 接口结束后换行，方便分割查看
		log.info("=========================================== End ===========================================" + LINE_SEPARATOR);
	}
	/**
	 * 环绕
	 *
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("webLog()")
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		ObjectMapper mapper = new ObjectMapper();
		// 用于序列化Java8新的时间类型
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDateTime.class,
				new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
		javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
		mapper.registerModule(javaTimeModule);
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
		// 打印出参
		log.info("Response Args  : {}", json);
		// 执行耗时
		log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
		return result;
	}

	/**
	 * 获取切面注解的描述
	 *
	 * @param joinPoint 切点
	 * @return 描述信息
	 * @throws Exception
	 */
	public String getAspectLogDescription(JoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		StringBuilder description = new StringBuilder("");
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description.append(method.getAnnotation(WebLog.class).description());
					break;
				}
			}
		}
		return description.toString();
	}
}
