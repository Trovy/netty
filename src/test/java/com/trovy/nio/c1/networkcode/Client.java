package com.trovy.nio.c1.networkcode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Author: Trovy
 * @Date: 2021/4/11 22:17
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        // System.out.println("waiting...");
        SocketAddress address = sc.getLocalAddress();
        sc.write(Charset.defaultCharset().encode("hellowor\n"));
        System.in.read();
    }
}
