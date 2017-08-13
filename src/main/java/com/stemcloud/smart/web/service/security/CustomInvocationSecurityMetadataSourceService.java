package com.stemcloud.smart.web.service.security;

import com.stemcloud.smart.web.dao.SysRoleRepository;
import com.stemcloud.smart.web.domain.security.SysResource;
import com.stemcloud.smart.web.domain.security.SysRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/10
 * Description: 根据user -> role -> resource的关系定义权限map
 */
@Service
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    private Logger logger = LoggerFactory.getLogger(CustomInvocationSecurityMetadataSourceService.class);

    @Autowired
    SysRoleRepository sysRoleRepository;

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    /**
     * init the auth when the application start
     * key: resource url, eg: /app
     * value: ConfigAttribute List
     */
    @PostConstruct
    private void loadResourceDefine() {
        Iterator<SysRole> sysRoles = sysRoleRepository.findAll().iterator();
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        logger.info("-------- Init the authentication --------");
        while (sysRoles.hasNext()){
            SysRole role = sysRoles.next();
            String auth = role.getName();
            ConfigAttribute ca = new SecurityConfig(auth);

            logger.info("Current role: " + auth);

            Set<SysResource> resources = role.getSysResources();
            for (SysResource resource : resources){
                String key = resource.getResourceUrl();

                if (resourceMap.containsKey(key)){
                    Collection<ConfigAttribute> value = resourceMap.get(key);
                    value.add(ca);
                    resourceMap.put(key, value);
                } else {
                    Collection<ConfigAttribute> value = new ArrayList<ConfigAttribute>();
                    value.add(ca);
                    resourceMap.put(key, value);
                }
                logger.info("Given the url " + key + " the role " + auth);
            }
        }
        logger.info("-------- Complete the authentication --------");
    }

    /**
     * 返回结果给CustomAccessDecisionManager的decide方法
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) o;
        if (resourceMap == null){
            loadResourceDefine();
        }

        RequestMatcher requestMatcher;
        for (String resURL : resourceMap.keySet()) {
            requestMatcher = new AntPathRequestMatcher(resURL);
            if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
