package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.AppInfoRepository;
import com.stemcloud.smart.web.dao.ExperimentRepository;
import com.stemcloud.smart.web.dao.SensorInfoRepository;
import com.stemcloud.smart.web.domain.AppInfo;
import com.stemcloud.smart.web.domain.Experiment;
import com.stemcloud.smart.web.domain.SensorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description: some view operation, service for MainController
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
        if (securityContextImpl == null)
            return null;

        return securityContextImpl.getAuthentication().getName();
    }

    public List<AppInfo> getOnlineAppInfoByCurrentUser(String user){
        return appInfoRepository.findByCreatorAndIsDeletedOrderByCreateTime(user, 0);
    }

    public boolean isAppBelongCurrentUser(long appId, String user){
        return appInfoRepository.findByCreatorAndId(user, appId) != null;
    }

    public List<Experiment> getExperimentByAppId(long appId){
        return experimentRepository.findByAppId(appId);
    }

    public List<SensorInfo> getSensorInfoByCurrentUser(String user){
        return sensorInfoRepository.findByCreator(user);
    }

    public List<SensorInfo> getSensorInfoByAppId(long appId){
        return sensorInfoRepository.findByAppIdOrderByType(appId);
    }

    public List<SensorInfo> getSensorInfoByExperimentId(long expId){
        return sensorInfoRepository.findByExpIdOrderByType(expId);
    }
}
