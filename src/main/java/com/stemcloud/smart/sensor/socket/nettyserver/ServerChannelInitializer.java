package com.stemcloud.smart.sensor.socket.nettyserver;

import com.stemcloud.smart.sensor.socket.idle.AcceptorIdleStateTrigger;
import com.stemcloud.smart.sensor.socket.protocol.ServerDecode;
import io.netty.channel.ChannelHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("serverChannel")
public class ServerChannelInitializer implements ChannelHandlerHolder {

    @Autowired
    AcceptorIdleStateTrigger idleStateTrigger;
    @Autowired
    NettyServerHandler nettyServerHandler;

    @Override
    public ChannelHandler[] handlers() {
        return new ChannelHandler[]{
                new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS),
                idleStateTrigger,
                new ServerDecode(),
                new ChunkedWriteHandler(),
                nettyServerHandler
        };
    }
}