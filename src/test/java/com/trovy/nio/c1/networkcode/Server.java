package com.trovy.nio.c1.networkcode;

import com.trovy.nio.c1.buffer.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: Trovy
 * @Date: 2021/4/11 19:00
 */
@Slf4j
public class Server {

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 得到一条完整消息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                ByteBufferUtil.debugAll(target);
            }
        }
        source.compact();
    }

    public static void main(String[] args) throws IOException {
        // 1.创建selector,管理多个channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // 2.建立selector和channel的联系(注册)
        // SelectionKey 将来事件发生后,通过它知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key: {}", sscKey);

        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            // 3.select方法,没有事件发生,线程阻塞
            // select 在事件未处理时,它不会阻塞
            selector.select();

            // 4.处理事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 处理完一个事件就要把相应的key给删掉
                iterator.remove();

                log.debug("key: {}", key);
                // 5.区分事件类型
                if (key.isAcceptable()) {// 如果是 accept
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel scOfAccept = channel.accept();
                    scOfAccept.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(5);
                    // 将ByteBuffer作为附件关联到SelectionKey上
                    SelectionKey scKey = scOfAccept.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("scOfAccept: {}", scOfAccept);
                    log.debug("scKey: {}", scKey);
                } else if (key.isReadable()) {// 如果是 read
                    try {
                        SocketChannel scOfRead = (SocketChannel) key.channel();// 拿到触发事件的channel
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        // 如果是正常断开,read的方法的返回值是-1
                        int read = scOfRead.read(buffer);
                        if (read == -1) {
                            key.cancel();
                        } else {
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                // 需要扩容
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() << 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 因为客户端断开了,因此需要将key取消
                        // 从selector的key集合中真正删除key
                        key.cancel();
                    }
                }

                // key.cancel();

            }
        }


    }
}
