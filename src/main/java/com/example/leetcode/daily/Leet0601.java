package com.example.leetcode.daily;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class Leet0601 {
    // 拼成一个正方形。有 3 3 3 3 4不可以处理的。
//    public boolean makesquare(int[] matchsticks) {
//        if(matchsticks == null || matchsticks.length == 0) {
//            return false;
//        }
//
//        //就是要寻找一个x
//        int sum = 0;
//        for(int matchstick : matchsticks) {
//            sum += matchstick;
//        }
//
//        // if 1，sum / 4 != 0; false; 2，count ?为什么的情况没有成功呢？
//        boolean flag = (sum % 4 == 0) && ();
//        return flag;
//    }
    /**
     * 其实就是对给定的数组一个n等分的实现了。
     */
    public boolean canPartitionKASubsets(int[] nums, int k) {
        if (k > nums.length) {
            return false;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % k != 0) {
            return false;
        }
        int used = 0;
        // 获取平均数的
        int target = sum / k;
        return backtrace(k, 0, nums, 0, used, target);
    }

    HashMap<Integer, Boolean> memo = new HashMap<>();

    private boolean backtrace(int k, int bucket, int[] nums,
                              int start, int used, int target) {
        if (k ==0) {
            //所有的桶装满；
            return true;
        }

        if (bucket == target) {
            boolean res = backtrace(k-1, 0, nums, 0, used, target);
            memo.put(used, res);
            return res;
        }

        //记下并获取。
        if (memo.containsKey(used)) {
            return memo.get(used);
        }

        for (int i = start; i < nums.length; i++) {
            // 判断第i位是否是1；
            if (((used >> i ) & 1) == 1) {
                log.info("nums[i] has been used");
                continue;
            }
            if (nums[i] + bucket > target) {
                continue;
            }

            used |= 1 << i;
            log.info("将第 i 位置为 1");
            bucket += nums[i];
            if (backtrace(k, bucket, nums, i+1, used, target)) {
                return true;
            }

            //撤销choose
            used ^= 1 << i;
            bucket -= nums[i];
        }
        return false;
    }
}
