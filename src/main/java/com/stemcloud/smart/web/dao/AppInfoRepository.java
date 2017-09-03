package com.stemcloud.smart.web.dao;

import com.stemcloud.smart.web.domain.AppInfo;
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
public interface AppInfoRepository extends CrudRepository<AppInfo, Integer> {
    List<AppInfo> findByCreatorOrderByCreateTime(String creator);

    List<AppInfo> findByIsShare(int isShare);

    List<AppInfo> findByCreatorOrIsShare(String creator, int isShare);

    AppInfo findByCreatorAndId(String creator, long id);

    @Query(value = "UPDATE base_app_info SET app_name = :name, longitude = :longitude, latitude = :latitude, sensor_city = :city, app_description = :description WHERE id = :id", nativeQuery = true)
    @Modifying
    Integer updateAppInfo(@Param("id") int id, @Param("name") String name, @Param("longitude") double longitude, @Param("latitude") double latitude,
                          @Param("city") String city, @Param("description") String description);

    Integer deleteById(long id);
}
