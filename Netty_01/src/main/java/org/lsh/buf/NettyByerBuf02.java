package org.lsh.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author lsh
 * @description:
 */
public class NettyByerBuf02 {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", StandardCharsets.UTF_8);

        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();

            System.out.println(new String(content, StandardCharsets.UTF_8));

            System.out.println(byteBuf.arrayOffset()); // 0
            System.out.println(byteBuf.readerIndex()); // 0
            System.out.println(byteBuf.readerIndex()); // 12
            System.out.println(byteBuf.capacity());

            // 可读字节数
            System.out.println(byteBuf.writableBytes());

            System.out.println(byteBuf.getCharSequence(4, 6, StandardCharsets.UTF_8));
        }
    }
}
