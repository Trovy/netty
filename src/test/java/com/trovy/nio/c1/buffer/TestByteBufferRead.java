package com.trovy.nio.c1.buffer;

import java.nio.ByteBuffer;

/**
 * @Author: Trovy
 * @Date: 2021/4/9 22:11
 */
public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        ByteBufferUtil.debugAll(buffer);
        buffer.flip();
        ByteBufferUtil.debugAll(buffer);

        buffer.get(new byte[4]);
        ByteBufferUtil.debugAll(buffer);

    }
}
