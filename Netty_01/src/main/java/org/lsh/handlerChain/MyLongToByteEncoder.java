package org.lsh.handlerChain;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author lsh
 * @description:
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder里面的encode被调用");
        System.out.println("msg=" + msg);
        System.out.println("-----------------------------------");
        out.writeLong(msg);
    }
}
