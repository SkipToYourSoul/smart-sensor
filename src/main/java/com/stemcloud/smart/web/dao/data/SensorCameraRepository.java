package com.stemcloud.smart.web.dao.data;

import com.stemcloud.smart.web.domain.data.SensorCamera;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/17
 * Description:
 */
public interface SensorCameraRepository extends CrudRepository<SensorCamera, Long> {
    List<SensorCamera> findBySensorIdAndExpId(long sensorId, long expId);
}
