package com.trovy.netty.advance;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: Trovy
 * @Date: 2021/4/24 18:06
 */
public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(
                        1024,
                        0,
                        4,
                        1,
                        0),
                new LoggingHandler(LogLevel.DEBUG)
        );

        // 4个字节的内容长度,实际内容
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        send(buf, "hello, world");
        send(buf, "hi!");
        channel.writeInbound(buf);

    }

    private static void send(ByteBuf buf, String s) {
        byte[] bytes = s.getBytes();
        int length = bytes.length;
        // 网络传输用大端
        buf.writeInt(length).writeByte(1).writeBytes(bytes);
    }
}
