package com.stemcloud.smart.sensor.nettyclient;

import com.stemcloud.smart.sensor.protocol.ClientEncode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;


/**
 * socket客户端
 *
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettyClient {

    /**
     * 客户端socket配置
     *
     * @param port
     * @param host
     * @throws Exception
     */
    public void connect(int port, String host) throws Exception {

        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {

                            ch.pipeline().addLast(new ClientEncode());  //客户端报文编码协议
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            ChannelFuture future = b.connect(host, port).sync();
            if (future.isSuccess())
                System.out.println("----------------connect server success----------------");

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 5879;
        new NettyClient().connect(port, "127.0.0.1");
    }
}
