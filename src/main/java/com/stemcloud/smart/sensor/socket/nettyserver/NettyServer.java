package com.stemcloud.smart.sensor.socket.nettyserver;

import com.stemcloud.smart.sensor.config.SocketConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * socket服务器端
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettyServer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    SocketConfig socketConfig;
    @Autowired
    @Qualifier("serverChannel")
    ChannelHandlerHolder channelHandlerHolder;

    //    private Channel channel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;

    @Override
    public void run(String... strings) throws Exception {
        /*try {
            runServer(socketConfig.getPort());
        } catch (InterruptedException e) {
            System.out.println("Server start failure." + e);
            logger.error("Server Start Failure. ->" + e.getMessage(), e);
        }*/
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
                    .childHandler(new ChannelInitializer<Channel>() {

                        //初始化channel
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(channelHandlerHolder.handlers());
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
