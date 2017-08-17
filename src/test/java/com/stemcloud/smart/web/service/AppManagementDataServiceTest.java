package com.stemcloud.smart.web.service;

import com.stemcloud.smart.web.dao.AppInfoRepository;
import com.stemcloud.smart.web.dao.SensorInfoRepository;
import com.stemcloud.smart.web.domain.AppInfo;
import com.stemcloud.smart.web.domain.SensorInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/10
 * Description: generate some test data
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppManagementDataServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AppInfoRepository appInfoRepository;

    @Autowired
    SensorInfoRepository sensorInfoRepository;

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    SensorDataService sensorDataService;

    @Test
    public void generateTestApp(){
        AppInfo appInfo = new AppInfo();
        appInfo.setName("app");
        appInfo.setCreator("liye");
        appInfo.setDescription("description");

        long appId = appInfoRepository.save(appInfo).getId();
        logger.info("New app: " + appId);

        SensorInfo sensorInfo = new SensorInfo();
        sensorInfo.setName("sensor");
        sensorInfo.setCode("0932");
        sensorInfo.setCreator("liye");
        sensorInfo.setType(1);
        sensorInfo.setAppId(appId);

        long sensorId = sensorInfoRepository.save(sensorInfo).getId();
        logger.info("New sensor: " + sensorId);
    }

    @Test
    public void generateUser(){
        userManagementService.registerUser("teacher","teacher","", "ROLE_TEACHER");
    }

    @Test
    public void initWhenFirstRunApplication(){
        userManagementService.initRoleResource();
    }

    @Test
    public void generateRandomData() {
        sensorDataService.generateRandomData();
    }
}