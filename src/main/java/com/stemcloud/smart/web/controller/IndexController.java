package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.web.service.AppManagementDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 */
@RestController
@RequestMapping("/")
public class IndexController {
    @Autowired
    AppManagementDataService appManagementDataService;
}
