package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.web.domain.base.AppInfo;
import com.stemcloud.smart.web.domain.base.ExperimentInfo;
import com.stemcloud.smart.web.domain.base.SensorInfo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Belongs to smart-sensor
 * Description: main controller of html template
 * @author liye on 2017/7/15
 */
@Controller
public class MainController implements ErrorController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    private final ViewService viewService;
    private final SensorDataService sensorDataService;

    @Autowired
    public MainController(ViewService viewService, SensorDataService sensorDataService) {
        this.viewService = viewService;
        this.sensorDataService = sensorDataService;
    }

    /**
     * index.html
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        String loginUser = viewService.getCurrentLoginUser(request);
        if (loginUser != null) {
            logger.info("Current login user: " + loginUser);
        }
        model.addAttribute("inIndex", true);

        return "index";
    }

    /**
     * app.html
     * @param id appId
     * @param model
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/app")
    public String app(@RequestParam(value = "id", required = false) Integer id, Model model,
                      HttpServletRequest request, HttpServletResponse response) throws IOException {
        // --- check the login user
        String currentUser = viewService.getCurrentLoginUser(request);
        if (currentUser == null) {
            response.sendRedirect("/login");
        }

        // --- check the app belongs the user
        if (id != null && !viewService.isAppBelongCurrentUser(id, currentUser)){
            logger.warn("The user: " + currentUser + " has not the app " + id);
            response.sendRedirect("/app");
        }

        // --- get app and sensor info according current user
        List<AppInfo> apps = viewService.getOnlineAppInfoByCurrentUser(currentUser);
        logger.info("APP PAGE: FIND " + apps.size() + " APPS By " + currentUser);
        model.addAttribute("apps", apps);
        List<SensorInfo> sensors = viewService.getOnlineSensorInfoByCurrentUser(currentUser);
        logger.info("APP PAGE: FIND " + sensors.size() + " SENSORS BY USER " + currentUser);
        model.addAttribute("sensors", sensors);

        model.addAttribute("inApp", true);

        if (id == null){
            // --- id is null, in app management page
            logger.info("APP PAGE: IN APP MANAGE PAGE!");
        } else {
            // --- in app page
            List<ExperimentInfo> experimentInfoList = viewService.getOnlineExperimentOfCurrentApp(id);
            model.addAttribute("experiment", experimentInfoList);










            /*if (apps.size() > 0){
                int currentAppIndex = -1;
                for (int i=0; i < apps.size(); i++){
                    if (id == apps.get(i).getId()){
                        currentAppIndex = i+1;
                        break;
                    }
                }

                List<ExperimentInfo> experiments = viewService.getExperimentByAppId(id);
                logger.info("APP PAGE: FIND " + experiments.size() + " EXPERIMENTS By " + id);

                // --- init sensor data
                Map<Long, Map<Integer, List<SensorInfo>>> sensors = new HashMap<Long, Map<Integer, List<SensorInfo>>>();
                Map<Long, Map<Long, Object>> data = new HashMap<Long, Map<Long, Object>>();
                for (ExperimentInfo experiment : experiments){
                    long expId = experiment.getId();
                    List<SensorInfo> expSensors = viewService.getSensorInfoByExperimentId(expId);

                    Map<Long, Object> sensorData = new HashMap<Long, Object>();
                    for (SensorInfo sensor: expSensors){
                        long sensorId = sensor.getId();
                        if (sensor.getSensorConfig().getType() == 1){
                            sensorData.put(sensorId, sensorDataService.getSensorTimeSeriesDataBySensorId(sensorId, expId));
                        } else if (sensor.getSensorConfig().getType() == 2){
                            sensorData.put(sensorId, sensorDataService.getSensorCameraBySensorId(sensorId, expId));
                        }
                    }
                    data.put(expId, sensorData);
                    sensors.put(expId, viewService.getSensorInfoByExperimentIdPartByType(expId));
                }

                model.addAttribute("sensors", sensors);
                model.addAttribute("currentAppIndex", currentAppIndex);
                model.addAttribute("sensorData", data);
            } else {
                logger.info("APP PAGE: CURRENT USER HAS NO APPS!");
            }*/

            logger.info("APP PAGE: IN APP DATA PAGE!");
        }

        return "app";
    }

    @GetMapping("/device")
    public String device(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // --- get login user name
        String currentUser = viewService.getCurrentLoginUser(request);
        if (currentUser == null) {
            response.sendRedirect("/login");
        }

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
