package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.web.domain.SensorData;
import com.stemcloud.smart.web.service.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description:
 */
@RestController
@RequestMapping("/app")
public class AppController {
    @Autowired
    SensorDataService sensorDataService;

    @GetMapping("/sensor/data")
    public List<SensorData> getSensorData(@RequestParam int sensorId){
        return sensorDataService.getSensorDataBySensorId(sensorId);
    }

    @GetMapping("/click")
    public String click(){

        return "success";
    }
}
