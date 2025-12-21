package com.eventify.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.UrlHandlerFilter;

@Configuration
public class TrailingSlashConfig {

    @Bean
    public FilterRegistrationBean<UrlHandlerFilter> urlHandlerFilter() {
        UrlHandlerFilter filter = UrlHandlerFilter.trailingSlashHandler("/**")
                .wrapRequest() // handle the request without redirect
                .build();
        
        FilterRegistrationBean<UrlHandlerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*"); // apply to all URLs
        registrationBean.setOrder(Integer.MIN_VALUE); // make it run early in the filter chain
        return registrationBean;
    }
}
