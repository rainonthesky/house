package com.kiyozawa.houses;

import com.kiyozawa.houses.mapper.UserMapper;
import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.utils.HashUtils;
import com.kiyozawa.houses.utils.HashUtils1;
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


        User user=new User();
        user.setEmail("864740212@qq.com");
       user.setPasswd(HashUtils.encryPassword("123456"));
        System.out.print("查询用户个数："+user.getPasswd());



        List<User> list=userMapper.selectUsersByQuery(user);
        System.out.print("查询用户个数："+list.size()+"hahha");

    }


}

