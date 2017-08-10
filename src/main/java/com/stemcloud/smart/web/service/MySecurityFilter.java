package com.stemcloud.smart.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/10
 * Description:
 */
@Component
public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(MySecurityFilter.class);

    @Autowired
    private CustomInvocationSecurityMetadataSourceService invocationSecurityMetadataSourceService;

    @Autowired
    private CustomAccessDecisionManager accessDecisionManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostConstruct
    public void init(){
        super.setAuthenticationManager(authenticationManager);
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        // logger.info("-------- obtainSecurityMetadataSource --------");
        return this.invocationSecurityMetadataSourceService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("-------- Filter init --------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("-------- Do filter --------");

        logger.info("Name: " + servletRequest.getLocalName());
        logger.info("Remote: " + servletRequest.getRemoteAddr() + " - " + servletRequest.getRemoteHost());

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
