package com.stemcloud.smart.sensor.dataparser;

import com.stemcloud.smart.sensor.config.SocketConfig;
import com.stemcloud.smart.sensor.exception.ParseDataException;
import com.stemcloud.smart.sensor.utils.RandomStrGenarator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 服务器端解析数据
 *
 * Created by betty.bao on 2017/7/28.
 */
@Component
public class ParseData {

    private static final Logger logger = LoggerFactory.getLogger(ParseData.class);

    @Autowired
    SocketConfig socketConfig;

    /**
     * 存储文件到本地磁盘
     *
     * @param dataType
     * @param dataBytes
     */
    public void persistDataLocally(String dataType, byte[] dataBytes) throws ParseDataException {
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        File file;
        try {
            File dir = new File(socketConfig.getTmpPath());
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(socketConfig.getTmpPath() + File.separator
                    + RandomStrGenarator.createRandomFileName() + "." + dataType);
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(dataBytes);
        } catch (Exception e) {
            throw new ParseDataException("error occurs when parsing data");
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e1) {
                    logger.error("bufferedOutputStream close exception !", e1);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e1) {
                    logger.error("fileOutputStream close exception !", e1);
                }
            }
        }
    }
}
