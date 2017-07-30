package com.stemcloud.smart.web.controller;

import com.stemcloud.smart.sensor.config.SocketConfig;
import com.stemcloud.smart.sensor.socket.nettyserver.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/15
 * Description: main controller of html template
 */
@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private SocketConfig socketConfig;
    @Autowired
    NettyServer socket;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/app")
    public String app(){
        return "app";
    }

    @GetMapping(value = "/nav")
    public String loadNavModel() {
        return "module/nav";
    }

    @ResponseBody
    @RequestMapping(value = "/startsocket", method = RequestMethod.GET)
    public void startSocket() {

        socket.start();
    }

    @ResponseBody
    @RequestMapping(value = "/stopsocket", method = RequestMethod.GET)
    public void stopSocket() {

        socket.stop();
    }
}
