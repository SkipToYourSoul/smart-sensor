package com.stemcloud.smart.sensor.utils;

import org.apache.commons.lang.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by betty.bao on 2017/7/28.
 */
public class RandomStrGenarator {

    /**
     * generate random file name
     * example: 20170728-dXSy-fjHV-AbT1n4nY
     * @return
     */
    public static String createRandomFileName(){

        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        sb.append(simpleDateFormat.format(new Date()));
        sb.append("-");
        sb.append(RandomStringUtils.randomAlphanumeric(4));
        sb.append("-");
        sb.append(RandomStringUtils.randomAlphanumeric(4));
        sb.append("-");
        sb.append(RandomStringUtils.randomAlphanumeric(8));
//        System.out.println(sb.toString());
        return sb.toString();
    }

    public static void main(String[] args) {
        createRandomFileName();
    }
}
