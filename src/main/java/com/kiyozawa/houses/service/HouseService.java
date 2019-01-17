package com.kiyozawa.houses.service;

import com.kiyozawa.houses.constants.HouseUserType;
import com.kiyozawa.houses.model.*;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;

import java.util.List;

public interface HouseService {
    /**
     * 用于模糊查询的方法
     * @param query 查询的结果
     * @param build 返回页数
     */

    public PageData<House> queryHouse(House query, PageParams build);

    /**
     * 查询一个房屋的详细信息
     * @return
     */
    public House getOneHouse(Long id);

    /**
     * 添加用户评论信息
     * @param userMsg
     */
     public void addUserMsg(UserMsg userMsg);
    /**
     * 获取房屋人信息
     * @param houseId
     * @return
     */
    public HouseUser getHouseUser(Long houseId);

    /**
     * 查询房屋的图片
     * @param query
     * @param pageParams
     * @return
     */

    List<House> queryAndSetImg(House query, PageParams pageParams);

    /**
     * 获取所有小区
     * @return
     */
    public List<Community> getAllCommunitys();
    /**
     * 1.添加房产图片
     * 2.添加户型图片
     * 3.插入房产信息
     * 4.绑定用户到房产的关系
     *
     */
    public void addHouse(House house, User user);

    /**
     * 修改评论的分数
     * @param id
     * @param rating
     */
    public void updateRating(Long id,Double rating);

    /**
     *
     * @param houseId
     * @param userId
     * @param isCollect
     */
    public void  bindUser2House(Long houseId,Long userId,boolean isCollect);

    /**
     *
     * @param id
     * @param userId
     * @param type
     */
    public void unbindUser2House(Long id, Long userId, HouseUserType type);
}














