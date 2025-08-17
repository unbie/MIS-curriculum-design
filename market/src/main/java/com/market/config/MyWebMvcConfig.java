package com.market.config;

import com.market.utils.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor tokenInterceptor;

    // 解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                //.allowCredentials(true)
                .allowedMethods("*")
                //.allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                .maxAge(1800)
                .allowedOriginPatterns("*")
                .allowCredentials(true); //允许携带跨域凭证
        //registry.addExposedHeader("*");
        //.exposedHeaders("*");
    }


    //更改过后的方法
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                //允许直接访问的接口
                .excludePathPatterns(
                        //添加不会被拦截的接口
                        "/person/personLogin",
                        "/person/personRegister",
                        "/admin/adminLogin",
                        "/admin/checkToken",
                        "/images/upload/**",
                        "/carousel/upload",
                        "/good/upload",
                        "/captcha/**",
                        "/user/HomeOne",
                        "/near-sell/listCampusesWithNoToken"
                );
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
