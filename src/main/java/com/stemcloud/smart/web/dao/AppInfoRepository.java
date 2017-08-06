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
    public List<AppInfo> findByCreatorOrderByCreateTime(String creator);

    @Query(value = "UPDATE base_app_info set app_name = :name, app_description = :description where id = :id", nativeQuery = true)
    @Modifying
    Integer updateAppInfo(@Param("id") int id, @Param("name") String name, @Param("description") String description);
}