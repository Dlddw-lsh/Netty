package org.lsh.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author lsh
 * @description:
 */
public class NewIOServer {

    public static void main(String[] args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(7001);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(address);

        // 创建Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel accept = serverSocketChannel.accept();
            int readCount = 0;
            while (readCount != -1) {
                readCount = accept.read(byteBuffer);
                byteBuffer.rewind(); // 倒带 position = 0 mark作废
            }
        }
    }
}
