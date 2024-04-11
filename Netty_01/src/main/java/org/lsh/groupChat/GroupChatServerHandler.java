package org.lsh.groupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

/**
 * @author lsh
 * @description: 自定义Handler处理
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 定义一个channel组，管理所有的channel
    // GlobalEventExecutor.INSTANCE 全局的事件执行器，是一个单例
    private static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 当连接建立时，一旦连接第一个被执行
    // 将当前channel加入到channels
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将改客户加入聊天的信息推送给其他在线的客户端
        /*
            该方法的channelGroup中所有的channel遍历并发送消息
            我们不需要自己遍历
         */
        group.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天\n");
        group.add(channel);
    }

    // 表示channel处于活动状态，显示xxx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了...");
    }

    // 表示channel处于不活动状态，显示xxx离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "离线了...");
    }

    // 断开连接 将某某客户离开信息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        group.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开了\n");
        System.out.println("当前在线人数：" + group.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取当前channel
        Channel channel = ctx.channel();
        // 遍历group，根据不同的情况，回送不同的消息
        group.forEach(ch -> {
            // 不是当前的channel
            if (!channel.equals(ch)) {
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + "：" + msg + "\n");
                // 回显自己的消息
            } else {
                ch.writeAndFlush("[自己]发送了消息：" + msg + "\n");
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭通道
        ctx.close();
    }
}
