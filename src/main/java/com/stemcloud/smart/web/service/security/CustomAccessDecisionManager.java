package com.stemcloud.smart.web.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/10
 * Description: access decision manager, 用于判断当前用户访问当前url是否有权限
 */
@Service
public class CustomAccessDecisionManager implements AccessDecisionManager {
    private Logger logger = LoggerFactory.getLogger(CustomAccessDecisionManager.class);

    /**
     * decide 方法用作判定是否拥有权限
     * @param authentication 当前用户拥有的权限(CustomUserService中循环添加到GrantedAuthority对象中的权限信息集合)
     * @param o 包含客户端发起的请求的request信息
     * @param configAttributes 当前资源需要的权限(CustomInvocationSecurityMetadataSource中添加)
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (null == configAttributes || configAttributes.size() < 0)
            return;

        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        logger.info("Request url:" + request.getRequestURI() + " - " + request.getMethod());

        String needRole;

        logger.info("-------- Compare the authentication --------");
        // 遍历该资源可以访问的角色
        for (ConfigAttribute ca : configAttributes) {
            needRole = ca.getAttribute();
            // 看该用户对应的角色是否有权限(ROLE_ANONYMOUS为不登陆用户role)
            for (GrantedAuthority ga: authentication.getAuthorities()){
                logger.info("-------- Need role: " + needRole + ", current role: " + ga.getAuthority().trim());
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
