package org.lsh.rpc.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<Object> {

    private ChannelHandlerContext context;
    private String result;
    private String param; //客户端调用方法时传入的结果

    // 与服务器连接创建成功时，就会被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;

    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify(); // 唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    // 被代理对象调用，发送数据给服务器 -> wait -> 等待被唤醒
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        wait();
        return result;
    }

    public void setParam(String param){
        this.param = param;
    }
}
