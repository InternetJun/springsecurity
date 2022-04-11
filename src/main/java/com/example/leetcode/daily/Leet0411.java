package com.example.leetcode.daily;

public class Leet0411 {
    /**
     * https://leetcode-cn.com/problems/count-numbers-with-unique-digits/
     * @param n
     * @return
     * 10 ^ n的除去重复数字的！其实就是利用了一个 !& 运算的。有：
     * 99 11 22 ... 这些的二进制的值是0.
     * 19 就是有101 001 就是有一个
     * 有一个记录，
     * 1000中呢? 1 ~ 9
     * 10+ 9 * 9 + 9 * 9 * 8
     * (11 22 ... 99) ==> 9 * 9 ==》 81
     *
     */
    public int countNumbersWithUniqueDigits(int n) {
        if (n == 0)
            return 1;
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 10;
        for (int i = 2; i <= n; i++){
            dp[i] = dp[i-1] + (dp[i-1] - dp[i-2])*(10-(i-1));
        }
        return dp[n];
    }
}
