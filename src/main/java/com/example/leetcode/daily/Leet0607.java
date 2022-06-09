package com.example.leetcode.daily;

/**
 * https://leetcode.cn/problems/koko-eating-bananas/
 */
public class Leet0607 {

    // 她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）
    public int minEatingSpeed(int[] piles, int h) {

        // 一般情况
        int left = 1, right = 100000 + 1;
        while(left < right) {
            int mid = left + (right -  left) /2;
            if (f(piles, mid) <= h) {
                right = mid;
            } else {
                left = mid+1;
            }
        }
        return left-1;
    }

    int f(int[] piles, int x) {
        int hours = 0;
        for (int pile : piles) {
            hours += pile / x;
            if (pile % x > 0) {
                hours++;
            }
        }
        return hours;
    }

    // 一般的题目是会有搜索的左边界和右界；==》左边界是右边的元素靠近，否则相反
}
