package com.example.leetcode.daily;

public class Leet0409 {
    /**0409
     * @https://leetcode-cn.com/problems/reaching-points/
     * @param sx
     * @param sy
     * @param tx
     * @param ty
     * @return
     * 思路：就是很容易想到的是一个深度，还是一个有记忆的搜索！
     * 2，动态规划。d代表了此刻的state;
     * f(x,y) = f(x-1,y) + d[x-1][y] | f(x,y-1) + d[x][y-1]
     * 什么时候停止呢？x相同或y相同
     */
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
       while (tx > 0 && ty > 0 ) {
           if (sx == tx && sy == ty) {
               return true;
           }

           if (tx > ty) {
               if(tx - sx < ty)
                   return false;
               tx -= Math.max(1, (tx-sx)/ty)*ty;
           } else {
               // 数据剪枝；对数据不满足的直接返回了。
               if(ty - sy < tx)
                   return false;
               ty -= Math.max(1, (ty- sy)/tx) * tx;
           }
       }
       return false;
    }

    public void dfs(int[] finalPoint, int tx, int ty) {
        // 只要有值相同或者大于的时候
        if (finalPoint[0] >= tx || finalPoint[1] >= ty) {
            return;
        }


    }

    public boolean isEnd(int[] points, int tx, int ty) {
       if ((points[0] == tx && points[0]+points[1] == ty)
       ||(points[0] == ty && points[0]+points[1] == tx))
           return true;
       return false;
    }

}
