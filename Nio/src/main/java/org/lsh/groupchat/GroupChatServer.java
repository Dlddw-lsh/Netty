package org.lsh.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author lsh
 * @description: 群聊服务端
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            // 得到选择器
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();

            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));

            // 设置非阻塞模式
            listenChannel.configureBlocking(false);
            // 将该listenChannel注册到select中
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 监听
    public void listen() {
        try {
            // 循环监听
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 如果是连接事件
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            // 将该SC注册到select上
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + "上线");
                        }
                        // 读取事件
                        if (key.isReadable()) {
                            readData(key);
                        }

                        // 删除当前key防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待。。。");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读取客户端消息
    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            // 取得管理的channel;
            channel = ((SocketChannel) key.channel());
            // 创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            // 根据count的值处理
            if (count > 0) {
                String msg = new String(buffer.array(), 0, count);
                System.out.println("客户端：" + msg);

                // 向其他客户端转发消息
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {

            try {
                System.out.println(channel.getRemoteAddress() + "离线了...");
                // 取消注册
                key.cancel();
                // 关闭
                channel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // 转发消息给其他客户(channel)
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息");
        // 遍历所有注册到selector上的socketChannel,并派出self
        for (SelectionKey key : selector.keys()) {
            // 通过key取出对应的socketChannel
            Channel targetChannel = key.channel();

            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将msg存储到buffer
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                // 将buffer的数据写入通道
                dest.write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) {
        // 创建一个服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
