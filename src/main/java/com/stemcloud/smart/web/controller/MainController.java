package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.web.domain.AppInfo;
import com.stemcloud.smart.web.domain.Experiment;
import com.stemcloud.smart.web.domain.SensorInfo;
import com.stemcloud.smart.web.service.AppManagementDataService;
import com.stemcloud.smart.web.service.SensorDataService;
import com.stemcloud.smart.web.service.ViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.management.Sensor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/15
 * Description: main controller of html template
 */
@Controller
public class MainController implements ErrorController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    private final ViewService viewService;
    private final AppManagementDataService appManagementDataService;
    private final SensorDataService sensorDataService;

    @Autowired
    public MainController(ViewService viewService, AppManagementDataService appManagementDataService, SensorDataService sensorDataService) {
        this.viewService = viewService;
        this.appManagementDataService = appManagementDataService;
        this.sensorDataService = sensorDataService;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        List<AppInfo> apps = new ArrayList<AppInfo>();
        String loginUser = viewService.getCurrentLoginUser(request);

        if (loginUser != null) {
            logger.info("Current login user: " + loginUser);
        }

        apps.addAll(appManagementDataService.getSharedAppInfo());
        model.addAttribute("apps", apps);
        model.addAttribute("inIndex", true);

        return "index";
    }

    @GetMapping("/app")
    public String app(@RequestParam(value = "id", required = false) Integer id, Model model,
                      HttpServletRequest request, HttpServletResponse response) throws IOException {
        // --- get login user name
        String currentUser = viewService.getCurrentLoginUser(request);
        if (currentUser == null)
            response.sendRedirect("/login");

        // --- check the app belongs the user
        if (id != null && !viewService.isAppBelongCurrentUser(id, currentUser)){
            logger.warn("The user: " + currentUser + " has not the app " + id);
            response.sendRedirect("/app");
        }

        model.addAttribute("inApp", true);

        // --- get app info according current user
        List<AppInfo> apps = viewService.getOnlineAppInfoByCurrentUser(currentUser);
        logger.info("APP PAGE: FIND " + apps.size() + " APPS By " + currentUser);
        model.addAttribute("apps", apps);

        if (id == null){
            List<SensorInfo> sensors = viewService.getSensorInfoByCurrentUser(currentUser);
            logger.info("APP PAGE: FIND " + sensors.size() + " SENSORS BY USER " + currentUser);
            model.addAttribute("sensors", sensors);

            logger.info("APP PAGE: IN APP MANAGE PAGE!");
        } else {
            if (apps.size() > 0){
                int currentAppIndex = -1;
                for (int i=0; i < apps.size(); i++){
                    if (id == apps.get(i).getId()){
                        currentAppIndex = i+1;
                        break;
                    }
                }

                List<Experiment> experiments = viewService.getExperimentByAppId(id);
                logger.info("APP PAGE: FIND " + experiments.size() + " EXPERIMENTS By " + id);

                // --- init sensor data
                Map<Long, Map<Integer, List<SensorInfo>>> sensors = new HashMap<Long, Map<Integer, List<SensorInfo>>>();
                Map<Long, Map<Long, Object>> data = new HashMap<Long, Map<Long, Object>>();
                for (Experiment experiment : experiments){
                    long expId = experiment.getId();
                    List<SensorInfo> expSensors = viewService.getSensorInfoByExperimentId(expId);

                    Map<Long, Object> sensorData = new HashMap<Long, Object>();
                    for (SensorInfo sensor: expSensors){
                        long sensorId = sensor.getId();
                        if (sensor.getType() == 1){
                            sensorData.put(sensorId, sensorDataService.getSensorTimeSeriesDataBySensorId(sensorId, expId));
                        } else if (sensor.getType() == 2){
                            sensorData.put(sensorId, sensorDataService.getSensorCameraBySensorId(sensorId, expId));
                        }
                    }
                    data.put(expId, sensorData);
                    sensors.put(expId, viewService.getSensorInfoByExperimentIdPartByType(expId));
                }

                model.addAttribute("sensors", sensors);
                model.addAttribute("currentAppIndex", currentAppIndex);
                model.addAttribute("sensorData", data);
            } else
                logger.info("APP PAGE: CURRENT USER HAS NO APPS!");

            logger.info("APP PAGE: IN APP DATA PAGE!");
        }

        return "app";
    }

    @GetMapping("/device")
    public String device(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // --- get login user name
        String currentUser = viewService.getCurrentLoginUser(request);
        if (currentUser == null)
            response.sendRedirect("/login");

        // --- get the devices of user
        List<SensorInfo> devices = viewService.getOnlineSensorInfoByCurrentUser(currentUser);
        model.addAttribute("sensors", devices);

        model.addAttribute("inDevice", true);

        return "device";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/denied")
    public String denied(){
        return "denied";
    }

    @GetMapping("/error")
    public String error(){
        return "denied";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
