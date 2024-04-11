package org.lsh.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.lsh.proto.MyDataInfo;
import org.lsh.proto.StudentPOJO;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    // 当通道就绪时就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        // 发送一个Student对象到服务器
//        StudentPOJO.Student wed0n = StudentPOJO.Student
//                .newBuilder().setId(4).setName("Wed0n").build();
//        System.out.println(wed0n);
//        ctx.writeAndFlush(wed0n);
        // 随机发送Student或者worker对象
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        if (random == 0) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("test").build())
                    .build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(20).setName("test2").build())
                    .build();
        }
        ctx.writeAndFlush(myMessage);
    }

    // 当通道有读取事件时， 会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("The server received: " + buf.toString(StandardCharsets.UTF_8));
        System.out.println("Address of server: " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
