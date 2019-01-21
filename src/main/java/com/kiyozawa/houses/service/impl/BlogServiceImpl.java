package com.kiyozawa.houses.service.impl;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.kiyozawa.houses.mapper.BlogMapper;
import com.kiyozawa.houses.model.Blog;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;
import com.kiyozawa.houses.service.BlogService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Override
    public PageData<Blog> queryBlog(Blog query, PageParams params) {
        List<Blog> blogs=blogMapper.selectBlog(query,params);
        populate(blogs);
        Long count =blogMapper.selectBlogCount(query);
        return PageData.<Blog>buildPage(blogs,count,params.getPageSize(),params.getPageNum());
    }

    @Override
    public Blog queryOneBlog(Integer id) {
        Blog query=new Blog();
        query.setId(id);
        List<Blog>blogs=blogMapper.selectBlog(query,new PageParams(1,1));
        if(!blogs.isEmpty()){
            return blogs.get(0);
        }
        return null;

    }

    private void populate(List<Blog> blogs){
        if(!blogs.isEmpty()){
            blogs.stream().forEach(item->{
                String stripped= Jsoup.parse(item.getContent()).text();
                item.setDigest(stripped.substring(0,Math.min(stripped.length(),40)));
                String tags=item.getTags();
                item.getTagList().addAll(Lists.newArrayList(Splitter.on(",").split(tags)));
            });
        }
    }
}
