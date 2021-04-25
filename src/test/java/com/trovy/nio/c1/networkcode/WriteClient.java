package com.trovy.nio.c1.networkcode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: Trovy
 * @Date: 2021/4/17 13:06
 */
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));

        // 3.接受数据
        int count = 0;
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 << 10);
            count += sc.read(buffer);
            System.out.println("count = " + count);
            buffer.clear();
        }
    }
}
