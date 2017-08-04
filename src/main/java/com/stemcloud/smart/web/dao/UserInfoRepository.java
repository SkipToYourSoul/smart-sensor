package com.stemcloud.smart.web.dao;

import com.stemcloud.smart.web.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, String> {
    public UserInfo findByUser(String user);
}
