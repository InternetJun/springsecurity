package com.example.leetcode.basicJava;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MaxThreeSum {
    // https://leetcode-cn.com/problems/maximum-sum-of-3-non-overlapping-subarrays/solution/gong-shui-san-xie-jie-he-qian-zhui-he-de-ancx/
    /**
     *dp反序的实现
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        long[] sum = new long[n + 1];
        for (int i = 1; i <= n; i++) sum[i] = sum[i - 1] + nums[i - 1];
        long[][] f = new long[n + 10][4];

        for (int i = n - k + 1; i >= 1; i--) {
            for (int j = 1; j < 4; j++) {
                f[i][j] = Math.max(f[i + 1][j], f[i + k][j - 1] + sum[i + k - 1] - sum[i - 1]);
            }
        }
        int[] ans = new int[3];
        int i = 1, j = 3, idx = 0;

        /**@有f(i)(j)表示的是一个i个位置的，第j个（总共3个window的意思）
         * 然后考虑「如何回溯出字典序最小的具体方案」，常规的回溯具体方案的做法是，从最终答案 f[n - 1][3]f[n−1][3] 开始往回追溯。
         * 利用 f[n - 1][3]f[n−1][3] 仅由两个可能的节点（f[n - 2][3]f[n−2][3] 和 f[n - 1 - k][2]f[n−1−k][2]）转移而来，通过判断 f[n - 1][3]f[n−1][3] 等于 f[n - 2][3]f[n−2][3] 还是 f[n - 1 - k][2] + \sum_{idx = n - k }^{n - 1} nums[idx]f[n−1−k][2]+∑
         * idx=n−k
         * n−1
         * ​
         *  nums[idx] 来决定回溯点为何值。
          // 最优值 f[i][j] 一定从两个状态转移过来 : f[i+1][j] 或者 sum[i...i+k-1] + f[i+k][j-1]
            // (1) 如果 nums[i] 无贡献，则一定有 f[i+1][j] 更大
            if (f[i+1][j] > f[i+k][j-1] + sum[i+k-1] - sum[i-1]){  i++;  }
            // (2) 如果 nums[i] 被选择，则一定有 sum[i...i+k-1] + f[i+k][j-1] 更大。
            else{
                ans.push_back(i-1);  // 注意我们的索引从1开始。
                i += k; // 选取 nums[i] 后，只能继续从 nums[i+k...] 继续模拟取后面的点。
                j--;
        * */
        while (j > 0) {
            //目的是什么呢?
            log.info("{}----------f[i + 1][j]的值是{}", j,f[i + 1][j]);
            log.info("{}---------f[i + k][j - 1]的值是{}", j,f[i + k][j - 1]);
            if (f[i + 1][j] > f[i + k][j - 1] + sum[i + k - 1] - sum[i - 1]) {
                i++;
            } else {
                ans[idx++] = i - 1;
                i += k;
                j--;
            }
        }
        return ans;
    }

    @Test
    public void main() {
        int[] nums = {1,2,1,2,6,7,5,1};
        int k = 2;
        maxSumOfThreeSubarrays(nums, k);


    }

    public int[] maxSumOfThreeSubarrays1(int[] nums, int k) {
        int n = nums.length;
        reverse(nums);
        long[] sum = new long[n + 1];
        for (int i = 1; i <= n; i++) sum[i] = sum[i - 1] + nums[i - 1];
        long[][] f = new long[n + 10][4];
        for (int i = k; i <= n; i++) {
            for (int j = 1; j < 4; j++) {
                f[i][j] = Math.max(f[i - 1][j], f[i - k][j - 1] + sum[i] - sum[i - k]);
            }
        }
        int[] ans = new int[3];
        int i = n, j = 3, idx = 0;
        while (j > 0) {
            if (f[i - 1][j] > f[i - k][j - 1] + sum[i] - sum[i - k]) {
                i--;
            } else {
                ans[idx++] = n - i;
                i -= k; j--;
            }
        }
        return ans;
    }
    void reverse(int[] nums) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l < r) {
            int c = nums[l];
            nums[l++] = nums[r];
            nums[r--] = c;
        }
    }


}
