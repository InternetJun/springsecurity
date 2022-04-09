package com.example.leetcode.basicJava;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownload {
    public static void main(String[] args) {
        //需要一个路径的映射的，不是随便的获取，不能识别出
        String url = "http://localhost:8080/image/touxiang.png";
        downloadPicture(url);
    }
    //链接url下载图片
    private static void downloadPicture(String urlList) {
        URL url = null;
        int imageNumber = 0;

        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            String imageName =  "F:/test.jpg";

            FileOutputStream fileOutputStream = new FileOutputStream(imageName);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            //一个是字节流，一个是imageIo的操作；
            //
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
