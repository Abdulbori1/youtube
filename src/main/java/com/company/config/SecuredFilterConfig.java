package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilterConfig {

    @Autowired
    private JwtFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(jwtTokenFilter);
        bean.addUrlPatterns("/attach/adm/*");
        bean.addUrlPatterns("/auth/adm/*");
        bean.addUrlPatterns("/category/adm/*");
        bean.addUrlPatterns("/profile/adm/*");
        bean.addUrlPatterns("/profile/image/*");
        bean.addUrlPatterns("/chanel/adm/*");
        bean.addUrlPatterns("/playlist/adm/*");
        bean.addUrlPatterns("/tag/adm/*");
        bean.addUrlPatterns("/video/adm/*");
        bean.addUrlPatterns("/playlist/video/adm/*");
        bean.addUrlPatterns("/video_tag/adm/*");
        bean.addUrlPatterns("/comment/adm/*");
        bean.addUrlPatterns("/subscribe/adm/*");
        bean.addUrlPatterns("/video_like/adm/*");
        bean.addUrlPatterns("/coment_Like/adm/*");
        bean.addUrlPatterns("/report/adm/*");
        return bean;
    }

}
