package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.UserInfoRepository;
import com.stemcloud.smart.web.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description: 将用户权限交给spring security进行管控
 */
@Service
public class CustomUserService /*implements UserDetailsService*/ {
    /*private static final Logger logger = LoggerFactory.getLogger(CustomUserService.class);

    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepository.findByUser(s);
        if (userInfo == null){
            logger.info("用户不存在");
            throw new UsernameNotFoundException("用户不存在");
        }

        // 添加权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(userInfo.getPermission()));

        return new User(userInfo.getUser(), userInfo.getPassword(), authorities);
    }*/
}
