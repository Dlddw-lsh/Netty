package org.lsh.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author lsh
 * @description:
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {
        // 创建一个ByteBuf
        // 说明
        /*
            1.创建 对象，该对象包含一个数组arr，是一个byte[10]
            2.在netty的buffer中不需要使用flip进行反转
                底层维护了readerIndex和writerIndex
            3.通过readerInder和writerIndex和capacity将buffer分成三个区域
                0->readIndex    以及读取的区域
                readIndex->writerIndex  可读的区域
                writerIndex->capacity   可写的区域
         */
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        // 就是上面的10在执行玩Unpooled.buffer之后就是设置的容量
        System.out.println("capacity: " + buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }

    }
}
