package com.stemcloud.smart.web.dao;

import com.stemcloud.smart.web.domain.SensorInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 */
public interface SensorInfoRepository extends CrudRepository<SensorInfo, Long> {
    List<SensorInfo> findByCreator(String creator);

    List<SensorInfo> findByAppIdOrderByType(long appId);

    List<SensorInfo> findByExpIdOrderByType(long expId);

    @Query(value = "UPDATE base_sensor_info SET sensor_name = :name, " +
            "sensor_code = :code, sensor_type = :sType, longitude = :longitude, latitude = :latitude, sensor_city = :city, " +
            "sensor_description = :description WHERE id = :id", nativeQuery = true)
    @Modifying
    Integer updateSensorInfo(@Param("id") int id, @Param("name") String name,
                             @Param("code") String code, @Param("sType") int sType,
                             @Param("longitude") double longitude, @Param("latitude") double latitude,
                             @Param("city") String city, @Param("description") String description);

    Integer deleteById(long id);
}
