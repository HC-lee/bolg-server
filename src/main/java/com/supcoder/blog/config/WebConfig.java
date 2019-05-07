package com.supcoder.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/16:20:56
 * 邮箱：1981462002@qq.com
 * 说明：拿到你的图片
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //在F:/SpringBootFiles/Image/下如果有一张 Excalibar.jpg的图片，那么：
        //【1】访问：http://localhost:8080/imgs/Excalibar.jpg 可以访问到
        //【2】html 中 <img src="imgs/Excalibar.jpg">
        registry.addResourceHandler("/imgs/**").addResourceLocations("file:/Users/lee/Documents/JavaProgram/blog/file/imgs/");
    }

}
