package com.example.leetcode.daily.august;

import org.junit.Test;

import java.util.Arrays;

public class Leet0809 {
    public int minStartValue(int[] nums) {
        int[] preSum = new int[nums.length];
        preSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        // 负数；
        int min = Arrays.stream(preSum).min().getAsInt();

        return -min + 1;
    }

    public int minStartValueAnswer(int[] nums) {
        int minInt = 0, preSum = 0;
        for (int num : nums) {
            preSum += num;
            minInt = Math.min(minInt, preSum);
        }
        return -minInt + 1;
    }

    @Test
    public void test() {
        /*[3, 1, 4, 0, -2]  x - y >= 1;==> x >= y+1

        1 -1 -4;
         */
        int[] nums = {-3, 2, -3, 4, 2};
        minStartValue(nums);
    }
}
