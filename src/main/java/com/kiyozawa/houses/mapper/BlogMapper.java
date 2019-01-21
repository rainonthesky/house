package com.kiyozawa.houses.mapper;

import com.kiyozawa.houses.model.Blog;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper {
    /**
     * 查询所有博客列表
     * @param query
     * @param params
     * @return
     */
    List<Blog> selectBlog(@Param("blog") Blog query, @Param("pageParams") PageParams params);

    /**
     * 查询博客的数量
     * @param query
     * @return
     */
    public Long selectBlogCount(Blog query);
}
