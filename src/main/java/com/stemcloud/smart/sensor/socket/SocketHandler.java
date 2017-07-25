package com.stemcloud.smart.sensor.socket;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description:
 */
public class SocketHandler {
    public static void handle() throws Exception {
        StreamExecutionEnvironment env = SocketConnector.getInstance().getStreamExecutionEnvironment();
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 10000));
        env.getConfig().disableSysoutLogging();

        DataStream<String> dataStream = env.socketTextStream("10.121.84.90", 9999);
        dataStream.map(
            new RichMapFunction<String, String>() {
                @Override
                public void open(Configuration conf) throws Exception {
                    super.open(conf);
                    // --- connect redis here
                }

                @Override
                public String map(String s) throws Exception {
                    // --- handle data here
                    return s.toUpperCase();
                }

                @Override
                public void close() throws Exception {
                    super.close();
                }
            }
        ).print();
        env.execute();
    }
}
