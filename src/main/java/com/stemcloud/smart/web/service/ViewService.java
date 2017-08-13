package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.AppInfoRepository;
import com.stemcloud.smart.web.dao.SensorInfoRepository;
import com.stemcloud.smart.web.domain.AppInfo;
import com.stemcloud.smart.web.domain.SensorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Autowired
    AppInfoRepository appInfoRepository;

    @Autowired
    SensorInfoRepository sensorInfoRepository;

    public String getCurrentLoginUser(HttpServletRequest request){
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContextImpl == null)
            throw new UsernameNotFoundException("The user is not exist!");

        return securityContextImpl.getAuthentication().getName();
    }

    public List<AppInfo> getAppInfoByCurrentUser(String user){
        return appInfoRepository.findByCreatorOrderByCreateTime(user);
    }

    public List<SensorInfo> getSensorInfoByAppId(long appId){
        return sensorInfoRepository.findByAppId(appId);
    }

    public boolean isAppBelongCurrentUser(long appId, String user){
        return appInfoRepository.findByCreatorAndId(user, appId) != null;
    }
}
