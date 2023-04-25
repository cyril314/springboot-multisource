package com.fit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 错误异常拦截处理
 */
@Slf4j
@Configuration
public class ExceptionConfig implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());    //返回json
        String exInfo = ex.toString().replaceAll("\n", "<br/>");

        boolean status = exInfo.contains("Subject does not have permission");

        if (status) {
            exInfo = "[没有此页面的访问权限]" + exInfo;
        } else {
            log.info("==============异常开始=============");
            ex.printStackTrace();
            log.info("==============异常结束=============");
        }
        mv.addObject("exception", exInfo);
        mv.addObject("result", "exception");

        return mv;
    }
}
