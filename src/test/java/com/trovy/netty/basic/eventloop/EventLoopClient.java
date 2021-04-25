package com.trovy.netty.basic.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @Author: Trovy
 * @Date: 2021/4/18 15:39
 */
@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 1.启动类
        ChannelFuture channelFuture = new Bootstrap()
                // 2.添加 EventLoop
                .group(group)
                // 3.选择客户端 channel 实现
                .channel(NioSocketChannel.class)
                // 4.添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override// 在连接建立后被调用
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 5.连接到服务器
                // 异步非阻塞
                .connect(new InetSocketAddress("localhost", 8080));
        Channel channel = channelFuture.sync().channel();
        log.debug("{}", channel);
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equalsIgnoreCase(line)) {
                    channel.close();// close 异步操作
                    // log.debug("处理关闭之后的操作");  不能在这里善后
                    break;
                }
                channel.writeAndFlush(line);
            }
        },"input").start();

        // 获取 ClosedFuture 对象
        ChannelFuture closeFuture = channel.closeFuture();
        /*System.out.println("waiting close");
        closeFuture.sync();
        log.debug("处理关闭之后的操作");*/
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.debug("处理关闭之后的操作");
                group.shutdownGracefully();
            }
        });



        // (1)使用 sync 方法同步处理结果
        // channelFuture.sync();

        // (2)使用 addListener 方法异步处理结果
        /*channelFuture.addListener(new ChannelFutureListener() {
            @Override
            // 在 nio 线程连接建立好之后,会调用 operationComplete
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.channel();
                log.debug("{}", channel);
                channel.writeAndFlush("hello, world");
            }
        });*/

        // 无阻塞向下执行获取channel
        /*Channel channel = channelFuture.channel();
        log.debug("{}", channel);
        channel.writeAndFlush("hello, world111");*/
    }
}
