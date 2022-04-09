package com.example.leetcode;

import org.junit.Test;

import java.util.*;

/**
 https://leetcode-cn.com/problems/maximum-product-of-word-lengths/solution/jian-dan-yi-dong-javac-pythonjsgo-zui-da-ertr/
 * 这是一个关于位运算的东西。有几点：
 * 1，数据要是有相同的位表示的话，肯定是不为0的。
 * 2.数据中包含相同元素的长度是可控的时候。例如：
 * eg；eegg;
 * max(map.put(bit     mask, Math.max(map.getOrDefault(bitmask, 0), word.length())))
 */
public class MaxLength {
    @Deprecated
    public int maxProductMe(String[] words) {
        if (words == null || words.length == 0){
            return 0;
        }
        int len = words.length;
        int max = 0;
        int[][] ind = new int[26][len];
        for (char i = 'a'; i < 'z'; i++) {
            for (int j = 0; j < len; j++) {
                int k =0;
                while(k < words[j].length()) {
                    if (words[j].charAt(k) != i) {
                        k++;
                    } else {
                        ind[i-'a'][j]++;
                        words[j] = "";
                        continue;
                    }
                }
            }
        }
        //对上面的数据已经排好序。后面就是找出最长的数据了。
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < 26; i++) {
            int[] item = ind[i];
            if (item != new int[6]) {
                char value = (char)((char) i+'a');
                set.add(value);
            }
        }

        for (int i = 0; i < len; i++) {
            if (!set.contains(words[i].toCharArray())) {

            }
        }

        return 0;
    }

    @Test
    public void main() {
        String[] strings = {"abcw", "baz", "foo", "bar", "xtfn", "abcdef"};
        Set<Character> set = new HashSet(){{
            add('a');
            add('f');
        }};
        System.out.println(set.toArray());
        char[] chars = "abcdef".toCharArray();
        for (char c : chars) {
            System.out.println(set.contains(c));
        }
//        maxProduct(strings);
    }

    public int maxProduct(String[] words) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = words.length;
        for (int i = 0; i < n; i++) {
            int bitMask = 0;
            for(char c : words[i].toCharArray()) {
                bitMask |= (1<<(c-'a'));
            }
            map.put(bitMask, Math.max(
                    map.getOrDefault(bitMask, 0), words[i].length()));
        }
        int ans = 0;
        for (int x: map.keySet()
             ) {
            for (int y: map.keySet()) {
                if ((x & y) == 0) {
                    ans = Math.max(ans, map.get(x)* map.get(y));
                }
            }
        }
        return ans;
    }

}
