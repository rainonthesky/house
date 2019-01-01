package com.kiyozawa.houses.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.filter.Filter;

/**
 * 配置druid的连接池，主要配置数据库慢方法的检测
 */
@Configuration
public class DruidConfig {
    /**
     * @ConfigurationProperties可以在配置文件中绑定返回的对象属性
     * @return
     */
    @ConfigurationProperties(prefix = "spring.druid")
    @Bean(initMethod="init",destroyMethod="close")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return  dataSource;
    }

    /**
     * 定义慢日志的时间和日志的打印
     * @return
     */
    @Bean
    public Filter statFilter(){
        StatFilter filter=new StatFilter();
        //定义慢日志的时间
        filter.setSlowSqlMillis(3000);
        //是否打印慢日志
        filter.setLogSlowSql(true);
        //是否将日志合并起来
        filter.setMergeSql(true);
        return  filter;
    }

    /**
     * 配置监控，主要用于监控对查询的时间和对查询的次数等
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    }
}
