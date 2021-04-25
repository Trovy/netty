package com.trovy.nio.c1.file;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Trovy
 * @Date: 2021/4/11 16:18
 */
public class TestFilesWalkFileTree {
    public static void main(String[] args) throws IOException {



    }

    // 删除不为空的文件夹
    private static void m3() throws IOException {
        Files.walkFileTree(Paths.get(""), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    // 计算一个文件夹下的jar包数量
    private static void m2() throws IOException {
        final AtomicInteger jarCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("D:\\Java\\jdk8"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".jar")) {
                    jarCount.incrementAndGet();
                    System.out.println(file);
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("jar count:" + jarCount);
    }

    // 计算一个目录下的文件和文件夹数量
    private static void m1() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("D:\\Java\\jdk8"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("============>" + dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }

        });
        System.out.println("dir count:" + dirCount);
        System.out.println("file count:" + fileCount);
    }
}
