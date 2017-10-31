package com.stemcloud.smart.web.dao.base;

import com.stemcloud.smart.web.domain.base.ExperimentInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 */
public interface ExperimentRepository extends CrudRepository<ExperimentInfo, Long> {
    List<ExperimentInfo> findByAppId(long appId);
}
