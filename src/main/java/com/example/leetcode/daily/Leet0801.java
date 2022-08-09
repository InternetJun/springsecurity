package com.example.leetcode.daily;

public class Leet0801 {
    /**
     * 给定n，奇数的字符数。
     */

    /**
     * 有特定的n，奇数
     * @param n
     * @return
     */
    public String generateTheString(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("参数必须大于0！请检查参数");
        }
        if (n == 0) {
            return "";
        }
        int[] chars = new int[26];
//        一个数分解为多个奇数之和。有什么算法？
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while(i < n) {

        }
        return sb.toString();
    }

    /**
     * 1~26;
     * @param n
     * @return
     */
    public int[] getEvenArray(int n) {
        if (n % 2 == 1) {
            //is even；
        }
        return new int[0];
    }
}
