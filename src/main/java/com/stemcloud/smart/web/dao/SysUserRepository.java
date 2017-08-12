package com.stemcloud.smart.web.dao;

import com.stemcloud.smart.web.domain.security.SysUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/9
 * Description:
 */
public interface SysUserRepository extends CrudRepository<SysUser, Long> {
    SysUser findByUsername(String username);
}
