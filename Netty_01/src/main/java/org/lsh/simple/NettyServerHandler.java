package org.lsh.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


/**
 * 1.自定义一个Handler 需要继续Netty 规定好某个HandlerAdapter
 * 2.我们自定义的一个Handler，才能成为一个Handler
 */

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /*
        读取数据 可以读取客户端发送的消息
        1. ChannelHandlerContext ctx: 上下文对象，含有管道pipeline和通道channel， 地址
        2. Object msg: 客户端发送的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 比如这里有一个非常耗费时间的业务-> 异步执行 -> 提交该channel 对应的NIOEventLoop的taskQueue
        // 1.用户程序自定义的普通任务
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client 2", StandardCharsets.UTF_8));
            } catch (InterruptedException e) {
                System.out.println("error: " + e.getMessage());
            }

        });

        // 2.用户自定义定时任务 -> 该任务时提交到scheduledTaskQueue
        ctx.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(10 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client 3", StandardCharsets.UTF_8));
            } catch (InterruptedException e) {
                System.out.println("error: " + e.getMessage());
            }

        }, 5, TimeUnit.SECONDS);


        System.out.println("go on......");
//        System.out.println("server crx = " + ctx);
//
//        // 将msg转成一个ByteBuffer
//        // Netty的Buffer
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("The message of client is: " + buf.toString(StandardCharsets.UTF_8));
//        System.out.println("Address of client: " + ctx.channel().remoteAddress());

    }

    /*
        数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 写入数据并刷新 使用编码写入
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client", StandardCharsets.UTF_8));
    }

    /*
        异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
