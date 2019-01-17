package com.kiyozawa.houses.service;

import com.kiyozawa.houses.model.Comment;

import java.util.List;

public interface CommentService {
    /**
     * 添加房屋评论
     * @param houseId
     * @param content
     * @param userId
     */
    public void addHouseComment(Long houseId, String content, Long userId);

    /**
     * 获取房屋评论
     * @param houseId
     * @param size
     * @return
     */
    public List<Comment> getHouseComments(long houseId, int size);

    /**
     * 获取博客评论
     * @param blogId
     * @param size
     * @return
     */
    public List<Comment> getBlogComments(long blogId, int size);

    /**
     * 添加博客评论
     * @param blogId
     * @param content
     * @param userId
     */
    public void addBlogComment(int blogId, String content, Long userId);
}
