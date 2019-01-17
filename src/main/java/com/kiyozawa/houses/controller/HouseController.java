package com.kiyozawa.houses.controller;
import com.kiyozawa.houses.constants.CommonConstants;
import com.kiyozawa.houses.constants.HouseUserType;
import com.kiyozawa.houses.interceptor.UserContext;
import com.kiyozawa.houses.model.*;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;
import com.kiyozawa.houses.result.ResultMsg;
import com.kiyozawa.houses.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.management.resources.agent;

import java.util.List;

@Controller
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseService houseService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CommentService commentService;
    /**
     * 1.实现分页
     * 2.支持小区搜索、类型搜索
     * 3.支持排序
     * 4.支持展示图片、价格、标题、地址的信息
     */
    @RequestMapping("/list")
    public  String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap){
        PageData<House> ps =  houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
        List<House> hotHouses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", hotHouses);
        modelMap.put("ps", ps);
        modelMap.put("vo", query);
        return "house/listing";
        }

     @RequestMapping("/toAdd")
     public  String toAdd(ModelMap modelMap){
        modelMap.put("citys",cityService.getAllCitys());
        modelMap.put("communitys",houseService.getAllCommunitys());
         return "/house/add";
     }

    /**
     * 1.获取用户
     * 2。设置房产状态
     * @param
     * @return
     */
    @RequestMapping("/add")
     public String doAdd(House house){
        User user= UserContext.getUser();
        house.setState(CommonConstants.HOUSE_STATE_UP);
        houseService.addHouse(house,user);
        return "redirect:/house/ownlist";
     }

    @RequestMapping("/ownlist")
    public String ownlist(House house,Integer pageNum,Integer pageSize,ModelMap modelMap){
        User user = UserContext.getUser();
        house.setUserId(user.getId());
        house.setBookmarked(false);
        modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
        modelMap.put("pageType", "own");
        return "/house/ownlist";
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
        recommendService.increase(id);
        List<Comment> comments = commentService.getHouseComments(id,8);
        if(houseUser.getUserId()!=null&&!houseUser.getUserId().equals(0)){
            User agent =agencyService.getAgentDetail(houseUser.getUserId());
            modelMap.put("agent",agent);
        }
        List<House> rcHouses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", rcHouses);
        modelMap.put("house",house);
        modelMap.put("commentList", comments);
        return "/house/detail";
    }
    @RequestMapping("/leaveMsg")
    public String houseMsg(UserMsg userMsg){
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id="+userMsg.getHouseId();

    }
    //1.评分
    @ResponseBody
    @RequestMapping("/rating")
    public ResultMsg houseRate(Double rating,Long id){
        houseService.updateRating(id,rating);
        return ResultMsg.sucessMsg("ok");
    }
    //2.收藏
    @ResponseBody
    @RequestMapping("/bookmark")
    public ResultMsg bookmark(Long id){
        User user = UserContext.getUser();
        houseService.bindUser2House(id,user.getId(),true);
        return ResultMsg.sucessMsg("ok");
    }
    //3.删除收藏
    @ResponseBody
    @RequestMapping("/unbookmark")
    public ResultMsg unbookmark(Long id){
        User user=UserContext.getUser();
        houseService.unbindUser2House(id,user.getId(), HouseUserType.BOOKMARK);
        return ResultMsg.sucessMsg("ok");
    }
    //4. 删除
    @RequestMapping("/del")
    public String delsale(Long id,String pageType){
        User user = UserContext.getUser();
        houseService.unbindUser2House(id,user.getId(),pageType.equals("own")?HouseUserType.SALE:HouseUserType.BOOKMARK);
        return "redirect:/house/ownlist";
    }
    //4. 显示收藏
    @RequestMapping("/bookmarked")
    public String bookmarked(House house,Integer pageNum,Integer pageSize,ModelMap modelMap){
        User user = UserContext.getUser();
        house.setBookmarked(true);
        house.setUserId(user.getId());
        modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
        modelMap.put("pageType", "book");
        return "/house/ownlist";



    }




}
