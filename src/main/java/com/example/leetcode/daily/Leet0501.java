package com.example.leetcode.daily;

import com.example.leetcode.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leet0501 {
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList();
        List<Integer> list2 = new ArrayList();
        if(root1 == null || root2 != null) {
            return getMidOrder(root2);
        } else if(root1 != null || root2 == null) {
            return getMidOrder(root1);
        } else {
            list1 = getMidOrder(root1);
            list2 = getMidOrder(root2);
            return mergeTrees(list1, list2);
        }
    }

    public List<Integer> getMidOrder(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> left = getMidOrder(root.left);
        result.addAll(left);
        result.add(root.val);
        List<Integer> right = getMidOrder(root.right);
        result.addAll(right);
        return result;
    }

    /**
     * 对有序的对象进行一个合并啊。自己实现。不要进行一个调用函数的接口
     * @param list1
     * @param list2
     * @return
     */
    public List<Integer> mergeTrees(List<Integer> list1, List<Integer> list2) {
        ArrayList<Integer> result = new ArrayList<>();
        int size1 = list1.size();
        int size2 = list2.size();
        int i = 0, j = 0;
        while(i < size1 && j < size2) {
            if (list1.get(i) <= list2.get(j)) {
                result.add(list1.get(i++));
            } else {
                result.add(list2.get(j++));
            }
        }
        // 最后多出的数据进行一个处理。
        result.addAll(i == size1 ? list2.subList(i-1, size2) : list1.subList(j-1, size1));
        return result;
    }

    @Test
    public void main() {
        TreeNode root = new TreeNode(3, 1, 5);
        TreeNode root2 = new TreeNode(2, 1, 4);
        List<Integer> list1 = getMidOrder(root);
        List<Integer> list2 = getMidOrder(root2);
        System.out.println(list1.toString());
//        List<Integer> list2 = Arrays.asList(1,3,5);
//        List<Integer> list1 = Arrays.asList(2,1,4);
        List<Integer> integers = mergeTrees(list1, list2);
        System.out.println(integers.toString());
    }
}
