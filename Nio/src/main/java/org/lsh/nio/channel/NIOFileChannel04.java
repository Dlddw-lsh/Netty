package org.lsh.nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author lsh
 * @description:
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        // 创建相关流
        FileInputStream fileInputStream = new FileInputStream("pox.xml");
        FileOutputStream fileOutputStream = new FileOutputStream("aaa.txt");

        // 获取Channel
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel dest = fileOutputStream.getChannel();

        // 使用transferFrom拷贝文件
        dest.transferFrom(sourceCh, 0, sourceCh.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
