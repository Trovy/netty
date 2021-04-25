package com.trovy.nio.c1.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Trovy
 * @Date: 2021/4/9 23:37
 */
public class TestGatheringWrites {
    public static void main(String[] args) {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("Hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("World");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("唐闻宣");

        try (FileChannel channel = new RandomAccessFile("words2.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{b1, b2, b3});
        } catch (IOException e) {
        }
    }
}
