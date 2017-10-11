package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.web.domain.SensorInfo;
import com.stemcloud.smart.web.service.AppManagementDataService;
import com.stemcloud.smart.web.service.ViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/10/11
 * Description:
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AppManagementDataService appManagementDataService;
    private final ViewService viewService;

    @Autowired
    public DeviceController(AppManagementDataService appManagementDataService, ViewService viewService) {
        this.appManagementDataService = appManagementDataService;
        this.viewService = viewService;
    }

    @PostMapping("/new/sensor")
    public String newSensor(@RequestParam Map<String, String> queryParams, HttpServletRequest request){
        int isNewSensor = Integer.parseInt(queryParams.get("is-new-sensor"));

        if (isNewSensor == 1) {
            // --- add a sensor
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
}
