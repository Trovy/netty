package com.trovy.nio.c1.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Author: Trovy
 * @Date: 2021/4/11 15:47
 */
public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try (
                FileChannel from = new FileInputStream("data.txt").getChannel();
                FileChannel to = new FileOutputStream("to.txt").getChannel()
        ) {
            // 效率高,底层会利用操作系统的零拷贝进行优化,2G 数据
            final long size = from.size();
            for (long left = size; left > 0; ) {
                left -= from.transferTo(size - left, left, to);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
