package org.lsh.bio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lsh
 * @description:
 */
public class BIOServer {

    public static void main(String[] args) {

        // 1.创建一个线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        // 2.如果有客户端连接，就创建一个线程与之通信
        try (ServerSocket serverSocket = new ServerSocket(6666)) {
            System.out.println("服务器启动了");

            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("连接了一个客户端");
                pool.execute(() -> {
                    try {
                        handler(socket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    // 编写一个handler方法和客户端通信
    public static void handler(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        BufferedInputStream bfs = new BufferedInputStream(inputStream);

        int len = -1;
        System.out.println("线程信息 id " + Thread.currentThread().getId() +
                "，名字=" + Thread.currentThread().getName());
        while ((len = bfs.read(bytes)) != -1) {
            System.out.println("线程信息 id " + Thread.currentThread().getId() +
                    "，名字=" + Thread.currentThread().getName());
            System.out.println(new String(bytes, 0, len));
        }
    }
}
