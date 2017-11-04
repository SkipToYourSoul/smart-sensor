package com.stemcloud.smart.web.dao.base;

import com.stemcloud.smart.web.domain.base.AppInfo;
import com.stemcloud.smart.web.domain.base.ExperimentInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 * @author liye
 */
public interface ExperimentRepository extends CrudRepository<ExperimentInfo, Long> {
    /**
     * findByAppInfoAndIsDeleted
     * @param appInfo app
     * @param isDeleted 0 or 1
     * @return
     */
    List<ExperimentInfo> findByAppInfoAndIsDeleted(AppInfo appInfo, int isDeleted);
}
