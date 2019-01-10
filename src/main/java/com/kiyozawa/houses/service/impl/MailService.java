package com.kiyozawa.houses.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.kiyozawa.houses.mapper.UserMapper;
import com.kiyozawa.houses.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${domain.name}")
    private String domainName;
    @Value("${spring.mail.username}")
    private String from;


    @Autowired
    private UserMapper userMapper;
    private final Cache<String,String> registerCache= CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, String>() ).build(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    String email =notification.getValue();
                    User user=new User();
                    user.setEmail(email);
                    List<User>targetUser=userMapper.selectUsersByQuery(user);
                    if(!targetUser.isEmpty()|| Objects.equals(targetUser.get(0).getEnable(),0)){
                        userMapper.delete(email);// 代码优化: 在删除前首先判断用户是否已经被激活，对于未激活的用户进行移除操作
                    }
                }
            });
        @Async
        public void sendMail(String title,String url,String email){
            SimpleMailMessage simpleMailMessage =new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setText(url);
            mailSender.send(simpleMailMessage);
        }

        public boolean enable(String key){
            //获取缓存中key对应的value，如果缓存没命中，返回null
            String email =registerCache.getIfPresent(key);
            //判断验证码是否存在
           if(StringUtils.isBlank(email)) {
               return false;
           }
           User updateUser=new User();
           updateUser.setEmail(email);
            updateUser.setEnable(1);
            userMapper.update(updateUser);
            //删除缓存
            registerCache.invalidate(key);
            return true;
        }

    /**
     * 缓存key——email的关系，
     * 2.借助Spring mail发送邮件
     * 3.借助异步框架进行异步操作
     * @param email
     */
    @Async
    public void registerNotify(String email){
            //生成指定长度的字母和数字的随机组合字符串
            String randomKey= RandomStringUtils.randomAlphabetic(10);
            registerCache.put(randomKey,email);
            String url="http://"+domainName+"/accounts/verify?key="+randomKey;
            sendMail("房产平台激活邮件",url,email);
        }

}
