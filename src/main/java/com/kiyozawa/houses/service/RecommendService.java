package com.kiyozawa.houses.service;

import com.kiyozawa.houses.model.House;

import java.util.List;

public interface RecommendService {

    public void  increase(Long id);

    /**
     * 获取热门房产的信息
     * @param size
     * @return
     */
    List<House> getHotHouse(Integer size);

    /**
     * 获取最新的房源
     * @return
     */

    public List<House>getLastest();
}
