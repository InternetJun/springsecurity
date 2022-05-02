package com.example.leetcode.daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leet0423 {
    /**
     * 凸包付问题！！
     * https://leetcode-cn.com/problems/erect-the-fence/solution/an-zhuang-zha-lan-by-leetcode-solution-75s3/
     * @param trees
     * @return
     */
    public int[][] outerTree(int[][] trees) {
        int n = trees.length;
        if (n < 4) {
            return trees;
        }

        Arrays.sort(trees, (a, b) -> {
            if (a[0] == b[0]) {
                return a[1] - b[1];
            }
            return a[0] - b[0];
        });
        List<Integer> hull = new ArrayList<>();
        boolean[] used = new boolean[n];
        hull.add(0);
        /*求出凸包下半部分。
         **/
        for (int i = 0; i < n; i++) {
            while (hull.size() > 1 &&
                    cross(trees[hull.get(hull.size() - 2)],
                            trees[hull.get(hull.size() - 1)], trees[i]) < 0) {
                used[i] = true;
                hull.add(i);
            }
        }
        int m = hull.size();
        for (int j = n - 2; j >= 0; j--) {
            if (!used[j]) {
                while (hull.size() > m &&
                        cross(trees[hull.get(hull.size() - 2)],
                                trees[hull.get(hull.size() - 1)], trees[j]) < 0) {
                    used[hull.get(hull.size()-1)] = false;
                    hull.remove(hull.size()-1);

                }
                used[j] = true;
                hull.add(j);
            }

        }
        /* hull[0] 同时参与凸包的上半部分检测，因此需去掉重复的 hull[0] */
        hull.remove(hull.size() - 1);
        int size = hull.size();
        int[][] res = new int[size][2];
        for (int i = 0; i < size; i++) {
            res[i] = trees[hull.get(i)];
        }
        return res;
    }

    /**
     *
     * @param p
     * @param q
     * @param r
     * @return
     */
    public int cross(int[] p, int[] q, int[] r) {
        return (q[0] - p[0]) * (r[1] - q[1]) - (q[1] - p[1]) * (r[0] - q[0]);
    }
}
