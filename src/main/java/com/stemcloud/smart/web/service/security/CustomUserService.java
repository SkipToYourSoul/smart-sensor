package com.stemcloud.smart.web.service.security;

import com.stemcloud.smart.web.domain.security.SecurityUser;
import com.stemcloud.smart.web.dao.SysUserRepository;
import com.stemcloud.smart.web.domain.security.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description: 将用户权限交给spring security进行管控
 */
@Service
public class CustomUserService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(CustomUserService.class);

    @Autowired
    SysUserRepository sysUserRepository;

    /**
     * 登陆表单提交后，转向此处进行验证处理
     * @param username
     * @return SecurityUser
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserRepository.findByUsername(username);
        if (user == null){
            logger.info("the user is not exist!");
            throw new UsernameNotFoundException("the user is not exist!");
        }
        logger.info("username:"+user.getUsername()+";password:"+user.getPassword());

        return new SecurityUser(user);
    }
}
