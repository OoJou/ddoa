package com.OoJou.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ch.qos.logback.classic.pattern.DateConverter;

@Configuration
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {

/**	在这里也可以配置单个文件大小和总文件大小，在properties也可以（properties只能写数字不能带单位，默认为byte，1mb=1024000字节）。
	一般两个都配置时，这里的配置优先起作用！
*/
//    @SuppressWarnings("deprecation") 
//	@Bean
//    public MultipartConfigElement multipartConfigElement(){
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //文件最大KB,MB
//        factory.setMaxFileSize("2MB");
//        //设置总上传数据总大小
//        factory.setMaxRequestSize("10MB");
//        return factory.createMultipartConfig();
//    }
	@Value("${img.location}")//@value注入properties里面配置的值。这里是本地路径
	String locationPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///"+locationPath);

        super.addResourceHandlers(registry);
    }
}
