package com.example.leetcode.daily;

public class Leet0518 {
    /**
     * 含义就是得到乘法表中小于等于 x 的数字个数恰好为 k 时， x的值。
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int findKthNumber(int m, int n, int k) {
        int left = 1, right = m * n;
        while (left < right) {
            int x = left + (right - left) / 2;
            int count = x / n * n;
            for (int i = x / n + 1; i <= m; ++i) {
                count += x / i;
            }
            if (count >= k) {
                right = x;
            } else {
                left = x + 1;
            }
        }
        return left;
    }

//1-权重随机数，平均随机数，黑名单列表数据。
//2-3点公线。利用公式转换。
}
