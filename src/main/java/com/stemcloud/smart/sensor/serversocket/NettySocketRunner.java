package com.stemcloud.smart.sensor.serversocket;

import com.stemcloud.smart.sensor.config.SocketConfig;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;

/**parse and deal with data tranferred from client
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettySocketRunner extends ChannelInboundHandlerAdapter {

    @Autowired
    SocketConfig socketConfig;

    /**
     * get msg from client automatically
     * @param ctx
     * @param msg
     * @throws Exception
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        FileOutputStream fout = new FileOutputStream(socketConfig.getTmpPath());
        fout.write(req);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
