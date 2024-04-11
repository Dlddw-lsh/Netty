package org.lsh.handlerChain.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.lsh.handlerChain.MyByteToLongDecoder;
import org.lsh.handlerChain.MyLongToByteEncoder;

/**
 * @author lsh
 * @description:
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    // 注意coder一定要在handler前面Netty会自动判断用decoder还是encoder
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 加入出站的encoder对数据进行编码 客户端的出站进行编码
        pipeline.addLast(new MyLongToByteEncoder());
        pipeline.addLast(new MyByteToLongDecoder());

        // 加入一个自定义的handler 客户端的入站
        pipeline.addLast(new MyClientHandler());

    }
}
