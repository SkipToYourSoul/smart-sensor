package com.stemcloud.smart.sensor.serversocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by betty.bao on 2017/7/27.
 */

public abstract class AbstractNettyServer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractNettyServer.class);

    private static Map<Integer, AbstractNettyServer> generatorMap = new HashMap<Integer, AbstractNettyServer>();

    protected void register(Integer chartType, AbstractNettyServer generator){
        generatorMap.put(chartType,generator);
    }

    public abstract void bind(int port);

    public abstract void unbind();

}
