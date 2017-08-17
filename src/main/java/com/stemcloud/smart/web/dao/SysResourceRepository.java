package com.stemcloud.smart.web.dao;

import com.stemcloud.smart.web.domain.security.SysResource;
import org.springframework.data.repository.CrudRepository;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/17
 * Description:
 */
public interface SysResourceRepository extends CrudRepository<SysResource, Long> {
    SysResource findByResourceUrl(String url);
}
