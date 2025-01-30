package com.dolphin.wechatserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/wechat/resources/avatar/**",
                        "/wechat/resources/chatBkg/**",
                        "/wechat/resources/momentBkg/**",
                        "/wechat/resources/moments/**")
                .addResourceLocations("file:/E:/MyProject/WeChat/resources/avatar/",
                        "file:/E:/MyProject/WeChat/resources/chatBkg/",
                        "file:/E:/MyProject/WeChat/resources/momentBkg/",
                        "file:/E:/MyProject/WeChat/resources/moments/");
    }
}
