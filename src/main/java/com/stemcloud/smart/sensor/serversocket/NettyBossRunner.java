package com.stemcloud.smart.sensor.serversocket;

import com.stemcloud.smart.sensor.config.SocketConfig;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.Optional;

/**
 * parse and deal with data tranferred from client
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettyBossRunner extends ChannelInboundHandlerAdapter {

    @Autowired
    SocketConfig socketConfig;

    /**
     * get msg from client automatically
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        Optional<FileOutputStream> fout = Optional.ofNullable(null);
        try {
            byte[] req = new byte[buf.readableBytes()];
            fout = Optional.ofNullable(new FileOutputStream("E:/tmp/file.txt"));
//            fout = Optional.ofNullable(new FileOutputStream(socketConfig.getTmpPath()));
            fout.get().write(req);
            fout.get().flush();
        } finally {
            if(buf != null)
                buf.release();
            if(fout.isPresent())
                fout.get().close();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
