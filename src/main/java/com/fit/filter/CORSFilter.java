package com.fit.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域访问处理
 */
@Component
public class CORSFilter implements Filter {

    private String ALLOW_HEADERS = "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified," +
            " Cache-Control, Expires, Content-Type, X-E4M-With,userId,token";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", ALLOW_HEADERS);
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 是否支持cookie跨域        
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
