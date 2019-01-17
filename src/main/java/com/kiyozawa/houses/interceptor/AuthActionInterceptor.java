package com.kiyozawa.houses.interceptor;
import com.kiyozawa.houses.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
@Component
public class AuthActionInterceptor implements HandlerInterceptor {

    //执行 handler之前 运行这个方法里面的代码
    //用户登录验证
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

       User user =UserContext.getUser();
       if(user==null){
           String msg = URLEncoder.encode("请现登录","utf-8");
           String target=URLEncoder.encode(request.getRequestURI().toString(),"utf-8");
           if ("GET".equalsIgnoreCase(request.getMethod())){
               response.sendRedirect("/accounts/signin?errorMsg=" + msg + "&target="+target);
               return false;//修复bug，未登录要返回false

           }else{
               response.sendRedirect("/accounts/signin?errorMsg="+msg);
               return false;//修复bug，未登录要返回false
           }
       }
       return true;

    }
    //在执行handler ,返回modelAndView之前 运行这个方法里面的代码
    //向页面提供一些公用的数据或者视图信息,
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView)throws Exception{

    }

    //执行handler之后 运行这个方法来面的代码
    //系统统一的异常处理,方法执行时间 :afterCompletion-preHandler
    //系统日志记录
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception exception)
            throws Exception {

    }
}
