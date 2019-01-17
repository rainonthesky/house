package com.kiyozawa.houses.service.impl;

import com.google.common.collect.Lists;
import com.kiyozawa.houses.model.City;
import com.kiyozawa.houses.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CityServiceImpl implements CityService {
    @Override
    public List<City> getAllCitys() {
        City city=new City();
        city.setCityCode("110000");
        city.setCityName("上海");
        city.setId(1);
        return Lists.newArrayList(city);
    }
}
