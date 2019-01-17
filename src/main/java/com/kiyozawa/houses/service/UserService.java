package com.kiyozawa.houses.service;


import com.kiyozawa.houses.model.HouseUser;
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

    /**
     * 用户登录接口
     * @param username
     * @param password
     * @return
     */

    User auth(String username, String password);

    /**
     * 根据条件查询用户列表
     * @param user
     * @return
     */
    List<User> getUserByQuery(User user);

    /**
     * 更新用户信息
     * @param updateUser
     * @param email
     */
    void updateUser(User updateUser, String email);

    /**
     * 通过用户id查询用户
     * @param userId
     * @return
     */
    public User getUserById(Long userId);


}
