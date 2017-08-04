package com.stemcloud.smart.sensor.socket.protocol;

import com.stemcloud.smart.sensor.exception.ProtocolException;
import com.stemcloud.smart.sensor.pojo.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 客户端报文编码协议
 * Created by betty.bao on 2017/7/30.
 */
public class ClientEncode extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf out) throws Exception {

        if(null == message){
            throw new ProtocolException("message is null");
        }
        byte[] body = message.getBody();
        out.writeByte(message.getDataType());
        out.writeInt(body.length);
        out.writeBytes(body);
    }
}