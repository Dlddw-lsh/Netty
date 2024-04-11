package org.lsh.handlerChain.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.lsh.handlerChain.MyByteToLongDecoder;
import org.lsh.handlerChain.MyLongToByteEncoder;

/**
 * @author lsh
 * @description:
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    // 注意coder一定要在handler前面Netty会自动判断用decoder还是encoder
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 入栈的handler进行解码 MyByteToLongDecoder 对应Inbound
        pipeline.addLast(new MyByteToLongDecoder());
        // 对应Outbound
        pipeline.addLast(new MyLongToByteEncoder());

        // 加入handler处理业务逻辑
        pipeline.addLast(new MyServerHandler());

    }
}
