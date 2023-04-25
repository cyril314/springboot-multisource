package com.fit.config;

import com.fit.util.DbFH;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @AUTO web 配置类
 * @Author AIM
 * @DATE 2019/10/23
 */
@Configuration
public class WebDefaultConfigurer implements WebMvcConfigurer {

    @Value("${spring.resource.static-locations}")
    private String staticLocations;

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //本应用
        registry.addResourceHandler("/**").addResourceLocations(staticLocations.split(","));
    }

    /**
     * 访问根路径默认跳转 index.html页面 （简化部署方案： 可以把前端打包直接放到项目的 webapp，上面的配置）
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login.html");
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> containerCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {

            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                //启动时会执行这里
                reductionDbBackupQuartzState();//
            }

            /**
             * 服务重启时，所有定时备份状态关闭
             */
            public void reductionDbBackupQuartzState() {
                try {
                    DbFH.executeUpdateFH("update DB_TIMINGBACKUP set STATUS = '2'");
                } catch (Exception e) {
                    System.out.println("==============数据库备份异常, 检查配置文件=============");
                }
            }
        };
    }
}
