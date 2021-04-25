package com.trovy.nio.c1.buffer;

import java.nio.ByteBuffer;

import static com.trovy.nio.c1.buffer.ByteBufferUtil.debugAll;

/**
 * @Author: Trovy
 * @Date: 2021/4/7 22:17
 */
public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        buffer.put((byte) 97);
        debugAll(buffer);
        buffer.put(new byte[]{98, 99, 100});
        debugAll(buffer);

        buffer.flip();
        debugAll(buffer);

        buffer.get();
        debugAll(buffer);
        buffer.compact();

    }
}
