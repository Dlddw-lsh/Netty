package org.lsh.handlerChain.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lsh
 * @description:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("MyClientHandler中的channelRead0被调用");
        System.out.println("从服务器接收到的数据：" + msg);
        System.out.println("-----------------------------------");
    }

    // 重写channelActive
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler发送数据");
        System.out.println("-----------------------------------");
        ctx.writeAndFlush(1234567L); // 发送一个Long
    }
}
