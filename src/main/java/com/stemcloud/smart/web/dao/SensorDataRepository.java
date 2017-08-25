package com.stemcloud.smart.web.dao;

import com.stemcloud.smart.web.domain.SensorData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 */
public interface SensorDataRepository extends CrudRepository<SensorData, Integer> {
    @Query(value = "SELECT b.* FROM " +
            "(" +
            "SELECT id FROM base_sensor_info WHERE sensor_app_id = :appId" +
            ") a" +
            " JOIN " +
            "(" +
            "SELECT * FROM sensor_data" +
            ") b " +
            "ON a.id = b.sensor_id " +
            "ORDER BY sensor_id, b.id", nativeQuery = true)
    public List<SensorData> findSensorDataByAppId(@Param("appId") int appId);

    public List<SensorData> findBySensorIdOrderByDataTime(long sensorId);
}
