package com.fit.config;

import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * @AUTO FreeMarker视图配置
 * @Author AIM
 * @DATE 2019/10/23
 */
@Configuration
public class FreeMarkerConfig {

    @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(false); // 关闭缓存
        resolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
        resolver.setRequestContextAttribute("request");
        resolver.setExposeRequestAttributes(true);
        //设置HttpServletRequest的attributes是否可以覆盖Controller的model 的attributes 默认是false
        resolver.setAllowRequestOverride(false);
        //是否公开一个由Spring的宏库使用RequestContext
        resolver.setExposeSpringMacroHelpers(true);
        //所有HttpSession的属性设置是否应该加入到模型之前，与模板合并 默认false
        resolver.setExposeSessionAttributes(true);
        resolver.setSuffix(".html"); // 视图后缀
        resolver.setContentType("text/html; charset=UTF-8");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPaths("classpath:/templates", "classpath:/ftl/", "src/main/resources/ftl");
        factory.setDefaultEncoding("UTF-8");
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();

        freemarker.template.Configuration configuration = factory.createConfiguration();
        configuration.setClassicCompatible(true);
        result.setConfiguration(configuration);
        Properties settings = new Properties();
        settings.put("template_update_delay", "0");
        settings.put("default_encoding", "UTF-8");
        settings.put("number_format", "0.##########");
        settings.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
        settings.put("classic_compatible", true);
        settings.put("template_exception_handler", "ignore");
        result.setFreemarkerSettings(settings);
        return result;
    }
}
