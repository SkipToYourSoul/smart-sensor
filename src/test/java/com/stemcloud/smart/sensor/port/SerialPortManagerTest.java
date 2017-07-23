package com.stemcloud.smart.sensor.port;

import gnu.io.SerialPort;
import org.junit.Test;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/23
 * Description:
 */
public class SerialPortManagerTest {
    @Test
    public void portTest(){
        for (String str : SerialPortManager.findPort()){
            System.out.println(str);
        }

        String portName = "COM4";
        int baudrate = 115200;

        SerialPort serialPort = SerialPortManager.openPort(portName, baudrate);
        SerialPortManager.addListener(serialPort, new SerialPortListener(serialPort));

        SerialPortManager.closePort(serialPort);
    }
}