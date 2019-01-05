package com.kiyozawa.houses.controller;


import com.kiyozawa.houses.constants.CommonConstants;
import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.result.ResultMsg;
import com.kiyozawa.houses.service.UserService;
import com.kiyozawa.houses.utils.HashUtils;
import com.kiyozawa.houses.utils.HashUtils1;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

// ---------------------个人信息页-------------------------
    /**
     * 1.能够提供页面信息 2.更新用户信息
     *
     * @param updateUser
     * @param modelMap
     * @return
     */

    @RequestMapping("accounts/profile")
    public String profile(HttpServletRequest request,User updateUser,ModelMap modelMap) {
        if (updateUser.getEmail() == null) {
            return "/user/accounts/profile";
        }
        userService.updateUser(updateUser, updateUser.getEmail());
        User query = new User();
        query.setEmail(updateUser.getEmail());
        List<User> users = userService.getUserByQuery(query);
        request.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, users.get(0));
        return "redirect:/accounts/profile?" + ResultMsg.sucessMsg("更新成功").asUrlParams();
    }
    @RequestMapping("accounts/changePassword")
    public String changePassword(String email,String password,String newPassword,
                                 String confirmPassword,ModelMap model){
        User user=userService.auth(email,password);
        if(user==null||!confirmPassword.equals(newPassword)){
            return "redirect:/accounts/profile?" + ResultMsg.sucessMsg("密码错误").asUrlParams();
        }
        User updateUser=new User();
        updateUser.setPasswd(HashUtils.encryPassword(newPassword));
        userService.updateUser(updateUser,email);
        return "redirect:/accounts/profile?" + ResultMsg.sucessMsg("更新成功").asUrlParams();
    }




    // ----------------------------登录流程------------------------------------


    /**
     * 登录接口
     * @param req
     * @return
     */
    @RequestMapping("/accounts/signin")
    public String signin(HttpServletRequest req){
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String target=req.getParameter("target");
        if(username==null||password==null){
            req.setAttribute("target",target);
            return "user/accounts/signin";
        }
        User user=userService.auth(username,password);
        if(user==null){
            return "redirect:/accounts/signin?" + "target=" + target + "&username=" + username + "&"
                    + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }else{
            HttpSession session=req.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
            return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
        }

    }
    @RequestMapping("/accounts/logout")
    public String logout(HttpServletRequest request){
        HttpSession session=request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }

    @RequestMapping("/getUserList")
    public List<User> getUserList(){
        return userService.getUserList();
    }

}





