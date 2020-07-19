package com.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

/**
 * 启动类里面使用 @EnableScheduling注解开启功能，自动扫描
 * @EnableScheduling 在配置类上使用，开启计划任务(定时任务)的支持
 * @SpringBootApplication  : 是Sprnig Boot项目的核心注解，目的是开启自动配置
 */
@EnableScheduling
@SpringBootApplication
public class Application {

    private  static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        Application.context = SpringApplication.run(Application.class,args);
    }

    /**
     *  被@PreDestroy修饰的方法会在服务器卸载Servlet的时候运行，并且只会被服务器调用一次，
     *  类似于Servlet的destroy()方法。被@PreDestroy修饰的方法会在destroy()方法之后运行，
     *  在Servlet被彻底卸载之前。
     */
    @PreDestroy
    public void close(){
        Application.context.close();
    }
}
