package com.stemcloud.smart.sensor.socket;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description: singleton connector of flink StreamExecutionEnvironment
 */
public class SocketConnector {
    private SocketConnector() {
    }

    private static volatile SocketConnector instance;

    public static SocketConnector getInstance(){
        if (instance == null){
            synchronized (SocketConnector.class){
                if (instance == null)
                    instance = new SocketConnector();
            }
        }
        return instance;
    }

    public StreamExecutionEnvironment getStreamExecutionEnvironment(){
        return StreamExecutionEnvironment.getExecutionEnvironment();
    }
}
