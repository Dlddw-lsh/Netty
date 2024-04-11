package org.lsh.nio.buffer;

import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lsh
 * @description:
 */
public class BasicBuffer {

    public static void main(String[] args) {
        // 举例说明Buffer的使用（简单说明）
        // 创建一个Buffer，大小为5，即可存放5个int

        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 如何从buffer读取数据
        // 将Buffer转换，读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
