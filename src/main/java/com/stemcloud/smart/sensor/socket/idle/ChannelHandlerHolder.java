package com.stemcloud.smart.sensor.socket.idle;

import io.netty.channel.ChannelHandler;

/**
 * Created by betty.bao on 2017/8/1.
 */
public interface ChannelHandlerHolder {

    ChannelHandler[] handlers();
}
