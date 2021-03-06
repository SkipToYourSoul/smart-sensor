package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.base.AppInfoRepository;
import com.stemcloud.smart.web.dao.base.ExperimentRepository;
import com.stemcloud.smart.web.dao.base.SensorInfoRepository;
import com.stemcloud.smart.web.domain.base.AppInfo;
import com.stemcloud.smart.web.domain.base.ExperimentInfo;
import com.stemcloud.smart.web.domain.base.SensorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description: some view operation, service for MainController
 * @author liye
 */
@Service
public class ViewService {
    private final AppInfoRepository appInfoRepository;
    private final SensorInfoRepository sensorInfoRepository;
    private final ExperimentRepository experimentRepository;

    @Autowired
    public ViewService(AppInfoRepository appInfoRepository, SensorInfoRepository sensorInfoRepository, ExperimentRepository experimentRepository) {
        this.appInfoRepository = appInfoRepository;
        this.sensorInfoRepository = sensorInfoRepository;
        this.experimentRepository = experimentRepository;
    }

    public String getCurrentLoginUser(HttpServletRequest request){
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContextImpl == null) {
            return null;
        }

        return securityContextImpl.getAuthentication().getName();
    }

    /**
     * find apps of current user
     * @param user eg.root
     * @return List<AppInfo>
     */
    public List<AppInfo> getOnlineAppInfoByCurrentUser(String user){
        return appInfoRepository.findByCreatorAndIsDeletedOrderByCreateTime(user, 0);
    }

    /**
     * is app belong current user
     * @param appId
     * @param user
     * @return
     */
    public boolean isAppBelongCurrentUser(long appId, String user){
        return appInfoRepository.findByCreatorAndId(user, appId) != null;
    }

    /**
     * getOnlineExperimentOfCurrentApp
     * @param appId
     * @return
     */
    public List<ExperimentInfo> getOnlineExperimentOfCurrentApp(long appId){
        AppInfo appInfo = appInfoRepository.findById(appId);
        return experimentRepository.findByAppInfoAndIsDeleted(appInfo, 0);
    }

    public List<SensorInfo> getOnlineSensorInfoByCurrentUser(String user){
        return sensorInfoRepository.findByCreatorAndIsDeleted(user, 0);
    }

    /*public List<SensorInfo> getSensorInfoByAppId(long appId){
        return sensorInfoRepository.findByAppIdOrderByType(appId);
    }

    public List<SensorInfo> getSensorInfoByExperimentId(long expId){
        return sensorInfoRepository.findByExpIdOrderByType(expId);
    }

    public Map<Integer, List<SensorInfo>> getSensorInfoByExperimentIdPartByType(long expId){
        Map<Integer, List<SensorInfo>> map = new HashMap<Integer, List<SensorInfo>>();
        for (int type = 1; type<=2; type++){
            List<SensorInfo> list = sensorInfoRepository.findByExpIdAndType(expId, type);
            if (list.size() > 0) {
                map.put(type, list);
            }
        }

        return map;
    }*/
}
