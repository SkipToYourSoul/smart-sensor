package com.stemcloud.smart.sensor.socket.nettyserver;

import com.stemcloud.smart.sensor.config.SocketConfig;
import com.stemcloud.smart.sensor.socket.protocol.ServerDecode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * socket服务器端
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    SocketConfig socketConfig;
    @Autowired
    NettyServerHandler nettyServerHandler;
    @Autowired
    ServerDecode serverDecode;

//    private Channel channel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

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
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)  // UDP NioDatagramChannel.class
                    .option(ChannelOption.SO_BACKLOG, 1024) //连接数
                    .option(ChannelOption.TCP_NODELAY, true)  //不延迟，消息立即发送
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline channelPipeline = ch.pipeline();
                            channelPipeline.addLast(serverDecode);  //服务器端数据报文解析协议
                            //支持异步发送大的码流，但不会占用过多的内存，防止发生java内存溢出
                            channelPipeline.addLast(new ChunkedWriteHandler());
                            channelPipeline.addLast(nettyServerHandler);
                        }
                    });

            ChannelFuture future = b.bind(port).sync();
//        channel = b.bind(port).sync().channel();
            System.out.println("Welcome to Server... " + port);
            logger.info("============== init netty server success ===============");
            logger.info("start server at port: " + port);

            future.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * start socket server
     */

    public void start() {

        new Thread(new Runnable() {
            public void run() {
                try {
                    runServer(socketConfig.getPort());
                } catch (InterruptedException e) {
                    System.out.println("Server start failure." + e);
                    logger.error("Server Start Failure. ->" + e.getMessage(), e);
                }
            }
        }).start();
//            run(socketConfig.getPort());
//            run(6666);

    }

    /**
     * stop socket server
     */
    public void stop() {

        if (bossGroup != null)
            bossGroup.shutdownGracefully();
        if (workerGroup != null)
            workerGroup.shutdownGracefully();
//        channel.closeFuture().syncUninterruptibly();
        bossGroup = null;
        workerGroup = null;
//        channel = null;
        logger.info("stop socket server at port: " + socketConfig.getPort());
    }

    public static void main(String[] args) throws Exception {
        NettyServer socket = new NettyServer();
        socket.start();
    }
}