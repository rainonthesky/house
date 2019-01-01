package com.kiyozawa.houses.controller;


import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;


public class UserHelper {

    public static ResultMsg validate(User account){
        if(StringUtils.isBlank(account.getEmail())){
            return ResultMsg.errorMsg("Email 不能为空");
        }
        if(StringUtils.isBlank(account.getName())){
            return ResultMsg.errorMsg("用户名 不能为空");
        }
        if(StringUtils.isBlank(account.getPasswd())||StringUtils.isBlank(account.getConfirmPasswd())
                ||!account.getPasswd().equals(account.getConfirmPasswd())){
            return ResultMsg.errorMsg("用户名或密码错误");
        }
        if(account.getPasswd().length()<6){
            return ResultMsg.errorMsg("密码要大于6位数");
        }
        return ResultMsg.sucessMsg("");
    }
}
