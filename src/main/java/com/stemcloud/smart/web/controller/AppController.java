package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.web.domain.SensorData;
import com.stemcloud.smart.web.domain.SensorInfo;
import com.stemcloud.smart.web.service.SensorDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/4
 * Description:
 */
@RestController
@RequestMapping("/app")
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    SensorDataService sensorDataService;

    @GetMapping("/sensor/data")
    public List<SensorData> getSensorData(@RequestParam int sensorId){
        return sensorDataService.getSensorDataBySensorId(sensorId);
    }

    @PostMapping("/new")
    public int newApp(@RequestParam Map<String, String> queryParams){
        String name = queryParams.get("new-app-name");
        String description = queryParams.get("new-app-description");
        int appId = sensorDataService.saveNewApp("liye", name, description);
        logger.info("New app " + appId);
        return appId;
    }

    @PostMapping("/edit")
    public int editApp(@RequestParam Map<String, String> queryParams){
        String name = queryParams.get("edit-app-name");
        String description = queryParams.get("edit-app-description");
        int appId = Integer.valueOf(queryParams.get("id"));

        return sensorDataService.saveEditApp(appId, name, description);
    }

    @PostMapping("/new/sensor")
    public int newSensor(@RequestParam Map<String, String> queryParams){
        int appId = Integer.parseInt(queryParams.get("appId"));

        String name = queryParams.get("new-sensor-name");
        String code = queryParams.get("new-sensor-code");
        int type = Integer.parseInt(queryParams.get("new-sensor-type"));
        String city = queryParams.get("new-sensor-city");
        double longitude = Double.parseDouble(queryParams.get("new-sensor-longitude"));
        double latitude = Double.parseDouble(queryParams.get("new-sensor-latitude"));
        String description = queryParams.get("new-sensor-description");

        SensorInfo sensorInfo = sensorDataService.saveNewSensor(appId, name, code, type, city, longitude, latitude, description);

        return 1;
    }

    @GetMapping("/click")
    public String click(){

        return "success";
    }
}
