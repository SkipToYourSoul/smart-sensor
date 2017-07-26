package com.stemcloud.smart.sensor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by betty.bao on 2017/7/26.
 */
@Component
@ConfigurationProperties(prefix = "serversocket")
public class SocketConfig {

    private int port;
    private String tmpPath;

    public String getTmpPath() {
        return tmpPath;
    }

    public void setTmpPath(String tmpPath) {
        this.tmpPath = tmpPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
