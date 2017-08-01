package com.stemcloud.smart.sensor.socket.nettyserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/1
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NettyServerTest {
    @Autowired
    NettyServer nettyServer;

    @Test
    public void serverTest(){
        nettyServer.start();
    }
}