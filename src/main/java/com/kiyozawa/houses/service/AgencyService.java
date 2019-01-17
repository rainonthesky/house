package com.kiyozawa.houses.service;

import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;

import java.util.List;

public interface AgencyService {
    public User getAgentDetail(Long agentId);

    public PageData<User> getAllAgent(PageParams pageParams);
}
