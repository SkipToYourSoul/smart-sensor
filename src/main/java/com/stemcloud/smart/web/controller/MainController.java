package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.web.dao.AppInfoRepository;
import com.stemcloud.smart.web.dao.SensorInfoRepository;
import com.stemcloud.smart.web.domain.AppInfo;
import com.stemcloud.smart.web.domain.SensorInfo;
import com.stemcloud.smart.web.service.AppManagementDataService;
import com.stemcloud.smart.web.service.ViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/15
 * Description: main controller of html template
 */
@Controller
public class MainController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    private final ViewService viewService;
    private final AppManagementDataService appManagementDataService;

    @Autowired
    public MainController(ViewService viewService, AppManagementDataService appManagementDataService) {
        this.viewService = viewService;
        this.appManagementDataService = appManagementDataService;
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
    public String app(@RequestParam(value = "id", required = false, defaultValue = "0") Integer id, Model model,
                      HttpServletRequest request, HttpServletResponse response) throws IOException {
        // --- get login user name
        String currentUser = viewService.getCurrentLoginUser(request);
        if (currentUser == null)
            response.sendRedirect("/login");

        // --- get app info according current user
        List<AppInfo> apps = viewService.getAppInfoByCurrentUser(currentUser);
        logger.info("FIND " + apps.size() + " APPS By " + currentUser);

        // ---
        if (apps.size() != 0){
            long currentAppId = apps.get(0).getId();
            int currentAppIndex = 1;
            if (viewService.isAppBelongCurrentUser(id, currentUser)){
                for (int i=0; i < apps.size(); i++){
                    if (id == apps.get(i).getId()){
                        currentAppId = id;
                        currentAppIndex = i+1;
                        break;
                    }
                }
            } else {
                logger.warn("The user: " + currentUser + " has not the app " + id);
                response.sendRedirect("/app?id=" + currentAppId);
            }

            // --- get first app's sensor info
            List<SensorInfo> sensors = viewService.getSensorInfoByAppId(currentAppId);
            logger.info("FIND " + sensors.size() + " SENSORS By " + currentUser);

            // --- get attribute
            model.addAttribute("apps", apps);
            model.addAttribute("sensors", sensors);
            model.addAttribute("currentAppId", currentAppId);
            model.addAttribute("currentAppIndex", currentAppIndex);
        }

        model.addAttribute("inApp", true);
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
}
