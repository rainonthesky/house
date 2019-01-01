package com.kiyozawa.houses.autoconfig;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sun.net.www.http.HttpClient;

@Configuration
@ConditionalOnClass(HttpClient.class)//当HttpClient.class文件存在时HttpClientAutoConfiguration执行
@EnableConfigurationProperties(HttpClientProperties.class)//用@EnableConfigurationProperties注解使@ConfigurationProperties生效，并从IOC容器中获取bean
public class HttpClientAutoConfiguration {

    @Autowired
    private HttpClientProperties properties;
//    private  final HttpClientProperties properties;
//
//    public HttpClientAutoConfiguration(HttpClientProperties properties) {
//        this.properties = properties;
//    }

    /**
     * httpClient定义的bin
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(HttpClient.class)
    public CloseableHttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(properties.getConnectionTimeOut())
                .setSocketTimeout(properties.getSocketTimeOut()).build();
        CloseableHttpClient client= HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
                .setMaxConnPerRoute(properties.getMaxConnPerRoute()).setConnectionReuseStrategy(new NoConnectionReuseStrategy()).build();
        return client;
    }




}
