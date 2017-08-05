package com.stemcloud.smart.sensor.socket.nettyclient;

import com.stemcloud.smart.sensor.pojo.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * parse and deal with data tranferred from server
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(NettyClientHandler.class);
    private ByteBuf msg;

    /**
     * 客户端连接到服务器时即发送数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] fileBytes = readFile("E:/tmp/sensortmp/in/Chrysanthemum.jpg");
        Message customMsg = new Message((byte) 2, fileBytes.length, fileBytes);
        ctx.channel().writeAndFlush(customMsg);
    }

    //文件内容转为字节数组
    private byte[] readFile(String filePath) throws IOException {
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(filePath);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) ;
            return byteBuffer.array();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                logger.error("", e);
            }
            try {
                fs.close();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }

    /**
     * 客户端接收来自服务器的数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        String rev = getMessage(buf);
        System.out.println("收到服务器数据:" + rev);
    }

    private String getMessage(ByteBuf buf) {

        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            return null;
        }
    }
}