package com.kiyozawa.houses.controller;


import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class HelloController {
    @Autowired
    private UserService userService;
    @RequestMapping("/hello")
    public ModelAndView hello(ModelAndView modelAndView){
         List<User> userList=userService.getUserList();
        User user=userList.get(0);
        if(user!=null){
            throw new IllegalArgumentException();

        }
        modelAndView.addObject("user",user);
        modelAndView.setViewName("hello");
        return modelAndView;
    }

    @RequestMapping("/index")
    public String index(){
            return "homepage/index";
    }
}
