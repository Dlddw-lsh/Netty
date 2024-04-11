package org.lsh.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lsh
 * @description: 1.MappedByteBuffer可以让文件直接在内存(堆外内存)修改，操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

        // 获取对应的通道
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 参数1: 使用什么模式，这里是读写模式
         * 参数2：0，可以直接修改的起始位置
         * 参数3：5：是映射到内存的大小,即将 1.txt的多少个字节映射到内存
         * 可以直接修改的范围就是0-5
         */
        MappedByteBuffer mapByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mapByteBuffer.put(0, (byte) 'H');
        mapByteBuffer.put(4, (byte) '9');
        randomAccessFile.close();

        System.out.println("修改成功");
    }
}
