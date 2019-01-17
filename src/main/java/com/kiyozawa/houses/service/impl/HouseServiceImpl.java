package com.kiyozawa.houses.service.impl;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.kiyozawa.houses.constants.HouseUserType;
import com.kiyozawa.houses.mapper.AgencyMapper;
import com.kiyozawa.houses.mapper.HouseMapper;
import com.kiyozawa.houses.model.*;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;
import com.kiyozawa.houses.service.AgencyService;
import com.kiyozawa.houses.service.HouseService;
import com.kiyozawa.houses.utils.BeanHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseServiceImpl implements HouseService {
    private static  final Logger logger= LoggerFactory.getLogger(RecommendServiceImpl.class);
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private MailService mailService;
    @Autowired
    private FileService fileService;

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
    public List<House>queryAndSetImg(House query,PageParams pageParams){
      List<House> houses=houseMapper.selectPageHouses(query,pageParams);
        houses.forEach(h->{
            h.setFirstImg(imgPrefix+h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img->imgPrefix+img).collect(Collectors.toList()));
            h.setFloorPlanList(h.getFloorPlanList().stream().map(pic->imgPrefix+pic).collect(Collectors.toList()));

        });
        return houses ;
    }

    @Override
    public List<Community> getAllCommunitys() {
        Community community=new Community();
        return houseMapper.selectCommunity(community);
    }

    @Override
    public void addHouse(House house, User user) {
        if(CollectionUtils.isNotEmpty(house.getHouseFiles())){
            String images = Joiner.on(",").join(fileService.getImgPaths(house.getHouseFiles()));
            house.setImages(images);
        }
        if(CollectionUtils.isNotEmpty(house.getFloorPlanList())){
            String images = Joiner.on(",").join(fileService.getImgPaths(house.getFloorPlanFiles()));
            house.setFloorPlan(images);
        }
        BeanHelper.onInsert(house);
        houseMapper.insert(house);
        logger.info("house.getId()"+house.getId());
        logger.info("user.getId()"+user.getId());
        bindUser2House(house.getId(),user.getId(),false);
    }


    public House queryOneHouse(Long id){
        House query=new House();
        query.setId(id);
        List<House> houses=queryAndSetImg(query,PageParams.build(1,1));
        if(!houses.isEmpty()){
            return houses.get(0);
        }
        return null;
    }

    @Override
    public void updateRating(Long id, Double rating) {
        House house=queryOneHouse(id);
        Double oldRating=house.getRating();
        Double newRating=oldRating.equals(0D)? rating : Math.min((oldRating+rating)/2, 5);
        House updateHouse=new House();
        updateHouse.setId(id);
        updateHouse.setRating(newRating);
        BeanHelper.onUpdate(updateHouse);
        houseMapper.updateHouse(updateHouse);

    }

    public void  bindUser2House(Long houseId,Long userId,boolean isCollect){
        HouseUser existHouserUser=houseMapper.selectHouseUser(userId,houseId,isCollect? HouseUserType.BOOKMARK.value:HouseUserType.SALE.value);
        if(existHouserUser!=null){
            return;
        }else {
            HouseUser houseUser=new HouseUser();
            houseUser.setUserId(userId);
            houseUser.setHouseId(houseId);
            houseUser.setType(isCollect? HouseUserType.BOOKMARK.value:HouseUserType.SALE.value);
            BeanHelper.setDefaultProp(houseUser,HouseUser.class);
            BeanHelper.onInsert(houseUser);
            houseMapper.insertHouseUser(houseUser);
        }
      }

    @Override
    public void unbindUser2House(Long id, Long userId, HouseUserType type) {
        if(type.equals(HouseUserType.SALE)){
            houseMapper.downHouse(id);
        }else {
            houseMapper.deleteHouseUser(id,userId,type.value);
        }
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
    public HouseUser getHouseUser(Long houseId){
        HouseUser houseUser =  houseMapper.selectSaleHouseUser(houseId);
        return houseUser;
    }


}
