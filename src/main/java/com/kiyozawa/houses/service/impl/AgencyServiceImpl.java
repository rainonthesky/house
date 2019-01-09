package com.kiyozawa.houses.service.impl;

import com.kiyozawa.houses.mapper.AgencyMapper;
import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.page.PageParams;
import com.kiyozawa.houses.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    private AgencyMapper agencyMapper;
    @Value("${file.prefix}")
    private String imgPref;

    /**
     * 访问user表的详情
     * 添加用户头像
     * @param agentId
     * @return
     */
    @Override
    public User getAgentDetail(Long agentId) {
        User user =new User();
        user.setId(agentId);
        user.setType(2);
        List<User> list=agencyMapper.selectAgent(user, PageParams.build(1,1));
        setImg(list);
        if (!list.isEmpty()){
            return  list.get(0);
        }
        return null;
    }
    private void setImg(List<User>list){
        list.forEach(i->{
            i.setAvatar(imgPref+i.getAvatar());
        });
    }
}
