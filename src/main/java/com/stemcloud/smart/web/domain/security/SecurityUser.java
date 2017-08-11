package com.stemcloud.smart.web.domain.security;

import com.stemcloud.smart.web.domain.SysRole;
import com.stemcloud.smart.web.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/9
 * Description: spring security 管理的 user 类
 */
public class SecurityUser extends SysUser implements UserDetails {
    private Logger logger = LoggerFactory.getLogger(SecurityUser.class);

    public SecurityUser(SysUser sysUser) {
        if (sysUser != null){
            this.setId(sysUser.getId());
            this.setUsername(sysUser.getUsername());
            this.setEmail(sysUser.getEmail());
            this.setPassword(sysUser.getPassword());
            this.setCreateTime(sysUser.getCreateTime());
            this.setSysRoles(sysUser.getSysRoles());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        Set<SysRole> userRoles = this.getSysRoles();

        if(userRoles != null)
        {
            for (SysRole role : userRoles) {
                logger.info("The user " + this.getUsername() + " has the role " + role.getName());

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
