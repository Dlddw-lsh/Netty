package org.lsh.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lsh
 * @description:
 */
public class NIOFileChannel02 {

    public static void main(String[] args) {
        try (FileInputStream fileOutputStream = new FileInputStream("E:\\DEMO\\Java\\" +
                "MiddleWare\\Netty\\src\\main\\resources\\file01.txt")) {

            FileChannel channel = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(11);
            channel.read(byteBuffer);
            System.out.println(new String(byteBuffer.array()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
