package com.kiyozawa.houses.service;

import com.kiyozawa.houses.model.City;

import java.util.List;

public interface CityService {
    /**
     * 获取所有的城市
     * @return
     */
    public List<City> getAllCitys();
}
