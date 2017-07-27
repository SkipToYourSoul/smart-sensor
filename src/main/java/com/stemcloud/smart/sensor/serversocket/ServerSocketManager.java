package com.stemcloud.smart.sensor.serversocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

//        this.flag = true;
        if (!exec.isPresent() || exec.get().isShutdown()) {
            exec = Optional.ofNullable(Executors.newFixedThreadPool(20));
        }
        if (!server.isPresent() || server.get().isClosed()) {
            try {
                server = Optional.ofNullable(new ServerSocket(port));
//                server.get().setSoTimeout(3000);
            } catch (Exception e) {
                logger.error("" + e);
            }
        }
        while (true) {
            Optional<Socket> client = Optional.ofNullable(null);
            try {
                client = Optional.ofNullable(server.get().accept());
                ServerSocketRunner serviceSocket = new ServerSocketRunner(client.get(), filePath);
                server.get().setSoTimeout(5000);
                exec.get().execute(serviceSocket);
            } catch (Exception e) {
                logger.error("" + e);
                exec.get().shutdown();
            }
        }

    }

    public void stop() {

//        if (!exec.isPresent())
//            return;
//        exec.get().shutdown();
//        try {
//            if (!exec.get().awaitTermination(60, TimeUnit.SECONDS)) {
//                exec.get().shutdownNow();
//                if (!exec.get().awaitTermination(60, TimeUnit.SECONDS))
//                    System.err.println("Pool did not terminate");
//            }
//        } catch (InterruptedException ie) { // (Re-)Cancel if current thread also interrupted
//            exec.get().shutdownNow();
//            Thread.currentThread().interrupt();
//        }

        this.flag = false;
        try {
            if (server.isPresent() && !server.get().isClosed()) {
                server.get().close();
                System.out.println("server closed");
            }
            if (exec.isPresent())
                exec.get().shutdownNow();
        } catch (Exception e) {
            logger.error("" + e);
        }
    }

}
