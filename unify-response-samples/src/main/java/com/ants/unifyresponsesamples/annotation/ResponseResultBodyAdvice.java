package com.ants.unifyresponsesamples.annotation;

import com.ants.unifyresponsesamples.utils.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Slf4j
@RestControllerAdvice(basePackages = {"com.ants.unifyresponsesamples.controller"})
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {
	private static final Class<? extends Annotation> ANNOTATION_TYPE = ResponseResultBody.class;
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ANNOTATION_TYPE) || returnType.hasMethodAnnotation(ANNOTATION_TYPE)
				|| !returnType.getGenericParameterType().equals(Result.class);

	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		if (body instanceof Result) {
			return body;
		}
		if (body instanceof Resource) {
			return body;
		}
		if (body instanceof ResponseEntity){
			return body;
		}
		// String类型不能直接包装，所以要进行些特别的处理
		if (body instanceof String) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				// 将数据包装在Result里后，再转换为json字符串响应给前端
				return objectMapper.writeValueAsString(Result.success(body));
			} catch (JsonProcessingException e) {
				throw new IllegalArgumentException();
			}
		}
		return Result.success(body);
	}
}
