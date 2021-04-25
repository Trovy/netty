package com.trovy.nio.c1;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @Author: Trovy
 * @Date: 2021/4/9 22:25
 */
public class Test {
    public static void main(String[] args) {
        // ByteBuffer b1 = ByteBuffer.allocate(10);
        // b1 = Charset.defaultCharset().encode("trovy");
        // b1.position(1);
        // System.out.println(Charset.defaultCharset().decode(b1));
        getCPUCoreNum();
    }

    public static void getCPUCoreNum() {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("i = " + i);
    }
}
