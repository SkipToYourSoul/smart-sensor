package com.stemcloud.smart.sensor.serversocket;

import com.stemcloud.smart.sensor.config.SocketConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by betty.bao on 2017/7/26.
 */
@Component
public class ServerSocketRunner implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServerSocketRunner.class);

    private String filePath;
    private Socket client;

    @Autowired
    SocketConfig socketConfig;

    public  ServerSocketRunner(){}

    public ServerSocketRunner(Socket client) {
        this.client = client;
    }

    public ServerSocketRunner(Socket client, String filePath) {
        this.client = client;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            System.out.println("thread:" + Thread.currentThread());
            receiveFile(client);
        } catch (Exception e) {
            logger.error("error occurs in client", client.getInetAddress(), e);
            System.out.println("catch error");
            throw new RuntimeException(e);
        }
    }

    private void receiveFile(Socket socket) throws IOException {
        byte[] inputByte = null;
        int length = 0;
        DataInputStream dataInputStream = null;
        FileOutputStream fout = null;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            String fileName = dataInputStream.readUTF();
            System.out.println(socketConfig.getTmpPath());
            System.out.println(fileName);
            fout = new FileOutputStream(new File(filePath + fileName));
            inputByte = new byte[1024];
            System.out.println("开始接收数据...");
            while (true) {
                if (length == -1) {
                    break;
                }
//                System.out.println(length);
                fout.write(inputByte, 0, length);
                fout.flush();
            }
            System.out.println("完成接收");
        } catch (Exception e) {
            logger.error("error occurs when receiving data", e);
        } finally {
            if (fout != null)
                fout.close();
            if (dataInputStream != null)
                dataInputStream.close();
            if (socket != null)
                socket.close();
        }
    }
}
