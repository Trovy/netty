package com.trovy.nio.c1.networkcode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @Author: Trovy
 * @Date: 2021/4/17 12:34
 */
public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);

                    // 1.向客户端发送大量数据
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 5000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    // 2.返回值代表实际写入的字节数
                    int write = sc.write(buffer);
                    System.out.println("write = " + write);

                    if (buffer.hasRemaining()) {
                        // 4.关注可写事件(相加的目的是为了避免把原来关注的事件覆盖了)
                        scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        // 使用位运算符也可以达到相同的目的
                        // scKey.interestOps(scKey.interestOps() | SelectionKey.OP_WRITE);
                        // 5.把未写完的数据挂到scKey上
                        scKey.attach(buffer);
                    }
                } else if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(buffer);
                    System.out.println("write = " + write);
                    // 6.清理操作
                    if (!buffer.hasRemaining()) {
                        // 写完后要清除Buffer
                        key.attach(null);
                        // 写完后不需关注可写事件
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                    }


                }
            }

        }
    }
}
