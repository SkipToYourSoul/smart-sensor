package com.stemcloud.smart.sensor.clientsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * Created by betty.bao on 2017/7/27.
 */
public class BasicClientSocket {
    private static final Logger logger = LoggerFactory.getLogger(BasicClientSocket.class);

    protected int length = 0;
    protected byte[] sendByte = null;
    protected Socket socket = null;
    protected DataOutputStream dataOutputStream = null;
    protected FileInputStream fin = null;

    protected Boolean isServerClose(Socket socket){
        try{
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        }catch(Exception se){
            return true;
        }
    }

    public void transferData(String filePath) { // 连接套接字方法
        try {
            try {
                socket = new Socket("127.0.0.1", 5879);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                File file = new File(filePath);
                fin = new FileInputStream(file);
                sendByte = new byte[1024];
                dataOutputStream.writeUTF(file.getName());
                System.out.println(file.getName());
                while ((length = fin.read(sendByte, 0, sendByte.length)) > 0) {
                    dataOutputStream.write(sendByte, 0, length);
                    dataOutputStream.flush();
                }
                System.out.println(file.getName() + " finished");
            } catch (Exception e) {
                logger.error("" + e);
            } finally {
                if (dataOutputStream != null)
                    dataOutputStream.close();
                if (fin != null)
                    fin.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
