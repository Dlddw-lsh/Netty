package org.lsh.protocolTcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyMessageDecoder中的decode被调用");
        // 需要将得到的二进制字节码->MessageProtocol数据项
        int len = byteBuf.readInt();

        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);

        // 封装成MessageProtocol

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setContent(bytes).setLen(len);
        list.add(messageProtocol);
    }
}
