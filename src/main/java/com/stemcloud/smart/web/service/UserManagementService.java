package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.SysResourceRepository;
import com.stemcloud.smart.web.dao.SysRoleRepository;
import com.stemcloud.smart.web.dao.SysUserRepository;
import com.stemcloud.smart.web.domain.security.SysResource;
import com.stemcloud.smart.web.domain.security.SysRole;
import com.stemcloud.smart.web.domain.security.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/10
 * Description: user register and authentication management
 */
@Service
public class UserManagementService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysResourceRepository sysResourceRepository;

    public void registerUser(String username, String password, String email, String role){
        SysUser sysUser = new SysUser();

        sysUser.setUsername(username);
        sysUser.setPassword(new BCryptPasswordEncoder(4).encode(password));
        sysUser.setEmail(email);

        SysRole sysRole = sysRoleRepository.findByName(role);
        Set<SysRole> set = new HashSet<SysRole>();
        set.add(sysRole);
        sysUser.setSysRoles(set);

        long userId = sysUserRepository.save(sysUser).getId();
        logger.info("New user: " + userId + " has the role " + role);
    }

    public void initRoleResource(){
        // --- init resource
        SysResource resource = new SysResource();
        resource.setResourceUrl("/app**");
        resource.setRemark("应用页");
        final SysResource appResource = sysResourceRepository.save(resource);

        SysResource resource2 = new SysResource();
        resource2.setResourceUrl("/class**");
        resource2.setRemark("课程页");
        final SysResource classResource = sysResourceRepository.save(resource2);

        // --- init role
        SysRole role = new SysRole();
        role.setName("ROLE_ADMIN");
        Set<SysResource> adminSysResources = new HashSet<SysResource>(){
            {
                add(appResource);
                add(classResource);
            }
        };
        role.setSysResources(adminSysResources);
        sysRoleRepository.save(role);

        // --- register user
        registerUser("root", "root", "root@root.com", "ROLE_ADMIN");
    }
}
