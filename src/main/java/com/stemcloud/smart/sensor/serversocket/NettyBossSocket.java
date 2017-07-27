package com.stemcloud.smart.sensor.serversocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettyBossSocket {

    private static volatile Optional<NettyBossSocket> instance = Optional.ofNullable(null);
    private static volatile Optional<NioEventLoopGroup> bossGroup = Optional.ofNullable(null);
    private static volatile Optional<NioEventLoopGroup> workerGroup = Optional.ofNullable(null);

    public void bind(int port) throws Exception {

        if (!bossGroup.isPresent() || bossGroup.get().isShutdown())
            bossGroup = Optional.ofNullable(new NioEventLoopGroup());
        if (!workerGroup.isPresent() || workerGroup.get().isTerminated())
            workerGroup = Optional.ofNullable(new NioEventLoopGroup());
        ServerBootstrap b = new ServerBootstrap();//配置类
        b.group(bossGroup.get(), workerGroup.get())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new NettyBossRunner());
                    }
                });
        ChannelFuture f = b.bind(port).sync();

        f.channel().closeFuture().sync();
    }

    public void unbind() {

        if (bossGroup.isPresent())
            bossGroup.get().shutdownGracefully();
        if (workerGroup.isPresent())
            workerGroup.get().shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        NettyBossSocket socket = new NettyBossSocket();
        socket.bind(6666);
    }
}
