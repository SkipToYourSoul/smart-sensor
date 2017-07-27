package com.stemcloud.smart.sensor.serversocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettyServerSocket {

    private static volatile NettyServerSocket instance;
    private static volatile Optional<NioEventLoopGroup> bossGroup;
    private static volatile Optional<NioEventLoopGroup> workerGroup;

    private NettyServerSocket() {

    }

    public static NettyServerSocket getIntance() {
        if (instance == null) {
            synchronized (NettyServerSocket.class) {
                if (instance == null) {
                    instance = new NettyServerSocket();
                }
            }
        }
        return instance;
    }

    @PostConstruct
    public void init() {
        bossGroup = Optional.ofNullable(null);
        workerGroup = Optional.ofNullable(null);
    }

    public void bind(int port) throws Exception {

        if (!bossGroup.isPresent() || bossGroup.get().isShutdown())
            bossGroup = Optional.ofNullable(new NioEventLoopGroup());
        if (!workerGroup.isPresent() || workerGroup.get().isTerminated())
            workerGroup = Optional.ofNullable(new NioEventLoopGroup());
        try {
            ServerBootstrap b = new ServerBootstrap();//配置类
            b.group(bossGroup.get(), workerGroup.get())
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception{
                            sc.pipeline().addLast( new NettySocketRunner());
                        }
                    });
            System.out.println("port：%%%%%%" + port);
            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.get().shutdownGracefully();
            bossGroup.get().shutdownGracefully();
        }
    }

    public void unbind() {

        if (bossGroup.isPresent())
            bossGroup.get().shutdownGracefully();
        if (workerGroup.isPresent())
            workerGroup.get().shutdownGracefully();
    }
}
