package com.example.leetcode;

import ch.qos.logback.core.pattern.color.ANSIConstants;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leet563 {
    /**
     * 一共有2种情况：1，存在；2不存在；
     *
     * @param root
     * @return
     */
    public int findTilt(TreeNode root) {
        int ans = 0;

        return ans;
    }

    @Test
    public void main() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        System.out.println(findTilt(root));
    }

    //对一个数组进行反序列化

    /**
     * 对层序进行构造树
     * [4,2,9,3,5,null,7]
     */
    public TreeNode constructTree(Integer[] list) throws Exception {
        if (list == null) {
            throw new Exception("参数无效，请检查！");
        }
        TreeNode[] NodeArray = new TreeNode[list.length];
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                if (NodeArray[i] == null) {
                    NodeArray[i] = new TreeNode(list[i]);
                }
                int ind = 2 * i + 1;
                if (ind < list.length && list[ind] != null) {
                    NodeArray[ind] = new TreeNode(list[ind]);
                    NodeArray[i].left = NodeArray[ind];
                } else {
                    NodeArray[i].left = null;
                }
                ind++;
                if (ind < list.length && list[ind] != null) {
                    NodeArray[ind] = new TreeNode(list[ind]);
                    NodeArray[i].right = NodeArray[ind];
                } else {
                    NodeArray[i].right = null;
                }
            }
        }
        return NodeArray[0];
    }

    @Test
    public void mainTree() throws Exception {
        Integer[]  list = {1, 2, 3, null, 2, 3, 6};
        /*
                                                    1
                                            2           3
                                       null    2    3       6
        * */
//        System.out.println(1);
        System.out.println(constructTree(list));
    }

    /**559 @leetcode
     * 要求有对N茶树的最大的深度的获取
     * @param root
     * @return
     */
    public int maxDepth(NTreeNode root) {
        if (root == null) {
            return 0;
        }
        int sum = 0;
        List<NTreeNode> children = root.children;
        if (children != null) {
            for (NTreeNode child : children) {
                int deepItem = maxDepth(child);
                sum = Math.max(deepItem, sum);
            }
        }
        return sum+1;
    }

    @Test
    public void mainNTree() {
        NTreeNode root = new NTreeNode(1);
        root.children = Arrays.asList(new NTreeNode(2),new NTreeNode(3), new NTreeNode(4));
        NTreeNode last = new NTreeNode(5);
        root.children.get(0).children = Arrays.asList(last);
        System.out.println(maxDepth(root));

    }

}
