package com.example.leetcode.daily.october;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author lejun
 */
@Slf4j
public class Leet1016 {

    private String push = "Push";
    private String pop = "Pop";

    /**
     * 获取2队列，利用的是unionFind的思想啊
     *
     * @param n
     * @param dislikes
     * @return
     */
    public boolean possibleBipartition(int n, int[][] dislikes) {
        return true;
    }

    @Test
    public void main() {
        int[] ints = {1, 3};
        int n = 3;
        System.out.println(buildArray(ints, n).toString());
    }

    public List<String> buildArray(int[] target, int n) {
        Deque<Integer> deque = new LinkedBlockingDeque<>();
        int len = target.length;
        String s = Arrays.toString(target);
        List arrayList = new ArrayList();
        log.info("{}", s);
        int operator = 0;
        for (int i = 1; i <= n; i++) {
            if (operator > len) {
                break;
            }
            // 什么思想呢？[[, 1, ,,  , 3, ]]
            if (s.indexOf(i) < 0) {
                arrayList.add(push);
                arrayList.add(pop);
            } else {
                arrayList.add(push);
            }
            operator++;
        }
        return arrayList;
    }

    /**
     * 获取s的全部的子序列。
     *
     * @param s
     * @return
     */
    public int distinctSubseqIIMe(String s) {
        int len = s.length();
        int allCount = 0;
        int[][] dp = new int[len][len];
        // 初始化
        for (int i = 0; i < len; i++) {
            dp[i][i] = 1;
        }

        // 结果是所有的dp为1的sum；
        return allCount;
    }

    /**
     * https://leetcode.cn/problems/distinct-subsequences-ii/solution/bu-tong-de-zi-xu-lie-ii-by-leetcode-solu-k2h5/
     * 我们用f[i] 表示以 s[i] 为最后一个字符的子序列的数目。
     */
    public int distinctSubquence(String s) {
        final int mod = 1000000007;
        int[] last = new int[26];
        Arrays.fill(last, -1);

        int n = s.length();
        int[] f = new int[n];
        Arrays.fill(f, 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 26; j++) {
                //只要最后的一个位置的字母
                if (last[j] != -1) {
                    f[i] = (f[i] + f[last[last[j]]]) % mod;
                }
            }
            last[s.charAt(i) - 'a'] = i;
        }
        int ans = 0;
        for (int i = 0; i < 26; i++) {
            if (last[i] != -1) {
                ans = (ans + f[last[i]]) % mod;
            }
        }
        return ans;
    }

    @Test
    public void testE() {
        double a = 1e7;
        System.out.println(10000000 / a);
    }

    public int distinctSubquenceII(String s) {
        final int mod = 100000007;
        int[] g = new int[26];
        int n = s.length(), total = 0;
        for (int i = 0; i < n; i++) {
            int oi = s.charAt(i) - 'a';
            int prev = g[oi];
            g[oi] = (total + 1) % mod;
            total = ((total + g[oi] - prev) % mod + mod) % mod;
        }
        return total;
    }

    /**https://leetcode.cn/problems/distinct-subsequences-ii/solution/bu-tong-by-capital-worker-vga3/
     * 利用的是dp[i] = dp[i - 1] + newCount - repeatCount
     * 其中newCount表示为引入新的字母
     * repeatCount表示为重复的
     * repeatCount如何计算？
     *
     * 以字符串“abcb”为例，dp[2] - dp[1] 是引入第一个'b'新增的子串个数，那么当引入第二个‘b’的时候重复子串个数也为 dp[2] - dp[1]
     * @param s
     * @return
     */
    public int distinctSubseqII(String s) {
        int mod = (int) 1e9 + 7;
        int n = s.length();
        //之前新增的个数
        int[] preCount = new int[26];
        int curAns = 1;
        char[] chs = s.toCharArray();
        for (int i = 0; i < n; i++) {
            //新增的个数
            int newCount = curAns;
            //当前序列的个数 = 之前的 + 新增的 - 重复的
            curAns = ((curAns + newCount) % mod - preCount[chs[i] - 'a'] % mod + mod) % mod;
            //记录当前字符的 新增值
            preCount[chs[i] - 'a'] = newCount;
        }
        //减去空串
        return curAns - 1;
    }
}
