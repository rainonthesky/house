package com.kiyozawa.houses.service;

import com.kiyozawa.houses.model.House;
import com.kiyozawa.houses.model.HouseUser;
import com.kiyozawa.houses.model.UserMsg;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;

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
}
