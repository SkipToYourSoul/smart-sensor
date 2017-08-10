package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.sensor.config.SocketConfig;
import com.stemcloud.smart.sensor.socket.nettyserver.NettyServer;
import com.stemcloud.smart.web.dao.AppInfoRepository;
import com.stemcloud.smart.web.dao.SensorInfoRepository;
import com.stemcloud.smart.web.domain.AppInfo;
import com.stemcloud.smart.web.domain.SensorInfo;
import com.stemcloud.smart.web.service.SensorViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/15
 * Description: main controller of html template
 */
@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    SensorViewService sensorViewService;

    @Autowired
    AppInfoRepository appInfoRepository;

    @Autowired
    SensorInfoRepository sensorInfoRepository;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {

        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContextImpl != null)
            System.out.println("Username:"
                + securityContextImpl.getAuthentication().getName());
        else
            System.out.println("Not login user!");


        model.addAttribute("inIndex", true);
        return "index";
    }

    @GetMapping("/app")
    public String app(@RequestParam(value = "id", required = false) Integer id, Model model){
        // --- get app info according current user
        String currentUser = "liye";
        List<AppInfo> apps = appInfoRepository.findByCreatorOrderByCreateTime(currentUser);
        logger.info("FIND " + apps.size() + " APPS By " + currentUser);

        // add app attribute
        model.addAttribute("apps", apps);
        if (apps.size() == 0)
            return "app";

        // --- check the app id
        int currentAppId = apps.get(0).getId();
        int currentAppIndex = 1;
        if (id != null) {
            Boolean isUserApp = false;
            for (int i=0; i < apps.size(); i++){
                if (id == apps.get(i).getId()){
                    currentAppId = id;
                    currentAppIndex = i+1;
                    isUserApp = true;
                    break;
                }
            }
            if (!isUserApp){
                logger.warn("The app " + id + " does not belong the user " + currentUser);
            }
        }

        // --- get first app's sensor info
        List<SensorInfo> sensors = new ArrayList<SensorInfo>();
        sensors = sensorInfoRepository.findByAppId(currentAppId);
        logger.info("FIND " + sensors.size() + " SENSORS By " + currentUser);

        // --- get sensor attribute
        model.addAttribute("sensors", sensors);

        model.addAttribute("currentAppId", currentAppId);
        model.addAttribute("currentAppIndex", currentAppIndex);

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
}
