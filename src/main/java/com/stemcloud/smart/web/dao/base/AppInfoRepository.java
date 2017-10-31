package com.stemcloud.smart.web.dao.base;

import com.stemcloud.smart.web.domain.base.AppInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description: some interface method of app info
 * @author liye
 */
public interface AppInfoRepository extends CrudRepository<AppInfo, Long> {
    /**
     * find by creator and isDeleted order by create time
     * @param creator: creator
     * @param isDeleted: 0 or 1
     * @return apps
     */
    List<AppInfo> findByCreatorAndIsDeletedOrderByCreateTime(String creator, int isDeleted);

    /**
     * find apps by isShare
     * @param isShare: 0 or 1
     * @return apps
     */
    List<AppInfo> findByIsShare(int isShare);

    /**
     * find app by id and creator
     * @param creator: creator
     * @param id: id
     * @return app
     */
    AppInfo findByCreatorAndId(String creator, long id);

    /**
     * update app
     * @param id
     * @param name
     * @param description
     * @return app id
     */
    @Query(value = "UPDATE zdc_base_app_info SET app_name = :name, app_description = :description WHERE id = :id", nativeQuery = true)
    @Modifying
    Integer updateAppInfo(@Param("id") int id, @Param("name") String name, @Param("description") String description);

    /**
     * delete app
     * @param id
     * @return
     */
    Integer deleteById(long id);
}
