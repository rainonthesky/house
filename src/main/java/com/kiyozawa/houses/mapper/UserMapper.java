package com.kiyozawa.houses.mapper;
import com.kiyozawa.houses.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
    /**
     * 查询所有用
     * @return
     */
    List<User> getUserList();

    public  int insertUser(User account);

    /**
     *删除email
     * @param email
     * @return
     */
    public  int delete(String email);

    /**
     * 根据条件查询用户列表
     * @param user
     * @return
     */
    List<User> selectUsersByQuery(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public int update(User user);
}