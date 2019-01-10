package com.kiyozawa.houses;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.kiyozawa.houses.mapper.UserMapper;
import com.kiyozawa.houses.model.User;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HousesApplicationTests {
    @Autowired
    private HttpClient httpClient;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testHttclient() throws ParseException, ClientProtocolException, IOException {
        //访问百度，输出百度网页
        System.out.println(EntityUtils.toString(httpClient.execute(new HttpGet("http://www.baidu.com")).getEntity()));
    }


    @Test
    public void testUser() {
         Cache<String,String> registerCache= CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES)
                .removalListener(new RemovalListener<String, String>() {

                }).build();
    }
//    @Override
//    public void onRemoval(RemovalNotification<String, String> notification) {
//        String email =notification.getValue();
//        System.out.println(111+email);
////                        User user=new User();
//                        user.setEmail(email);
//                        List<User>targetUser=userMapper.selectUsersByQuery(user);
//                        if(!targetUser.isEmpty()|| Objects.equals(targetUser.get(0).getEnable(),0)){
//                            userMapper.delete(email);// 代码优化: 在删除前首先判断用户是否已经被激活，对于未激活的用户进行移除操作
//                        }
    }



}

