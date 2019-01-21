package com.kiyozawa.houses.controller;
import com.kiyozawa.houses.constants.CommonConstants;
import com.kiyozawa.houses.model.Blog;
import com.kiyozawa.houses.model.Comment;
import com.kiyozawa.houses.model.House;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;
import com.kiyozawa.houses.service.BlogService;
import com.kiyozawa.houses.service.CommentService;
import com.kiyozawa.houses.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/list",method ={RequestMethod.GET,RequestMethod.POST})
    public String list(Integer pagesize, Integer pageNum, Blog query, ModelMap modelMap){
        PageData<Blog> ps=blogService.queryBlog(query, PageParams.build(pagesize,pageNum));
        List<House>houses=recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", houses);
        modelMap.put("ps", ps);
        return "/blog/listing";
    }
    @RequestMapping(value = "/detail",method = {RequestMethod.GET,RequestMethod.POST})
    public String blogDetail(int id,ModelMap modelMap){
        Blog blog=blogService.queryOneBlog(id);
        List<Comment>comments=commentService.getBlogComments(id,8);
        List<House>houses=recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", houses);
        modelMap.put("blog", blog);
        modelMap.put("commentList", comments);
        return "/blog/detail";

    }

}
