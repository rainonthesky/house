package com.kiyozawa.houses.service;


import com.kiyozawa.houses.model.User;

import java.util.List;

public interface UserService {
    /**
     * 获取所有的user对象
     * @return
     */

    List<User> getUserList();

    /**
     * 增加用户，非激活是用加盐Md5
     * 生成key，绑定email
     * 发送邮件给用户
     * @param account
     * @return
     */
    public boolean addAccount(User account);

    /**
     * 判断是否注册成功
     * @param key
     * @return
     */
    public boolean enable(String key);
}
