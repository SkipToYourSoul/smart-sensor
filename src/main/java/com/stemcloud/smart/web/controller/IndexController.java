package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.web.domain.SensorInfo;
import com.stemcloud.smart.web.service.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 */
@RestController
@RequestMapping("/")
public class IndexController {
    @Autowired
    SensorDataService sensorDataService;

    @GetMapping(value = "/index/map/sensor")
    public List<SensorInfo> getSensorInfo(){
        return sensorDataService.getShowSensorInfo();
    }
}
