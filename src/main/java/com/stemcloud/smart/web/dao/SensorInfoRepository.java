package com.stemcloud.smart.web.dao;

import com.stemcloud.smart.web.domain.SensorInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 */
public interface SensorInfoRepository extends CrudRepository<SensorInfo, Integer> {
    List<SensorInfo> findByIsShow(int isShow);

    List<SensorInfo> findByAppId(int appId);
}
