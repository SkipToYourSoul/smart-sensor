package com.stemcloud.smart.web.dao;

import com.stemcloud.smart.web.domain.security.SysRole;
import org.springframework.data.repository.CrudRepository;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/10
 * Description:
 */
public interface SysRoleRepository extends CrudRepository<SysRole, Long> {
    SysRole findByName(String name);
}
