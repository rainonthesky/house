package com.kiyozawa.houses.service;

import com.kiyozawa.houses.model.Agency;
import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;

import java.util.List;

public interface AgencyService {
    public User getAgentDetail(Long agentId);

    public PageData<User> getAllAgent(PageParams pageParams);

    /**
     * 获取所有的机构的信息
     * @return
     */
    public List<Agency> getAllAgency();

    /**
     * 获取机构信息
     * @param id
     * @return
     */
    public Agency getAgency(Integer id);

    /**
     * 添加机构信息
     * @param agency
     * @return
     */
    public  int add(Agency agency);
}
