package com.example.leetcode.basicJava;

import com.example.util.DateUtil;
import com.example.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class CopyFile {

    public static void main(String[] args) throws IOException {
        log.info("开始复制！");
        copy2Path("C:\\Users\\Administrator\\Desktop\\周杰伦",
                "C:\\Users\\Administrator\\Desktop\\周杰伦_音乐");

    }

    // 模糊的搜索。
    public static void copy2Path(String originalPath, String currentPath) throws IOException {

        log.info("复制开始...,现在时间是：{}", DateUtil.localDateTime2Str(LocalDateTime.now()));
        File diractory = new File(originalPath);

        List<String> strings = FileUtil.readFileName(originalPath);

        File dir2 = new File(currentPath);
        if (!dir2.exists()) {
            dir2.mkdirs();
        }

        BufferedInputStream bis;
        BufferedOutputStream bos;
        StringBuffer fileNames = new StringBuffer();
        for (String filePath : strings) {
            // 过滤只要mp3的数据啊。
            if (filePath.endsWith(".mp3")) {
                // 中间可能是会有很多级的路径!
                fileNames.append(filePath.substring(filePath.lastIndexOf("\\")));
                bis = new BufferedInputStream(new FileInputStream(filePath));
                bos = new BufferedOutputStream(new FileOutputStream(currentPath + File.separator + fileNames.toString()));
                fileNames.setLength(0);
                byte[] buff = new byte[1024 * 1024];
                int len = -1;
                while ((len = bis.read(buff)) != -1) {
                    bos.write(buff, 0, len);
                }

                bos.flush();
                bis.close();
                bos.close();
            }
        }
        log.info("复制完成...");
    }

    public static void copyOneFile2Path(String originalPath, String currentPath) throws IOException {

        File diractory = new File(originalPath);

        List<String> strings = FileUtil.readFileName(originalPath);

        File dir2 = new File(currentPath);
        if (!dir2.exists()) {
            dir2.mkdirs();
        }

        BufferedInputStream bis;
        BufferedOutputStream bos;
        StringBuffer fileNames = new StringBuffer();
        for (String filePath : strings) {
            // 过滤只要mp3的数据啊。
            if (filePath.endsWith(".mp3")) {
                // 中间可能是会有很多级的路径!
                fileNames.append(filePath.substring(filePath.lastIndexOf("\\")));
                bis = new BufferedInputStream(new FileInputStream(filePath));
                bos = new BufferedOutputStream(new FileOutputStream(currentPath + File.separator + fileNames.toString()));
                fileNames.setLength(0);
                byte[] buff = new byte[1024 * 1024];
                int len = -1;
                while ((len = bis.read(buff)) != -1) {
                    bos.write(buff, 0, len);
                }

                bos.flush();
                bis.close();
                bos.close();
            }
        }
        System.out.println("复制完成！");
    }
}

