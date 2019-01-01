package com.kiyozawa.houses.service.impl;


import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.kiyozawa.houses.mapper.UserMapper;
import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.service.UserService;
import com.kiyozawa.houses.utils.BeanHelper;
import com.kiyozawa.houses.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class userServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;
    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        account.setPasswd(HashUtils.encryPassword(account.getNewPassword()));
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

}
