package com.kiyozawa.houses.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
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
        registry
                .addInterceptor(authActionInterceptor).addPathPatterns("/accounts/profile")
                .addPathPatterns("/house/bookmarked").addPathPatterns("/house/del")
                .addPathPatterns("/house/ownlist").addPathPatterns("/house/add")
                .addPathPatterns("/house/toAdd").addPathPatterns("/agency/agentMsg")
                .addPathPatterns("/comment/leaveComment").addPathPatterns("/comment/leaveBlogComment");
        super.addInterceptors(registry);
    }
}
