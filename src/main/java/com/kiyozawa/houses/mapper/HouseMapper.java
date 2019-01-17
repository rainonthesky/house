package com.kiyozawa.houses.mapper;

import com.kiyozawa.houses.model.Community;
import com.kiyozawa.houses.model.House;
import com.kiyozawa.houses.model.HouseUser;
import com.kiyozawa.houses.model.UserMsg;
import com.kiyozawa.houses.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface HouseMapper {
    /**
     * 查询房屋的数据和分页数据
     * @param house
     * @param pageParams
     * @return
     */

    public List<House>selectPageHouses(@Param("house") House house,@Param("pageParams") PageParams pageParams);

    /**
     * 返回房屋数据的总数
     * @param house
     * @return
     */
    public Long selectPageCount(@Param("house") House house);

    /**
     * 查询所有的小区列表
     * @param community
     * @return
     */
    public List<Community>selectCommunity(Community community);

    /**
     * 增加信息
     * @param userMsg
     * @return
     */
    public int insertUserMsg(UserMsg userMsg);

    /**
     * 查询卖房人的信息
     * @param houseId
     * @return
     */
    public HouseUser selectSaleHouseUser(@Param("id")Long houseId);

    /**
     * 添加房产的信息
     * @return
     */
    public int insert(House house);

    /**
     * 查询用户和房屋对应的关系
     * @param userId
     * @param houseId
     * @param type
     * @return
     */
    public HouseUser selectHouseUser(@Param("userId")Long userId,@Param("id")Long houseId,Integer type);

    /**
     * 房产和用户关系的增加
     * @param houseUser
     * @return
     */
    public int  insertHouseUser(HouseUser houseUser);

    /**
     * 更新房子的信息
     * @param house
     * @return
     */
    public int updateHouse(House house);

    /**
     *
     * @param id
     * @return
     */
    public int downHouse(Long  id);

    /**
     * 删除房屋关系
     * @param id
     * @param userId
     * @param value
     * @return
     */
    public int deleteHouseUser(@Param("id")Long id,@Param("userId") Long userId,@Param("type") Integer value);
}