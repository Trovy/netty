package com.trovy.nio.c1.channel;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: Trovy
 * @Date: 2021/4/11 16:09
 */
public class TestPath {
    public static void main(String[] args) {
        Path path1 = Paths.get("to.txt");
        System.out.println("path1 = " + path1);
        Path path2 = Paths.get("logback.xml");
        System.out.println("path2 = " + path2);
        Path path3 = Paths.get("ByteBufferUtil.java");
        System.out.println("path3 = " + path3);
    }
}
