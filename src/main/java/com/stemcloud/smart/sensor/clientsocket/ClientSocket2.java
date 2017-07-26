package com.stemcloud.smart.sensor.clientsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by betty.bao on 2017/7/26.
 */
public class ClientSocket2 extends BasicClientSocket{

    private static final Logger logger = LoggerFactory.getLogger(ClientSocket2.class);

    public static void main(String[] args) {
        BasicClientSocket socket = new ClientSocket();
        socket.transferData("E:\\tmp\\sensortmp\\in\\Chrysanthemum.jpg");
    }
}
