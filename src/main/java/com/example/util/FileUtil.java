package com.example.util;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {

    @Test
    public void testFile() throws IOException {
        String pa = "C:\\Users\\Administrator\\Pictures";
        List<String> strings = readFileName(pa);
        System.out.println(strings.toString());
    }

    public List<String> readFileName(String path) throws IOException {
        Path startingDir = Paths.get(path);
        List<Path> result = new LinkedList();
        Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        ArrayList<String> paths = new ArrayList<>();
        result.stream().forEach(Path-> {
            paths.add(Path.toString());
        });
        return paths;
    }

    private static class FindJavaVisitor extends SimpleFileVisitor<Path> {
        private List result;

        public FindJavaVisitor(List result){
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            //这里是需要做点东西的
            result.add(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            //访问到目录的时候 就会触发该方法 就可以对遍历的目录进行操作
            System.out.println("正在操作的目录是："+dir);

            return FileVisitResult.CONTINUE;
        }

        @Override
//       做完后呢
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return super.postVisitDirectory(dir, exc);
        }
    }
}

