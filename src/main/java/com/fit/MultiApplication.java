package com.fit;

import lombok.extern.slf4j.Slf4j;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Slf4j
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})//去除冲突
public class MultiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(MultiApplication.class, args);
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = getParam("POST", run.getEnvironment().getProperty("server.port"));

        log.info("\n---------------------------------------------------------\n" +
                "Application MultiAdmin is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "External:\thttp://" + ip + ":" + port + "/" +
                "\n-----------------页面请部署 admin-web----------------------");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MultiApplication.class);
    }

    private static String getParam(String param, String defVal) {
        Optional<String> envPort = Optional.ofNullable(System.getenv(param));
        return envPort.map(String::valueOf).orElse(defVal);
    }
}