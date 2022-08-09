package com.example.leetcode.daily;

import io.swagger.models.auth.In;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.springframework.security.core.parameters.P;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
完全二叉树 是每一层（除最后一层外）都是完全填充（即，节点数达到最大）的，并且所有的节点都尽可能地集中在左侧。
 */
public class Leet0725 {
//    public Leet0725(TreeNode root) {
//
//    }

//    public int insert(int val) {
//
//    }
//
//    public TreeNode get_root() {
//
//    }

    /**
     * 层序遍历一颗树
     * 思路其实就是一个对先进先出的队列设计
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null ) {
            return new ArrayList<>();
        }
        Queue<TreeNode> queue = new LinkedList();
        List<List<Integer>> result = new ArrayList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> item = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode peek = queue.poll();
                item.add(peek.val);
                TreeNode left = peek.left;
                TreeNode right = peek.right;
                if (left != null) {
                    queue.add(left);
                }
                if (right != null) {
                    queue.add(right);
                }
            }
            result.add(item);
        }
        return result;
    }

    @Test
    public void ts() {
        TreeNode right = new TreeNode(20, new TreeNode(15), new TreeNode((7)));
        TreeNode left = new TreeNode(9);
        TreeNode root = new TreeNode(3, left, right);
        List<List<Integer>> lists = levelOrder(root);
        System.out.println(lists.toString());
    }
}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}