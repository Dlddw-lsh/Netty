package org.lsh.protocolTcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

// 处理业务的handler
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        // 接收到数据，并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("长度= " + len);
        System.out.println("服务端接收到的信息：" + new String(content, StandardCharsets.UTF_8));
        System.out.println("服务器接收到的消息数量：" + (++this.count));

        // 回复消息
        String ms = "教练我想打篮球";
        byte[] bytes = ms.getBytes(StandardCharsets.UTF_8);
        len = bytes.length;
        MessageProtocol messageProtocol = new MessageProtocol().setLen(len).setContent(bytes);
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}