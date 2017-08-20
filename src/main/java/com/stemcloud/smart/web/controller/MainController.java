package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.web.domain.AppInfo;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        String loginUser = viewService.getCurrentLoginUser(request);
        if (loginUser != null) {
            logger.info("Current login user: " + loginUser);
            model.addAttribute("loginUser", loginUser);
            model.addAttribute("sensors", appManagementDataService.getSensorInfoByCreatorAndShared(loginUser));
        } else {
            logger.info("No login user!");
            model.addAttribute("sensors", appManagementDataService.getSharedSensorInfo());
        }
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

        model.addAttribute("inApp", true);

        // --- get app info according current user
        List<AppInfo> apps = viewService.getAppInfoByCurrentUser(currentUser);
        logger.info("FIND " + apps.size() + " APPS By " + currentUser);

        if (id != null && !viewService.isAppBelongCurrentUser(id, currentUser)){
            logger.warn("The user: " + currentUser + " has not the app " + id);
            response.sendRedirect("/app");
        }

        if (apps.size() != 0){
            long currentAppId = apps.get(0).getId();
            int currentAppIndex = 1;

            if (id != null)
                for (int i=0; i < apps.size(); i++){
                    if (id == apps.get(i).getId()){
                        currentAppId = id;
                        currentAppIndex = i+1;
                        break;
                    }
                }

            // --- get first app's sensor info
            List<SensorInfo> sensors = viewService.getSensorInfoByAppId(currentAppId);
            logger.info("FIND " + sensors.size() + " SENSORS By " + currentUser);

            // --- get attribute
            model.addAttribute("apps", apps);
            model.addAttribute("sensors", sensors);
            model.addAttribute("currentAppId", currentAppId);
            model.addAttribute("currentAppIndex", currentAppIndex);

            // --- init sensor data
            Map<Long, Object> sensorData = new HashMap<Long, Object>();
            for (SensorInfo sensor: sensors){
                long sensorId = sensor.getId();
                if (sensor.getType() == 1){
                    sensorData.put(sensorId, sensorDataService.getSensorDataBySensorId(sensorId));
                } else if (sensor.getType() == 2){
                    sensorData.put(sensorId, sensorDataService.getSensorCameraBySensorId(sensorId));
                } else if (sensor.getType() == 3){

                }
            }
            model.addAttribute("sensorData", sensorData);
        }

        return "app";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/class")
    public String classes(){
        return "class";
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
