package com.stemcloud.smart.sensor.clientsocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

/**
 * parse and deal with data tranferred from server
 * Created by betty.bao on 2017/7/27.
 */
@Component
public class NettyWorkerRunner extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(NettyWorkerRunner.class.getName());
    private String filePath = "E:\\tmp\\sensortmp\\in\\ClientSocketFrame.java";

    /**
     * send msg to server automatically
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        try {
            FileInputStream fin = new FileInputStream(filePath);
            byte[] bytes = new byte[fin.available()];
            System.out.println("size*********:" + bytes.length);
//            ByteBuf data = Unpooled.wrappedBuffer(bytes);
            ByteBuf poolBuffer = PooledByteBufAllocator.DEFAULT.directBuffer(1024);
            poolBuffer.writeBytes(bytes);
            ctx.writeAndFlush(poolBuffer);
            poolBuffer.release();
        } catch (Exception e) {
            logger.error("" + e);
        }
//        try {
//            FileInputStream fin = new FileInputStream(filePath);
//            System.out.println("WTFFFGGGGGGGGGGGGGGGG");
//            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
//            String line;
//            ByteBuf data = ctx.alloc().buffer(fin.available());
//            while((line = br.readLine()) != null){
//                data.writeBytes(line.getBytes());
//            }
////            encoded.writeBytes(msg.getBytes());
//            ctx.write(data);
//            ctx.flush();
//            ctx.writeAndFlush(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * read meg from server automatically
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        logger.info("NettyWorkerRunner.channelRead");
        ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);
        System.out.println("Server info:" + new String(bytes));
        result.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warn("Unexpected exception from downstream : "
                + cause.getMessage());
        ctx.close();
    }
}