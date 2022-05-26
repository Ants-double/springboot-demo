package com.ants.interceptor.samples.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Component
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(false);
		log.info("用户访问session request:{},session:{}", request.getRequestURI().toString(), request.getRequestedSessionId());

		if (session != null && session.getAttribute("user") != null) {
			return true;
		} else {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json;charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			String error = "not login";
			printWriter.print(String.valueOf(error));
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info(" 处理之后");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info("处理完在后");
	}
}
