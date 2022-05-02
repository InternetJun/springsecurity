package com.example.leetcode.daily;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.models.auth.In;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Leet0425 {
    /*随机函数的实现:
    *
    * 水塘抽样算法
    *
    * */
    static class Solution{
        public int[] nums;
        Random random;

        public int pick(int target){
            int ans = 0;
            for (int i = 0, cnt = 0; i < nums.length; i++) {
                if (nums[i] == target) {
                    ++cnt;
                    if (random.nextInt(cnt) == 0) {
                        ans = i;
                    }
                }
            }
            return ans;
        }

        public Solution(int[] nums) {
            this.nums = nums;
           random = new Random();
        }

    }
    public static void main(String[] args) {
        Solution solution = new Solution(new int[]{1,2,3,3,4});
        System.out.println(solution.pick(3));
    }
}
