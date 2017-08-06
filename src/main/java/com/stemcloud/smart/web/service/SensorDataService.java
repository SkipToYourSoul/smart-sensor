package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.AppInfoRepository;
import com.stemcloud.smart.web.domain.AppInfo;
import com.stemcloud.smart.web.domain.SensorData;
import com.stemcloud.smart.web.dao.SensorDataRepository;
import com.stemcloud.smart.web.domain.SensorInfo;
import com.stemcloud.smart.web.dao.SensorInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description: return data for ajax
 */
@Service
@Transactional
public class SensorDataService {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    SensorInfoRepository sensorInfoRepository;

    @Autowired
    SensorDataRepository sensorDataRepository;

    @Autowired
    AppInfoRepository appInfoRepository;

    public List<SensorInfo> getShowSensorInfo(){
        return sensorInfoRepository.findByIsShow(1);
    }

    public List<SensorData> getSensorDataByAppId(int appId){
        return sensorDataRepository.findSensorDataByAppId(appId);
    }

    public List<SensorData> getSensorDataBySensorId(int sensorId) {
        return sensorDataRepository.findBySensorId(sensorId);
    }

    public int saveNewApp(String user, String name, String description){
        AppInfo appInfo = appInfoRepository.save(new AppInfo(name, user, description, dateFormat.format(new Date()),0));
        return appInfo.getId();
    }

    public int saveEditApp(int id, String name, String description){
        int ret = appInfoRepository.updateAppInfo(id, name, description);
        return ret;
    }
}
