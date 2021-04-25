package com.trovy.nio.c1.buffer;

import java.nio.ByteBuffer;

/**
 * @Author: Trovy
 * @Date: 2021/4/7 22:34
 */
public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
    }
}
