package com.trovy.netty.basic.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @Author: Trovy
 * @Date: 2021/4/21 22:22
 */
public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
        TestByteBuf.log(buf);

        // 在切片过程中没有发生数据的复制
        ByteBuf buf1 = buf.slice(0, 5);
        ByteBuf buf2 = buf.slice(5, 5);
        TestByteBuf.log(buf1);
        TestByteBuf.log(buf2);


        System.out.println("====================");
        buf1.setByte(0, 'b');
        TestByteBuf.log(buf);


    }
}
