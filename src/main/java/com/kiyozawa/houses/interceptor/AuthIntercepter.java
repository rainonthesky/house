package com.kiyozawa.houses.interceptor;

import com.google.common.base.Joiner;
import com.kiyozawa.houses.constants.CommonConstants;
import com.kiyozawa.houses.model.User;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 *HandlerInterceptor是由Spring提供的拦截器
 */
@Component
public class AuthIntercepter implements HandlerInterceptor {

    //执行 handler之前 运行这个方法里面的代码
    //用户登录验证
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        Map<String,String[]>map=request.getParameterMap();
        map.forEach((k,v)->{
            if(k.equals("errorMsg")||k.equals("successMsg")|| k.equals("target")){
                request.setAttribute(k, Joiner.on(",").join(v));
            }
        });
        String reqUri= request.getRequestURI();
        if(reqUri.startsWith("/static")||reqUri.startsWith("/error")){
            return true;
        }
        HttpSession session=request.getSession(true);
        User user=(User)session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        if(user!=null){
            UserContext.setUser(user);
        }
        return true;

    }
    //在执行handler ,返回modelAndView之前 运行这个方法里面的代码
    //向页面提供一些公用的数据或者视图信息,
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView)throws Exception{
        UserContext.remove();
    }

    //执行handler之后 运行这个方法来面的代码
    //系统统一的异常处理,方法执行时间 :afterCompletion-preHandler
    //系统日志记录
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception exception)
            throws Exception {
        UserContext.remove();

    }
}
