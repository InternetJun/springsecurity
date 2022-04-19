package com.example.leetcode.daily;

import com.baomidou.mybatisplus.extension.api.R;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Leet0413 {
    /*
    https://leetcode-cn.com/problems/insert-delete-getrandom-o1/
    380. O(1) 时间插入、删除和获取随机元素*/

    /** 要怎么来表示出一个ele是一个平均元素的？
     *  和lru很像的！！
     * */
    public static void main(String[] args) {

    }

    /*还有的是一个什么呢？黑名单！！不能选择黑名单中的元素
     * https://leetcode-cn.com/problems/random-pick-with-blacklist/
     * */
    public static class Solution {
        /*
        int pick() {
        int res = rand() % N;
        while (res exists in blacklist) {
            // 重新随机一个结果
            res = rand() % N;
        }
        return res;
        }
        这个函数会多次调用 rand() 函数，执行效率竟然和随机数相关，不是一个漂亮的解法。
        * */
        Random random;
        int length;
        Map<Integer, Integer> map;

        public Solution(int n, int[] blackList) {
            random = new Random();
            length = n- blackList.length;
            map = new HashMap<>();

            //标记黑名单
            for (int i : blackList) {
                map.put(i, -1);
            }
            int index = length;
            for (int black : blackList) {
                if (0 <= black && black < length) {
                    while (index < n) {
                        if(!map.containsKey(index)) {
                            map.put(black, index);
                            index++;
                            break;
                        } else {
                            index++;
                        }
                    }
                }
            }
        }

        public int pick() {
            int i = random.nextInt(length);
            if (map.containsKey(i)){
                return map.get(i);
            }
            return i;
        }

        public static void main(String[] args) {
            Solution solution = new Solution(5, new int[]{1,3});
            for (int i = 0; i < 100; i++) {
                int pick = solution.pick();
                System.out.print(pick+"\t");
            }
        }

    }
}
