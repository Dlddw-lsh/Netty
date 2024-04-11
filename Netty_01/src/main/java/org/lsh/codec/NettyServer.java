package org.lsh.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.lsh.proto.MyDataInfo;
import org.lsh.proto.StudentPOJO;

public class NettyServer {

    public static void main(String[] args) throws Exception {
        /*
           1.创建两个线程组bossGroup和workerGroup
           2.bossGroup只处理连接请求，workerGroup处理业务
           3.默认二者的NioEventLoop数量等于CPU核心 * 2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建服务端的启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();

            // 链式编程设置参数
            bootstrap.group(bossGroup, workerGroup) // 设置父子线程组
                    .channel(NioServerSocketChannel.class) // 实使用NioServerSocketChannel作为通道
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列等待的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道测试对象
                        // 给管道设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 加入ProtoBuf解码器,指定对那种对象进行解码 要写在handler的上面，否则handler不处理
//                            ch.pipeline().addLast("decoder",
//                                    new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                            ch.pipeline().addLast("decoder",
                                    new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                            // 可以使用一个集合管理SocketChannel, 在推送消息时，可以将业务加入到各个Channel
                            // 对应的taskQueue或者scheduledTaskQueue
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给workGroup的eventLoop管道设置处理器

            System.out.println("......Server is ready......");
            // 绑定端口并同步[启动]
            ChannelFuture cf = bootstrap.bind(6668).sync();
            // 给ChannelFuture注册监听器，监控我们关心的事件
            cf.addListener(future -> {
                if (cf.isSuccess()) {
                    System.out.println("监听端口6668成功");
                } else {
                    System.out.println("监听端口6668失败");
                }
            });

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
