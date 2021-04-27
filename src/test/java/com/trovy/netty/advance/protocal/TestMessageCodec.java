package com.trovy.netty.advance.protocal;

import com.trovy.netty.advance.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: Trovy
 * @Date: 2021/4/27 21:07
 */
public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new LengthFieldBasedFrameDecoder(
                        1024,12,4,0,0),
                new MessageCodec());

        // encode
        LoginRequestMessage message = new LoginRequestMessage("trovy", "123", "唐闻宣");
        channel.writeOutbound(message);

        // decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);

        ByteBuf buf1 = buf.slice(0, 100);
        ByteBuf buf2 = buf.slice(100, buf.readableBytes() - 100);

        buf1.retain();
        // 入站
        channel.writeInbound(buf1);
        channel.writeInbound(buf2);

    }
}
