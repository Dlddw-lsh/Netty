package org.lsh.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lsh
 * @description:
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {

        // 创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到一个Selector对象
        Selector selector = Selector.open();

        // 绑定一个端口6666,在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把ServerSocketChannel 注册到 selector 关系事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {
            // 没有事件发生
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            // 如果返回的>0,就获取到相关的selectionKey集合
            /**
             * 1.如果返回的>0，表示已经获取到关注的集合
             * 2.selector.selectedKeys() 返回关注事件的集合
             *      通过selectionKeys反向获取通道
             */
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // 遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 根据对应的事件执行相应的操作
                if (key.isAcceptable()) {
                    // 该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 将当前通道注册到Selector中,关注事件为OP_READ,同时关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                //发生OP_READ事件
                if (key.isReadable()) {
                    // 通过Key反向获取对应的Channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取该Channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("从客户端拿到的数据：" + new String(buffer.array()));
                }

                // 手动从集合中删除当前的SelectKey,防止重复操作
                iterator.remove();
            }
        }
    }
}
