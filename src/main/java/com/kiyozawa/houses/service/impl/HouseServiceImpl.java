package com.kiyozawa.houses.service.impl;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.kiyozawa.houses.mapper.AgencyMapper;
import com.kiyozawa.houses.mapper.HouseMapper;
import com.kiyozawa.houses.model.*;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;
import com.kiyozawa.houses.service.AgencyService;
import com.kiyozawa.houses.service.HouseService;
import com.kiyozawa.houses.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private MailService mailService;

    @Value("${file.prefix}")
    private String imgPrefix;

    /**
     * 1.查询小区
     * 2.添加图片服务器地址前缀
     * 3.构建分页结果
     * @param query 查询的结果
     * @param pageParams 返回页数
     */
    @Override
    public PageData<House> queryHouse(House query, PageParams pageParams) {
        List<House>houses= Lists.newArrayList();
        if(!Strings.isNullOrEmpty(query.getName())){
            Community community=new Community();
            community.setName(query.getName());
            List<Community>communityList=houseMapper.selectCommunity(community);
            if(!communityList.isEmpty()){
               query.setCommunityId(communityList.get(0).getId());
            }
        }
        houses =queryAndSetImg(query,pageParams);
        Long count=houseMapper.selectPageCount(query);
        return PageData.buildPage(houses,count,pageParams.getPageSize(),pageParams.getPageNum());
    }
    private List<House>queryAndSetImg(House query,PageParams pageParams){
      List<House> houseList=houseMapper.selectPageHouses(query,pageParams);
        houseList.forEach(h->{
            h.setFirstImg(imgPrefix+h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img->imgPrefix+img).collect(Collectors.toList()));
            h.setFloorPlanList(h.getFloorPlanList().stream().map(pic->imgPrefix+pic).collect(Collectors.toList()));

        });
        return houseList ;
    }
    @Override
    public House getOneHouse(Long id) {
        House house=new House();
        house.setId(id);
        List<House>houseList=queryAndSetImg(house,PageParams.build(1,1));
        if(!houseList.isEmpty()){
            return houseList.get(0);
        }
        return null;
    }

    @Override
    public void addUserMsg(UserMsg userMsg) {
        BeanHelper.onInsert(userMsg);
        houseMapper.insertUserMsg(userMsg);
        User agent=agencyService.getAgentDetail(userMsg.getAgentId());
        mailService.sendMail("来自用户"+userMsg.getEmail()+"的留言",userMsg.getMsg(),agent.getEmail());
    }

    @Override
    public HouseUser getHouseUser(Long houseId) {
        HouseUser houseUser=houseMapper.selectSaleHouseUser(houseId);
        return houseUser;
    }


}
