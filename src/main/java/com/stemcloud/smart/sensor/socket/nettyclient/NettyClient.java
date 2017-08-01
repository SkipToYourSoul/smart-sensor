package com.stemcloud.smart.sensor.socket.nettyclient;

import com.stemcloud.smart.sensor.socket.idle.ConnectionWatchdog;
import com.stemcloud.smart.sensor.socket.idle.ConnectorIdleStateTrigger;
import com.stemcloud.smart.sensor.socket.protocol.ClientEncode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * socket客户端
 * <p>
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettyClient {

    private static final Logger logger = Logger.getLogger(NettyClient.class);

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    private final ConnectorIdleStateTrigger idleStateTrigger = new ConnectorIdleStateTrigger();

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
            Bootstrap boot = new Bootstrap();
            boot.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024).handler(new LoggingHandler(LogLevel.INFO));

            final ConnectionWatchdog watchdog = new ConnectionWatchdog(boot, timer, port, host, true) {

                public ChannelHandler[] handlers() {
                    return new ChannelHandler[]{
                            this,
                            new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                            idleStateTrigger,
                            new ClientEncode(),
                            new NettyClientHandler()
                    };
                }
            };

            ChannelFuture future;
            //进行连接
            try {
                synchronized (boot) {
                    boot.handler(new ChannelInitializer<Channel>() {

                        //初始化channel
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(watchdog.handlers());
                        }
                    });

                    future = boot.connect(host, port);
                }
                future.sync();
//                ChannelFuture future = b.connect(host, port).sync();
                if (future.isSuccess())
                    System.out.println("----------------connect server success----------------");
            } catch (Throwable t) {
                logger.error("connects to fails");
            }
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 5879;
        new NettyClient().connect(port, "127.0.0.1");
    }
}
