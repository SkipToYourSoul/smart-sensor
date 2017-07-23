package com.stemcloud.smart.sensor.port;

import com.stemcloud.smart.sensor.utils.Transfer;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/22
 * Description:
 */
public class SerialPortListener implements SerialPortEventListener {
    private SerialPort serialPort = null;

    public SerialPortListener(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI: // 10 通讯中断

            case SerialPortEvent.OE: // 7 溢位（溢出）错误

            case SerialPortEvent.FE: // 9 帧错误

            case SerialPortEvent.PE: // 8 奇偶校验错误

            case SerialPortEvent.CD: // 6 载波检测

            case SerialPortEvent.CTS: // 3 清除待发送数据

            case SerialPortEvent.DSR: // 4 待发送数据准备好了

            case SerialPortEvent.RI: // 5 振铃指示

            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
                break;

            case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
                byte[] data = SerialPortManager.readFromPort(serialPort);
                System.out.println(Transfer.bytesToHexString(data));
                break;
        }
    }
}
