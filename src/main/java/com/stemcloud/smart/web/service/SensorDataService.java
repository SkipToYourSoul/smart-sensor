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
import java.util.Map;

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

    public int saveNewApp(Map<String, String> queryParams){
        String name = queryParams.get("new-app-name");
        String description = queryParams.get("new-app-description");
        AppInfo appInfo = appInfoRepository.save(new AppInfo(name, "liye", description, dateFormat.format(new Date()),0));
        return appInfo.getId();
    }

    public int saveEditApp(Map<String, String> queryParams){
        int id = Integer.valueOf(queryParams.get("id"));
        String name = queryParams.get("new-app-name");
        String description = queryParams.get("new-app-description");
        int ret = appInfoRepository.updateAppInfo(id, name, description);

        return ret;
    }

    public SensorInfo saveNewSensor(Map<String, String> queryParams){
        int appId = Integer.parseInt(queryParams.get("appId"));

        String name = queryParams.get("new-sensor-name");
        String code = queryParams.get("new-sensor-code");
        int type = Integer.parseInt(queryParams.get("new-sensor-type"));
        String city = queryParams.get("new-sensor-city");
        double longitude = Double.parseDouble(queryParams.get("new-sensor-longitude"));
        double latitude = Double.parseDouble(queryParams.get("new-sensor-latitude"));
        String description = queryParams.get("new-sensor-description");

        return sensorInfoRepository.save(new SensorInfo(name, code, "liye", "liye", type, longitude, latitude,
                city, description, appId, 1, dateFormat.format(new Date()) , 0));
    }

    public int saveEditSensor(int sensorId, Map<String, String> queryParams){
        String name = queryParams.get("new-sensor-name");
        String code = queryParams.get("new-sensor-code");
        int type = Integer.parseInt(queryParams.get("new-sensor-type"));
        String city = queryParams.get("new-sensor-city");
        double longitude = Double.parseDouble(queryParams.get("new-sensor-longitude"));
        double latitude = Double.parseDouble(queryParams.get("new-sensor-latitude"));
        String description = queryParams.get("new-sensor-description");

        return sensorInfoRepository.updateSensorInfo(sensorId, name, code, type, longitude, latitude, city, description);
    }

    public int deleteSensor(int id){
        return sensorInfoRepository.deleteById(id);
    }
}
