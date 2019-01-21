package com.kiyozawa.houses.service;

import com.kiyozawa.houses.model.Blog;
import com.kiyozawa.houses.page.PageData;
import com.kiyozawa.houses.page.PageParams;

import java.util.List;

public interface BlogService {
    /**
     * 查询所有的博客列表
     * @param blog
     * @param params
     * @return
     */

    PageData<Blog>queryBlog(Blog blog, PageParams params);
    Blog queryOneBlog(Integer id);
}
