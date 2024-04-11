package org.lsh.rpc.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.lsh.rpc.consumer.ClientBootstrap;
import org.lsh.rpc.provider.HelloServiceImpl;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送的消息，并调用我们的服务
        System.out.println("msg=" + msg);
        // 客户端在调用服务器的Api时，我们需要定义一个协议
        // 比如我们要求 每次发消息时必须以某个字符串开头 "iKun#Basketball#"
        if(msg.toString().startsWith(ClientBootstrap.PROVIDER_NAME)){
            String str = msg.toString();
            String result = new HelloServiceImpl().hello(str.substring(str.lastIndexOf("#") + 1));

            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
