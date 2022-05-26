package com.ants.security.samples.handler;


import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lyy
 * @Deprecated
 * @date 2021/8/17
 */
public class ServletUtils {
	/**
	 * 渲染到客户端
	 *
	 * @param object 待渲染的实体类，会自动转为json
	 */
	public static void render(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
		// 允许跨域

		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setContentType("textml;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "0");
		response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
		response.setHeader("XDomainRequestAllowed", "1");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Credential", "true");
		response.setHeader("P3P", "CP=CAO PSA OUR");
		if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
			response.addHeader("Access-Control-Allow-Methods", "POST,GET,TRACE,OPTIONS");
			response.addHeader("Access-Control-Allow-Headers", "Content-Type,Origin,Accept");
			response.addHeader("Access-Control-Max-age", "120");

		}
		ObjectMapper mapper = new ObjectMapper();

         // Java object to JSON
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);


		response.getWriter().print(jsonString);
	}
}
