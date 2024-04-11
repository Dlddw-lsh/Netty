package org.lsh.handlerChain;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author lsh
 * @description:
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * @param ctx 上下文对象
     * @param in  入站的ByteBuf
     * @param out List集合，将解码后的数据传给下一个Handler
     * @description: decode 会根据接收的数据，被调用多次，直到确定没有新的元素被添加到List，
     * 或者ByteBuf没有更多的可读字节为止
     * 如果List out不为空，就会将List的内容传递给下一个channelInboundHandler处理，该处理的方法也会被调用多次
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder里的decode被调用");
        System.out.println("-----------------------------------");
        // 因为Long为8个字节
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
