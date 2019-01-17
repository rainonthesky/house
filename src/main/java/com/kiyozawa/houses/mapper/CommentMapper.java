package com.kiyozawa.houses.mapper;

import com.kiyozawa.houses.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface CommentMapper {
    /**
     * 插入一条评论
     * @param comment
     * @return
     */
   public int  insert(Comment comment);

    /**
     * 查询所有的评论
     * @param houseId
     * @param size
     * @return
     */
   List<Comment>  selectComments(@Param("houseId") Long houseId, @Param("size") int size);

    /**
     * 查询所有博客的评论
     * @param blogId
     * @param size
     * @return
     */
   List<Comment> selectBlogComments(@Param("blogId") Long blogId,@Param("size") Integer size);
}
