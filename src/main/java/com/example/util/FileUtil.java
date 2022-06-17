package com.example.util;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

public class FileUtil {

    @Test
    public void testFile() throws IOException {
        String pa = "C:\\Users\\Administrator\\Pictures";
        List<String> strings = readFileName(pa);
        System.out.println(strings.toString());
    }

    /**
     * 只是粗略的读取所有的file的绝对路径！
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> readFileName(String path) throws IOException {
        Path startingDir = Paths.get(path);
        List<Path> result = new LinkedList();
        Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        ArrayList<String> paths = new ArrayList<>();
        result.stream().forEach(Path-> {
            paths.add(Path.toString());
        });
        return paths;
    }

    /**
     * 只需要唯一的内容，不重复的内容！
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> readOneFileName(String path) throws IOException {
        Path startingDir = Paths.get(path);
        List<Path> result = new LinkedList();
        Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        // 去重。***.mp3和***(live).mp3
        Set<String> lives = new HashSet<>();
        Set<String> music = new HashSet<>();
        Set<String> distinctMusicName = new HashSet<>();
        distinctMusicName.addAll(music);
        List<Path> collectLive = result.stream().filter(s ->
                s.toString().indexOf("(live)") > -1).collect(Collectors.toList());
        List<Path> collectMusic = result.stream()
                                    .filter(s->!collectLive.contains(s.toString()))
                                    .collect(Collectors.toList());
        for (String live : lives) {
            String realName = live.substring(live.lastIndexOf("("), live.lastIndexOf(")") + 1);
            if (!distinctMusicName.contains(realName)) {
                distinctMusicName.add(live);
            }
        }
        ArrayList<String> paths = new ArrayList<>();
        distinctMusicName.stream().forEach(Path-> {
            paths.add(Path);
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
            //这里是需要对文件的名字记录
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

