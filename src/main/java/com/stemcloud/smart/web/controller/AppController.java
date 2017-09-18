package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.web.domain.SensorData;
import com.stemcloud.smart.web.domain.SensorInfo;
import com.stemcloud.smart.web.view.Video;
import com.stemcloud.smart.web.service.AppManagementDataService;
import com.stemcloud.smart.web.service.SensorDataService;
import com.stemcloud.smart.web.service.ViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AppManagementDataService appManagementDataService;
    private final ViewService viewService;
    /*private final SensorDataService sensorDataService;*/

    @Autowired
    public AppController(AppManagementDataService appManagementDataService, ViewService viewService/*, SensorDataService sensorDataService*/) {
        this.appManagementDataService = appManagementDataService;
        this.viewService = viewService;
        /*this.sensorDataService = sensorDataService;*/
    }

    /*@GetMapping("/sensor/data")
    public List<SensorData> getSensorData(@RequestParam int sensorId){
        return sensorDataService.getSensorDataBySensorId(sensorId);
    }

    @GetMapping("/sensor/camera")
    public List<Video> getSensorCamera(@RequestParam int sensorId){
        logger.info("==========" + sensorId);
        List<Video> videos = sensorDataService.getSensorCameraBySensorId(sensorId);
        return videos;
    }*/

    @PostMapping("/new/app")
    public long newApp(@RequestParam Map<String, String> queryParams, HttpServletRequest request){
        int isNewApp = Integer.parseInt(queryParams.get("is-new-app"));
        if (isNewApp == 1){
            String user = viewService.getCurrentLoginUser(request);
            long appId = appManagementDataService.saveNewApp(queryParams, user);
            logger.info("New app " + appId);
            return appId;
        } else if (isNewApp == 0){
            int appId = Integer.valueOf(queryParams.get("id"));
            appManagementDataService.saveEditApp(queryParams);
            logger.info("Edit app " + appId);
            return appId;
        }

        return -1;
    }

    @PostMapping("/new/sensor")
    public String newSensor(@RequestParam Map<String, String> queryParams, HttpServletRequest request){
        int isNewSensor = Integer.parseInt(queryParams.get("is-new-sensor"));

        if (isNewSensor == 1) {
            String user = viewService.getCurrentLoginUser(request);
            SensorInfo sensorInfo = appManagementDataService.saveNewSensor(queryParams, user);
            logger.info("New sensor: " + sensorInfo.getId());
        } else if (isNewSensor == 0){
            int sensorId = Integer.parseInt(queryParams.get("sensorId"));
            int ret = appManagementDataService.saveEditSensor(sensorId, queryParams);
            logger.info("Update sensor: " + sensorId + " return state: " + ret);
        }

        return "success";
    }

    @GetMapping(value = "/delete/sensor")
    public String deleteSensor(@RequestParam long id){
        try {
            appManagementDataService.deleteSensor(id);
            logger.info("Delete sensor: " + id);
        } catch (Exception e){
            return "failure";
        }
        return "success";
    }

    @GetMapping(value = "/delete/app")
    public String deleteApp(@RequestParam long id){
        try {
            appManagementDataService.deleteApp(id);
            logger.info("Delete app: " + id);
        } catch (Exception e){
            return "failure";
        }
        return "success";
    }

    @GetMapping("/monitor/sensor")
    public String click(@RequestParam String appId, @RequestParam String sensorId){
        logger.info("AppId: " + appId);
        logger.info("SensorId: " + sensorId);

        // call your service here

        return "success";
    }
}
