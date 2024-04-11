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
public class NIOFIleChannel03 {
    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("E:\\DEMO\\Java\\" +
                "MiddleWare\\Netty\\src\\main\\resources\\1.txt");
             FileOutputStream fileOutputStream = new FileOutputStream("E:\\DEMO\\Java\\" +
                     "MiddleWare\\Netty\\src\\main\\resources\\2.txt")) {
            FileChannel channel01 = fileInputStream.getChannel();
            FileChannel channel02 = fileOutputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(512);

            while (true) {
                buffer.clear();
                int read = channel01.read(buffer);
                if (read == -1) {
                    break;
                }
                buffer.flip();
                channel02.write(buffer);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
