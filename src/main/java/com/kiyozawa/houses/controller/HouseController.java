package com.kiyozawa.houses.controller;
import com.kiyozawa.houses.model.House;
import com.kiyozawa.houses.model.HouseUser;
import com.kiyozawa.houses.model.UserMsg;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;
import com.kiyozawa.houses.service.AgencyService;
import com.kiyozawa.houses.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseService houseService;
    @Autowired
    private AgencyService agencyService;
    /**
     * 1.实现分页
     * 2.支持小区搜索、类型搜索
     * 3.支持排序
     * 4.支持展示图片、价格、标题、地址的信息
     */
    @RequestMapping("/list")
    public  String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap){
            PageData<House> ps =  houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
            modelMap.put("ps", ps);
            modelMap.put("vo", query);
            return "house/listing";
        }

    /**
     * 查询房屋详情
     * 查询关联经纪人
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    public String houseDetail(Long id,ModelMap modelMap){
        House house=houseService.getOneHouse(id);
        HouseUser houseUser=houseService.getHouseUser(id);
        if(houseUser.getUserId()!=null&&!houseUser.getUserId().equals(0)){
            modelMap.put("agent",agencyService.getAgentDetail(house.getUserId()));
        }
        modelMap.put("house",house);
        return "/house/detail";
    }
    @RequestMapping("/leaveMsg")
    public String houseMsg(UserMsg userMsg){
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id="+userMsg.getHouseId();

    }




}
