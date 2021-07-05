package com.ants.nap.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lyy
 * @Deprecated
 * @date 2020/6/8
 */
@WebFilter(filterName = "CorsFilter ")
@Configuration
public class CorsFilter implements Filter {
    public static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (req instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) req;
            logger.info(httpRequest.getRequestURI());
            // 遇到post方法才对request进行包装
            String methodType = httpRequest.getMethod();

            // 上传文件时同样不进行包装
            String servletPath = httpRequest.getRequestURI().toString();
            //if ("POST".equals(methodType) && !servletPath.contains("/material/upload")) {
                RequestWrapper requestWrapperTemp = new RequestWrapper((HttpServletRequest) req);
                requestWrapper = requestWrapperTemp;
                req.setAttribute("everise", requestWrapperTemp.getBody());
            if ("POST".equals(methodType))
            {
                req.setAttribute("everise", requestWrapperTemp.getBody());
            }
            else {
                req.setAttribute("everise", requestWrapperTemp.getQueryString()==null?"":requestWrapperTemp.getQueryString());
            }


        }


        HttpServletResponse response = (HttpServletResponse) res;
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        HttpServletRequest reqs = (HttpServletRequest) req;
        String curOrigin = reqs.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Origin", curOrigin == null ? "true" : curOrigin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//        if(!httpRequest.getRequestURI().contains("favicon.ico")){
//            filterChain.doFilter(req,res);
//            return;
//        }
        if (requestWrapper == null) {
            filterChain.doFilter(req, res);
        } else {
            filterChain.doFilter(requestWrapper, res);
        }

    }

    @Override
    public void destroy() {

    }
}
