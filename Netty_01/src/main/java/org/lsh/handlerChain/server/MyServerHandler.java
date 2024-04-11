package org.lsh.handlerChain.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lsh
 * @description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("MyServerHandler中的channelRead0被调用");
        System.out.println("从客户端：" + ctx.channel().remoteAddress() + "读取到Long:" + msg);
        System.out.println("-----------------------------------");
        // 给客户端发送数据
        ctx.writeAndFlush(1111L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
