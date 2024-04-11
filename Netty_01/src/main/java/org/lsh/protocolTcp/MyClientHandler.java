package org.lsh.protocolTcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
            String mes = "我爱打篮球";
            byte[] content = mes.getBytes(StandardCharsets.UTF_8);
            int len = mes.getBytes(StandardCharsets.UTF_8).length;

            // 创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(len).setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol msg) throws Exception {
        // 接收到数据，并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("长度= " + len);
        System.out.println("客户端接收到的信息：" + new String(content, StandardCharsets.UTF_8));
        System.out.println("客户端接收到的消息数量：" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息：" + cause.getMessage());
        ctx.close();
    }
}