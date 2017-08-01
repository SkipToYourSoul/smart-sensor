package com.stemcloud.smart.sensor.socket.nettyserver;

import com.stemcloud.smart.sensor.config.SocketConfig;
import com.stemcloud.smart.sensor.socket.idle.AcceptorIdleStateTrigger;
import com.stemcloud.smart.sensor.socket.protocol.ServerDecode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * socket服务器端
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettyServer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    AcceptorIdleStateTrigger idleStateTrigger;
    @Autowired
    SocketConfig socketConfig;
    @Autowired
    NettyServerHandler nettyServerHandler;
    @Autowired
    ServerDecode serverDecode;

    //    private Channel channel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;

    @Override
    public void run(String... strings) throws Exception {
        try {
            runServer(socketConfig.getPort());
        } catch (InterruptedException e) {
            System.out.println("Server start failure." + e);
            logger.error("Server Start Failure. ->" + e.getMessage(), e);
        }
    }

    /**
     * socket服务器配置
     *
     * @param port
     * @throws InterruptedException
     */
    private void runServer(final int port) throws InterruptedException {
        try {

            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)  // UDP NioDatagramChannel.class
                    .option(ChannelOption.SO_BACKLOG, 1024) //连接数
                    .option(ChannelOption.TCP_NODELAY, true)  //不延迟，消息立即发送
                    .option(ChannelOption.SO_REUSEADDR, true) // 端口重用
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline channelPipeline = ch.pipeline();
                            channelPipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                            channelPipeline.addLast(idleStateTrigger);
                            channelPipeline.addLast(serverDecode);  //服务器端数据报文解析协议
                            channelPipeline.addLast(new ChunkedWriteHandler()); //支持异步发送大的码流，但不会占用过多的内存，防止发生java内存溢出
                            channelPipeline.addLast(nettyServerHandler);
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("Welcome to Server... " + port);
            logger.info("============== init netty server success ===============");
            logger.info("start server at port: " + port);
            future.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
