package com.stemcloud.smart.sensor.serversocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by betty.bao on 2017/7/26.
 */

public class ServerSocketManager {
    private static final Logger logger = LoggerFactory.getLogger(ServerSocketManager.class);

    private static volatile ServerSocketManager instance;
    private static volatile Optional<ServerSocket> server = Optional.ofNullable(null);
    private static volatile Optional<ExecutorService> exec = Optional.ofNullable(null);
    private static volatile boolean flag;

    private ServerSocketManager() {

    }

    public static ServerSocketManager getIstance() {
        if (instance == null) {
            synchronized (ServerSocketManager.class) {
                if (instance == null) {
                    instance = new ServerSocketManager();
                }
            }
        }
        return instance;
    }

    public void start(int port, String filePath) {

        this.flag = true;
        if (!exec.isPresent() || exec.get().isShutdown()) {
            exec = Optional.ofNullable(Executors.newFixedThreadPool(20));
        }
        if (!server.isPresent() || server.get().isClosed()) {
            try {
                server = Optional.ofNullable(new ServerSocket(port));
            } catch (Exception e) {
                logger.error("" + e);
            }
        }
        while (flag) {
            Optional<Socket> client = Optional.ofNullable(null);
            try {
                client = Optional.ofNullable(server.get().accept());
                ServerSocketRunner serviceSocket = new ServerSocketRunner(client.get(), filePath);
                exec.get().execute(serviceSocket);
            } catch (Exception e) {
                logger.error("" + e);
            } finally {
                try {
                    if (client.isPresent())
                        client.get().close();  //与一个客户通信结束后, 要关闭Socket
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void stop() {

        this.flag = false;
        try {
//            if (server.isPresent()) {
//                server.get().close();
//                System.out.println("server closed");
//            }
            if (exec.isPresent())
                exec.get().shutdownNow();
        } catch (Exception e) {
            logger.error("" + e);
        }
    }

}
