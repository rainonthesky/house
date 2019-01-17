package com.kiyozawa.houses.service.impl;

import com.kiyozawa.houses.constants.CommonConstants;
import com.kiyozawa.houses.mapper.CommentMapper;
import com.kiyozawa.houses.model.Comment;
import com.kiyozawa.houses.model.User;
import com.kiyozawa.houses.service.CommentService;
import com.kiyozawa.houses.service.UserService;
import com.kiyozawa.houses.utils.BeanHelper;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private UserService userService;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public void addHouseComment(Long houseId, String content, Long userId) {
        addComment(houseId,null,content,userId,1);
    }

    @Transactional(rollbackFor=Exception.class)
    public void addComment(Long houseId, Integer blogId, String content, Long userId, int type){
        Comment comment=new Comment();
        if(type ==1){
            comment.setHouseId(houseId);
        }else {
            comment.setBlogId(blogId);
        }
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setType(type);
        BeanHelper.onInsert(comment);
        BeanHelper.setDefaultProp(comment,Comment.class);
        commentMapper.insert(comment);
    }

    @Override
    public List<Comment> getHouseComments(long houseId, int size) {
        List<Comment>comments=commentMapper.selectComments(houseId,size);
        comments.forEach(comment -> {
            User user=userService.getUserById(comment.getUserId());
            comment.setUserName(user.getName());
            comment.setAvatar(user.getAvatar());
        });
        return comments;
    }

    @Override
    public List<Comment> getBlogComments(long blogId, int size) {
        List<Comment>comments=commentMapper.selectBlogComments(blogId,size);
        comments.forEach(comment -> {
            User user=userService.getUserById(comment.getUserId());
            comment.setUserName(user.getName());
            comment.setAvatar(user.getAvatar());
        });

        return comments;
    }

    @Override
    public void addBlogComment(int blogId, String content, Long userId) {
        addComment(null,blogId,content,userId, CommonConstants.COMMENT_BLOG_TYPE);
    }
}
















