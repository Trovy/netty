package com.trovy.nio.c1.aio;

import com.trovy.nio.c1.buffer.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author: Trovy
 * @Date: 2021/4/18 0:01
 */
@Slf4j
public class AioFileChannel {
    public static void main(String[] args) throws IOException {
        try (AsynchronousFileChannel channel =
                     AsynchronousFileChannel.open(
                             Paths.get("data.txt"),
                             StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(16);
            log.debug("read begin...");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                // 成功的回调
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    log.debug("read completed... ");
                    attachment.flip();
                    ByteBufferUtil.debugAll(attachment);
                }

                // 失败的回调
                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
            log.debug("read end...");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.in.read();
    }
}
