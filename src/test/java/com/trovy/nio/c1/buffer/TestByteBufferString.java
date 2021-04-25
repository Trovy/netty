package com.trovy.nio.c1.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Trovy
 * @Date: 2021/4/9 23:11
 */
public class TestByteBufferString {
    public static void main(String[] args) {
        // 1.字符串转为ByteBuffer
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        buffer1.flip();
        ByteBufferUtil.debugAll(buffer1);

        // 2.Charset
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("trovy");
        ByteBufferUtil.debugAll(buffer2);

        // 3.wrap
        ByteBuffer buffer3 = ByteBuffer.wrap("giacinta".getBytes());
        ByteBufferUtil.debugAll(buffer3);

        System.out.println(StandardCharsets.UTF_8.decode(buffer3).toString());
        System.out.println(StandardCharsets.UTF_8.decode(buffer1).toString());
    }
}
