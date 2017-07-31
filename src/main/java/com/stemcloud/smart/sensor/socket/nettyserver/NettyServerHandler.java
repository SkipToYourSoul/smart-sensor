package com.stemcloud.smart.sensor.socket.nettyserver;

import com.stemcloud.smart.sensor.dataparser.ParseData;
import com.stemcloud.smart.sensor.exception.ParseDataException;
import com.stemcloud.smart.sensor.pojo.Message;
import com.stemcloud.smart.sensor.utils.DataType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


/**
 * Created by betty.bao on 2017/7/27.
 */
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Autowired
    ParseData dataParser;

    /**
     * 接收来自客户端的数据
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        try {
            if (msg instanceof Message) {
                Message customMsg = (Message) msg;
                String dataType = DataType.getType(customMsg.getDataType() & 0xff);
                doAction(dataType, customMsg.getBody());
                ctx.writeAndFlush(getSendByteBuf("data transfer completed"));
//                System.out.println("Client->Server:"+ctx.channel().remoteAddress()+" send "+customMsg.getBody());
            }

        } catch (EnumConstantNotPresentException e) {
            logger.error("DataType is unsupported !");
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncoding in server !");
        } catch (ParseDataException e) {
            logger.error("error occurs when parsing data");
        } catch (Exception e) {
            logger.error("error occurs when receiving data", e);
        }
    }

    /**
     * 业务分析处理
     *
     * @param dataType
     */
    private void doAction(String dataType, byte[] dataBytes) throws ParseDataException {

//        dataParser.persistDataLocally(dataType, Arrays.copyOfRange(dataBytes, 1, dataBytes.length));
//        new ParseData().persistDataLocally(dataType, Arrays.copyOfRange(dataBytes, 1, dataBytes.length));
        dataParser.persistDataLocally(dataType, dataBytes);
    }

    private ByteBuf getSendByteBuf(String message)
            throws UnsupportedEncodingException {

        byte[] req = message.getBytes("UTF-8");
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);

        return pingMessage;
    }

}
