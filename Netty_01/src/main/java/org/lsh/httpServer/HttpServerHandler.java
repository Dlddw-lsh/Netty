package org.lsh.httpServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * 1.SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * 2.HttpObject客户端和服务器端相互通讯的数据被封装成HttpObject
 */

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 判断msg是不是httpRequest请求
        if (msg instanceof HttpRequest) {
            System.out.println("pipeline: " + ctx.pipeline().hashCode());
            System.out.println("HttpServerHandler: " + this.hashCode());

            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址:  " + ctx.channel().remoteAddress());

            // 获取URI
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if (uri.getPath().equals("/favicon.ico")) {
                return;
            }

            // 回复信息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", StandardCharsets.UTF_16);

            // 构造httpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse
                    (HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers()
                    .set("content-type", "text/plain")
                    .set("content-length", content.readableBytes());

            // 将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }
}
