package com.kiyozawa.houses.controller;

import com.kiyozawa.houses.model.House;
import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;
import com.kiyozawa.houses.service.AgencyService;
import com.kiyozawa.houses.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/agency")
public class AgencyController {
    @Autowired
    private AgencyService  agencyService;
    @Autowired
    private HouseService houseService;
    @RequestMapping("/agentList")
    public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap){
        if(pageSize==null){
            pageSize = 6;
        }
        PageData<User> ps=agencyService.getAllAgent(PageParams.build(pageSize,pageNum));
        modelMap.put("ps",ps);
        return "/user/agent/agentList";
    }
    @RequestMapping("/agentDetail")
    public String agentDetail(Long id,ModelMap modelMap){
        User user =agencyService.getAgentDetail(id);
        House query =new House();
        query.setUserId(id);
        query.setBookmarked(false);
        PageData<House>bindHouse=houseService.queryHouse(query,new PageParams(3,1));
        if(bindHouse!=null){
           modelMap.put("bindHouses",bindHouse.getList());
        }
        modelMap.put("agent",user);
        modelMap.put("agencyName",user.getAgencyName());
        return "/user/agent/agentDetail";

    }


}
