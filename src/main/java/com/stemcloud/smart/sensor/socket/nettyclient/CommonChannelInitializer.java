package com.stemcloud.smart.sensor.socket.nettyclient;

import com.stemcloud.smart.sensor.socket.nettyserver.ChannelHandlerHolder;
import com.stemcloud.smart.sensor.socket.protocol.ClientEncode;
import io.netty.channel.ChannelHandler;

/**
 * Created by betty.bao on 2017/8/2.
 */
public class CommonChannelInitializer implements ChannelHandlerHolder {

    @Override
    public ChannelHandler[] handlers() {
        return new ChannelHandler[]{
                new ClientEncode(),
                new NettyClientHandler()
        };
    }
}
