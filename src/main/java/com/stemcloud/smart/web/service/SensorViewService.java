package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.domain.SensorInfo;
import com.stemcloud.smart.web.domain.SensorInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 */
@Service
public class SensorViewService {
    @Autowired
    SensorInfoRepository sensorInfoRepository;

    public List<SensorInfo> getShowSensorInfo(){
        return sensorInfoRepository.findByIsShow(1);
    }
}
