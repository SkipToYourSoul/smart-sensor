package com.stemcloud.smart.sensor.socket.nettyserver;

import com.stemcloud.smart.sensor.dataparser.ParseData;
import com.stemcloud.smart.sensor.exception.ParseDataException;
import com.stemcloud.smart.sensor.pojo.Message;
import com.stemcloud.smart.sensor.utils.DataType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by betty.bao on 2017/7/27.
 */
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

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
            }

        } catch (EnumConstantNotPresentException e) {
            logger.error("DataType is unsupported !");
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

}
