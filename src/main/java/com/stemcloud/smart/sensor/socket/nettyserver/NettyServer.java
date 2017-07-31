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

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

    private ServerBootstrap bootstrap;

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
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)  // UDP NioDatagramChannel.class
                    .option(ChannelOption.SO_BACKLOG, 1024) //连接数
                    .option(ChannelOption.TCP_NODELAY, true)  //不延迟，消息立即发送
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline channelPipeline = ch.pipeline();
                            try{
                                channelPipeline.addLast(serverDecode);  //服务器端数据报文解析协议
                                channelPipeline.addLast(new ChunkedWriteHandler()); //支持异步发送大的码流，但不会占用过多的内存，防止发生java内存溢出
                                channelPipeline.addLast(nettyServerHandler);
                            }catch (ChannelPipelineException e){
                                logger.info("-----------server is already on--------------");
                            }
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

    /**
     * start socket server
     */
    public void start() {

        try {
            if (!isPortUsing("localhost", socketConfig.getPort())) {
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
            }
        } catch (UnknownHostException e) {
            logger.error("Host is Unknown", e);
        }
    }

    /**
     * stop socket server
     */
    public void stop() {

        if (bossGroup != null)
            bossGroup.shutdownGracefully();
        if (workerGroup != null)
            workerGroup.shutdownGracefully();
        bossGroup = null;
        workerGroup = null;
        bootstrap = null;
        logger.info("stop socket server at port: " + socketConfig.getPort());
    }


    /**
     * check whether port is bound
     *
     * @param host
     * @param port
     * @return
     * @throws UnknownHostException
     */
    private boolean isPortUsing(String host, int port) throws UnknownHostException {
        boolean flag = false;
        InetAddress theAddress = InetAddress.getByName(host);
        try {
            new Socket(theAddress, port);
            flag = true;
            logger.error("-----------port has already been used--------------");
        } catch (IOException e) {
            return flag;
        }
        return flag;
    }

    public static void main(String[] args) throws Exception {
        NettyServer socket = new NettyServer();
        socket.start();
    }
}
