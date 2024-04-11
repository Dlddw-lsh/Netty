package org.lsh.nio.channel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lsh
 * @description:
 */
public class NIOFileChannel01 {

    public static void main(String[] args) {
        String str = "Hello,Wed0n";
        // 创建一个输出流->channel
        try (FileOutputStream fileOutputStream = new FileOutputStream("E:\\DEMO\\Java\\" +
                "MiddleWare\\Netty\\src\\main\\resources\\file01.txt")) {

            // 通过fileOutputStream获取对于的FileChannel
            FileChannel channel = fileOutputStream.getChannel();

            // 创建一个缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            // 将str 放入byteBuffer
            byteBuffer.put(str.getBytes());

            // 对byteBuffer进行反转
            byteBuffer.flip();

            channel.write(byteBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
