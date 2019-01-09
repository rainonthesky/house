package com.kiyozawa.houses.controller;
import com.google.common.base.Objects;
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

    public static ResultMsg validateResetPassword(String key,String password,String confirmPassword){
        if(StringUtils.isBlank(key)||StringUtils.isBlank(password)||StringUtils.isBlank(confirmPassword)){
            return ResultMsg.errorMsg("参数有误");
        }
        if(!Objects.equal(password,confirmPassword)){
            return ResultMsg.errorMsg("密码必须与确认密码一致");
        }
        return ResultMsg.sucessMsg("");
    }
}
