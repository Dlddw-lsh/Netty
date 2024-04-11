package org.lsh.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用客户端发送10条数据 hello,server
        for (int i = 0; i < 10; i++){
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,server" + i, StandardCharsets.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] buffer = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buffer);

        String message = new String(buffer, StandardCharsets.UTF_8);
        System.out.println("客户端接收到的消息=" + message);
        System.out.println("客户端接收到的数量为" + (++count));
    }

}