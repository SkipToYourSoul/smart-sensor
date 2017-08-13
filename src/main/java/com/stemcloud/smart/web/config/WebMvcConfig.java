package com.stemcloud.smart.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/13
 * Description:
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 拦截配置
        addInterceptor.addPathPatterns("/app/new/app");
        addInterceptor.addPathPatterns("/app/new/sensor");
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/denied"));
                // container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/page/404.html"));
                // container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/page/500.html"));
            }
        };
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            logger.info("======== pre handle ========");
            SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                    .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
            if (securityContextImpl != null)
                logger.info("Username:"
                        + securityContextImpl.getAuthentication().getName() + " do the post action: " + request.getRequestURI());

            return true;
        }
    }
}
