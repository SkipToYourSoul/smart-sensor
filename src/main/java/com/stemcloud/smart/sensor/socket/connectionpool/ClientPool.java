package com.stemcloud.smart.sensor.socket.connectionpool;

import com.stemcloud.smart.sensor.socket.nettyclient.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by betty.bao on 2017/8/1.
 */
public class ClientPool {
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private final Bootstrap bs = new Bootstrap();
    private FixedChannelPool fixpool;
    private InetSocketAddress remoteaddress;

    public ClientPool(String host, int port, int maxconnect) {
        bs.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        remoteaddress = InetSocketAddress.createUnresolved(host, port);
        bs.remoteAddress(remoteaddress);
        fixpool = new FixedChannelPool(bs, new ClientPoolHandler(), maxconnect);
    }

    //申请连接，没有申请到(或者网络断开)，返回null
    public Channel acquire(int seconds) {
        try {
            Future<Channel> fch = fixpool.acquire();
            Channel ch = fch.get(seconds, TimeUnit.SECONDS);
            return ch;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //释放连接
    public void release(Channel channel) {
        try {
            if (channel != null) {
                fixpool.release(channel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NettyClientHandler getChannelHandler(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        NettyClientHandler ret = (NettyClientHandler)(pipeline.last());
        return ret;
    }

    //单元测试
    public static void main(String[] args) {
        try {
            int maxconnect = 10;
            ClientPool pool = new ClientPool("127.0.0.1", 5879, maxconnect);
            Channel ch = pool.acquire(5);
            pool.release(ch);

            Channel[] chlist = new Channel[maxconnect];
            for(int i=0; i<maxconnect; i++) {
                chlist[i] = pool.acquire(1);
            }
            for(int i=0; i<maxconnect; i++) {
                pool.release(chlist[i]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
