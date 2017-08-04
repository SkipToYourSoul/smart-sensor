package com.stemcloud.smart.sensor.socket.connectionpool;

import com.stemcloud.smart.sensor.socket.nettyclient.NettyClientHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by betty.bao on 2017/8/1.
 */
public class ClientPoolHandler implements ChannelPoolHandler {


    @Override
    public void channelReleased(Channel ch) throws Exception {
        System.out.println("channelReleased");
    }


    @Override
    public void channelAcquired(Channel ch) throws Exception {
        System.out.println("channelAcquired");
    }

    @Override
    public void channelCreated(Channel ch) throws Exception {
        System.out.println("channelCreated");
        NioSocketChannel channel = (NioSocketChannel) ch;
        channel.config().setKeepAlive(true);
        channel.config().setTcpNoDelay(true);
        ChannelPipeline pipeline = channel.pipeline();
        NettyClientHandler handler = new NettyClientHandler();
        pipeline.addLast(handler);
    }
}
