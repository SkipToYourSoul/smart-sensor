package com.stemcloud.smart.sensor.port;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/22
 * Description:
 */
public class SerialPortManager {
    /**
     * get port name list
     * @return port name list, like 'COM4'
     */
    public static ArrayList<String> findPort(){
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<String>();

        while (portList.hasMoreElements()){
            portNameList.add(portList.nextElement().getName());
        }

        return portNameList;
    }

    /**
     * open a port with port name and baudrate
     * @param portName
     * @param baudrate
     * @return
     */
    public static SerialPort openPort(String portName, int baudrate){
        try {
            // identify port
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

            // open port
            CommPort commPort = portIdentifier.open(portName, 2000);

            if (commPort instanceof SerialPort){
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(baudrate,
                        SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                return serialPort;
            }
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * close serial port
     * @param serialPort
     */
    public static void closePort(SerialPort serialPort){
        if (serialPort != null){
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * send byte[] to port
     * @param serialPort
     * @param order
     */
    public static void sendToPort(SerialPort serialPort, byte[] order) {
        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * read from port
     * @param serialPort
     * @return
     */
    public static byte[] readFromPort(SerialPort serialPort) {
        InputStream in = null;
        byte[] bytes = null;
        try {
            in = serialPort.getInputStream();
            int buffLength = in.available();
            while (buffLength != 0) {
                bytes = new byte[buffLength];
                in.read(bytes);
                buffLength = in.available();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * add listener for port, run in separate thread
     * @param serialPort
     * @param listener
     */
    public static void addListener(SerialPort serialPort, SerialPortEventListener listener){
        try {
            serialPort.addEventListener(listener);
            serialPort.notifyOnDataAvailable(true);
            serialPort.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }
}
