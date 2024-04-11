package org.lsh.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author lsh
 * @description: Scatting: 将数据写入到Buffer时，可以采用buffer数组，依次写入
 * Gathering: 从Buffer读取数据时，可以采用Buffer数组，依次读
 */
public class ScattingAndGatheringTest {

    public static void main(String[] args) throws Exception {
        // 使用ServerSocketChannel和SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到Socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建Buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLen = 8;

        // 循环的读取
        while (true) {
            long byteRead = 0;
            while (byteRead < messageLen) {
                long count = socketChannel.read(byteBuffers);
                byteRead += count;
                System.out.println("byteRead=" + byteRead);

                Arrays.stream(byteBuffers).forEach((item) -> {
                    System.out.println("position=" + item.position() + ", limit=" + item.limit());
                });
            }

            // 将所有的buffer进行反转
            Arrays.stream(byteBuffers).forEach(ByteBuffer::flip);

            // 将数组读出显示到客户端
            long byteWrite = 0;

            while (byteWrite < messageLen) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }

            // 将所有的buffer进行复位
            Arrays.stream(byteBuffers).forEach(ByteBuffer::clear);

            System.out.println("byteRead=" + byteRead + ", byteWrite="
                    + byteWrite + ", messageLen=" + messageLen);
        }
    }
}
