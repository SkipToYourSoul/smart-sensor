package com.stemcloud.smart.web.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/10
 * Description: security filter, judge user access before request
 */
@Service
public class MySecurityFilterInterceptor extends AbstractSecurityInterceptor implements Filter {
    private Logger logger = LoggerFactory.getLogger(MySecurityFilterInterceptor.class);

    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setCustomAccessDecisionManager(CustomAccessDecisionManager accessDecisionManager){
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        // logger.info("-------- obtainSecurityMetadataSource --------");
        return this.securityMetadataSource;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("-------- Filter init --------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info("-------- Do filter - Request Path:" + request.getRequestURI());

        FilterInvocation fi = new FilterInvocation( servletRequest, servletResponse, filterChain );
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try{
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }finally{
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {
        logger.info("-------- Filter end --------");
    }
}
