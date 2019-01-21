package com.kiyozawa.houses.controller;

import com.kiyozawa.houses.constants.CommonConstants;
import com.kiyozawa.houses.interceptor.UserContext;
import com.kiyozawa.houses.model.Agency;
import com.kiyozawa.houses.model.House;
import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;
import com.kiyozawa.houses.result.ResultMsg;
import com.kiyozawa.houses.service.AgencyService;
import com.kiyozawa.houses.service.HouseService;
import com.kiyozawa.houses.service.RecommendService;
import com.kiyozawa.houses.service.impl.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/agency")
public class AgencyController {
    @Autowired
    private AgencyService  agencyService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private MailService mailService;
    @RequestMapping("/agentList")
    public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap){
        if(pageSize==null){
            pageSize = 20;
        }
        List<House> houses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        PageData<User> ps=agencyService.getAllAgent(PageParams.build(pageSize,pageNum));
        modelMap.put("recomHouses", houses);
        modelMap.put("ps",ps);
        return "/user/agent/agentList";
    }
    @RequestMapping("/agentDetail")
    public String agentDetail(Long id,ModelMap modelMap){
        User user =agencyService.getAgentDetail(id);
        List<House> houses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        House query =new House();
        query.setUserId(id);
        query.setBookmarked(false);
        PageData<House>bindHouse=houseService.queryHouse(query,new PageParams(3,1));
        if(bindHouse!=null){
           modelMap.put("bindHouses",bindHouse.getList());
        }
        modelMap.put("agent",user);
        modelMap.put("recomHouses", houses);
        modelMap.put("agencyName",user.getAgencyName());
        return "/user/agent/agentDetail";

    }

    /**
     * 留言功能
     * @param id
     * @param msg
     * @param name
     * @param email
     * @param modelMap
     * @return
     */
    @RequestMapping("/agentMsg")
    public String agentMsg(Long id,String msg,String name,String email,ModelMap modelMap){
        User user =agencyService.getAgentDetail(id);
        mailService.sendMail("咨询","name："+name+",email:"+email+",message："+msg,user.getEmail());
        return "redirect:/agency/agentDetail?id="+id+"&"+ ResultMsg.sucessMsg("留言成功").asUrlParams();
    }

    /**
     *获取经纪机构的列表
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String agencyList(ModelMap modelMap){
        List<Agency>agencies=agencyService.getAllAgency();
        List<House>houses=recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", houses);
        modelMap.put("agencyList", agencies);
        return "/user/agency/agencyList";
    }

    /**
     * 获取某个经纪机构的详情信息
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/agencyDetail")
    public String agencyDetail(Integer id,ModelMap modelMap){
        Agency  agency=agencyService.getAgency(id);
        List<House>houses=recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", houses);
        modelMap.put("agency",agency);
        return "/user/agency/agencyDetail";
    }
    @RequestMapping("/submit")
    public String agnencySubmit(Agency agency){
        User user= UserContext.getUser();
        if(user==null&&!Objects.equals(user.getEmail(),"864740212@qq.com")){
            return "redirect:/accounts/signin?" + ResultMsg.sucessMsg("请先登录").asUrlParams();
        }
        agencyService.add(agency);
        return "redirect:/index?"+ResultMsg.sucessMsg("创建成功").asUrlParams();
    }
    @RequestMapping("/create")
    public String agencyCreate(){
        User user =  UserContext.getUser();
        if (user == null || !Objects.equals(user.getEmail(), "864740212@qq.com")) {
            return "redirect:/accounts/signin?" + ResultMsg.sucessMsg("请先登录").asUrlParams();
        }
        return "/user/agency/create";
    }


}
