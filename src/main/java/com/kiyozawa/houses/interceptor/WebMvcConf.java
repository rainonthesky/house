package com.kiyozawa.houses.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebMvcConf extends WebMvcConfigurerAdapter {
    @Autowired
    private  AuthActionInterceptor authActionInterceptor;
    @Autowired
    private AuthIntercepter authIntercepter;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //拦截所有的请求
        registry.addInterceptor(authIntercepter).excludePathPatterns("/static").addPathPatterns("/**");
        //拦截需要登录的请求
        registry.addInterceptor(authActionInterceptor).addPathPatterns("/accounts/profile");
        super.addInterceptors(registry);
    }
}
