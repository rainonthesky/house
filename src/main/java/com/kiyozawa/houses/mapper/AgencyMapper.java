package com.kiyozawa.houses.mapper;

import com.kiyozawa.houses.model.Agency;
import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgencyMapper {
    List<User> selectAgent(@Param("user")User user, @Param("pageParams")PageParams pageParams);

    Long selectAgentCount(@Param("user") User user);

    /**
     * 获取机构列表
     * @param agency
     * @return
     */
    List<Agency>select(Agency agency);

    /**
     * 添加机构信息
     * @param agency
     * @return
     */
    public int insert(Agency agency);

}
