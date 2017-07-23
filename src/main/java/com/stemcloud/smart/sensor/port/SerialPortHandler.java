package com.stemcloud.smart.sensor.port;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/22
 * Description:
 */
public interface SerialPortHandler {
    /**
     * extract sensor information
     * @return temperature, humidity or other information
     */
    public String informationExtract();
}
