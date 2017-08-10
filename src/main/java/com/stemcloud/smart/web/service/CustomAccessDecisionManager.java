package com.stemcloud.smart.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/10
 * Description: access decision manager
 */
@Service
public class CustomAccessDecisionManager implements AccessDecisionManager {
    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDecisionManager.class);

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null)
            return;

        logger.info("-------- Compare the authentication --------");
        // 遍历该资源可以访问的角色
        for (ConfigAttribute ca : configAttributes) {
            String needRole = ((SecurityConfig) ca).getAttribute();
            // 看该用户对应的角色是否有权限(ROLE_ANONYMOUS为不登陆用户role)
            for (GrantedAuthority ga: authentication.getAuthorities()){
                logger.info("-------- compare role: " + needRole + " with " + ga.getAuthority().trim());
                if (needRole.trim().equals(ga.getAuthority().trim()))
                    return;
            }
        }

        throw new AccessDeniedException("Authentication denied!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
