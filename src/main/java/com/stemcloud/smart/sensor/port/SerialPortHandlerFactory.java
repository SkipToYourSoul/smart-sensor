package com.stemcloud.smart.sensor.port;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/22
 * Description:
 */
public class SerialPortHandlerFactory {
    private enum SensorType { DL_LN33; }

    public static SerialPortHandler newInstance(SensorType type){
        switch (type){
            case DL_LN33:
                return new SensorDL_LN33();
            default:
                return new SensorDL_LN33();
        }
    }
}

class SensorDL_LN33 implements SerialPortHandler{

    @Override
    public String informationExtract() {
        return null;
    }
}
