package org.lsh.rpc.consumer;

import org.lsh.rpc.netty.client.NettyClient;
import org.lsh.rpc.publicInterface.HelloService;

public class ClientBootstrap {

    public static final String PROVIDER_NAME = "iKun#Basketball#";

    public static void main(String[] args) throws InterruptedException {
        // 创建一个消费者
        NettyClient consumer = new NettyClient();

        // 创建代理对象
        HelloService service = (HelloService) consumer.getBean(HelloService.class, PROVIDER_NAME);
        for (; ; ) {
            String hello = service.hello("你好 RPC");
            System.out.println("调用的结果 res=" + hello);
            Thread.sleep(1000);
        }

    }
}
