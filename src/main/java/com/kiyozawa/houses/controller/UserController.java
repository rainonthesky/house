package com.kiyozawa.houses.controller;


import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.result.ResultMsg;
import com.kiyozawa.houses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/accounts/register")
    public String accountsRegister(User account, ModelMap modelMap){
        if(account==null || account.getName()==null){
            return "user/accounts/register";
        }
        //用户验证信息
        ResultMsg resultMsg=UserHelper.validate(account);
    if(resultMsg.isSucess()&&userService.addAccount(account)){
        modelMap.put("email",account.getEmail());
        return "user/accounts/registerSubmit";
    }else{
        return "redirect:accounts/register?" + resultMsg.asUrlParams();
    }
    }

    @RequestMapping("accounts/verify")
    public String verify(String key){
        boolean result=userService.enable(key);
        if(result){
            return "redirect:/index?"+ResultMsg.sucessMsg("激活成功").asUrlParams();
        }else {
            return "redirect:/accounts/register?"+ResultMsg.errorMsg("激活失败，请确认链接是否过期");
        }

    }

    @RequestMapping("/getUserList")
    public List<User> getUserList(){
        return userService.getUserList();
    }

}
