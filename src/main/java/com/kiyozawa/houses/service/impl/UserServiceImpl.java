package com.kiyozawa.houses.service.impl;


import com.google.common.collect.Lists;
import com.kiyozawa.houses.mapper.UserMapper;
import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.service.UserService;
import com.kiyozawa.houses.utils.BeanHelper;
import com.kiyozawa.houses.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static  final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @Value("${file.prefix}")
    private String imgPrefix;
    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
        logger.info(account.getPasswd());
       List<String> imgList= fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
       if(!imgList.isEmpty()){
           account.setAvatar(imgList.get(0));
       }
        BeanHelper.setDefaultProp(account,User.class);
        BeanHelper.onInsert(account);
        account.setEnable(0);
        userMapper.insertUser(account);
        mailService.registerNotify(account.getEmail());
        return true;
    }
    public  boolean enable(String key){
       return mailService.enable(key);
    }


    @Override
    public User auth(String username, String password) {

        User user=new User();
        user.setEmail(username);
        user.setPasswd(HashUtils.encryPassword(password));
        user.setEnable(1);
        logger.info(user.getPasswd());
        List<User>userList= getUserByQuery(user);
        if(!userList.isEmpty()){
            return userList.get(0);
        }
        return null;
    }
    public List<User>getUserByQuery(User user){
        List<User>list=userMapper.selectUsersByQuery(user);
        list.forEach(u ->{
            u.setAvatar(imgPrefix+u.getAvatar());
        });
        return list;
    }

    @Override
    public void updateUser(User updateUser, String email) {
        updateUser.setEmail(email);
        BeanHelper.onUpdate(updateUser);
        userMapper.update(updateUser);

    }

}
