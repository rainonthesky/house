package com.kiyozawa.houses.mapper;

import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgencyMapper {
    List<User> selectAgent(@Param("user")User user, @Param("pageParams")PageParams pageParams);

}
