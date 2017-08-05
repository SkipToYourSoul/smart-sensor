package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.domain.SensorData;
import com.stemcloud.smart.web.dao.SensorDataRepository;
import com.stemcloud.smart.web.domain.SensorInfo;
import com.stemcloud.smart.web.dao.SensorInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description: return data for ajax
 */
@Service
public class SensorDataService {
    @Autowired
    SensorInfoRepository sensorInfoRepository;

    @Autowired
    SensorDataRepository sensorDataRepository;

    public List<SensorInfo> getShowSensorInfo(){
        return sensorInfoRepository.findByIsShow(1);
    }

    public List<SensorData> getSensorDataByAppId(int appId){
        return sensorDataRepository.findSensorDataByAppId(appId);
    }

    public List<SensorData> getSensorDataBySensorId(int sensorId) {
        return sensorDataRepository.findBySensorId(sensorId);
    }
}
