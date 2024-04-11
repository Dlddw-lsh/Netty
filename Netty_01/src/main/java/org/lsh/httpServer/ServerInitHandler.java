package org.lsh.httpServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerInitHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向管道加入处理器

        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个netty提供的httpServerCodec codec => [coder - decoder]
        /*
            1.HttpServerCodec是netty提供的编码器
         */
        pipeline.addLast("myHttpServer", new HttpServerCodec());
        // 2.曾加一个自定义的处理器
        pipeline.addLast("myServerHandler", new HttpServerHandler());

        System.out.println("ok");
    }
}
