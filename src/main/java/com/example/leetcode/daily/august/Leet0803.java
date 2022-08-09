package com.example.leetcode.daily.august;

import com.example.util.StringUtil;
import org.junit.Test;

import java.util.Arrays;

public class Leet0803 {

    /**
     * s的前k个字符移动。某一加入字符的末尾。
     *
     * @param s
     * @param k
     * @return
     */
    public String orderlyQueue(String s, int k) {
        if (k < 0 || StringUtil.isEmpty(s)) {
            return "";
        }
        if (k == 1) {
            String smallest = s;
            StringBuffer sb = new StringBuffer(s);
            int n = s.length();
            for (int i = 0; i < n; i++) {
                char c = sb.charAt(0);
                sb.deleteCharAt(0);
                sb.append(c);
                if (sb.toString().compareTo(smallest) < 0) {
                    smallest = sb.toString();
                }
            }
            return smallest;
        } else {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            return new String(chars);
        }
    }

    @Test
    public void ms() {
        String rs = orderlyQueue("aaacb", 3);
        System.out.println(rs);

    }

}
