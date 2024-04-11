package org.lsh.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.lsh.proto.MyDataInfo;
import org.lsh.proto.StudentPOJO;

import java.nio.charset.StandardCharsets;


/**
 * 1.自定义一个Handler 需要继续Netty 规定好某个HandlerAdapter
 * 2.我们自定义的一个Handler，才能成为一个Handler
 */

//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    /*
        读取数据 可以读取客户端发送的消息
        1. ChannelHandlerContext ctx: 上下文对象，含有管道pipeline和通道channel， 地址
        2. Object msg: 客户端发送的数据 默认Object
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        // 读取从客户端你发送的StudentPOJO.Student类型
//        System.out.println("客户端发送的数据:\n" + msg);
        // 根据DataType来显示不同的信息
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.StudentType) {
            MyDataInfo.Student student = msg.getStudent();
            System.out.println(student);
        } else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType) {
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println(worker);
        } else {
            System.out.println("传输的类型不正确");
        }
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
