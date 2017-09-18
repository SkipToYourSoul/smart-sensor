package com.stemcloud.smart.web.dao;

import com.stemcloud.smart.web.domain.Experiment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 */
public interface ExperimentRepository extends CrudRepository<Experiment, Long> {
    List<Experiment> findByAppId(long appId);
}
