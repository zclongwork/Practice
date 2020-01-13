package com.zcl.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 广度优先算法 ：解决 A-->B 有无路径及最短路径问题
 * 从起点开始，把所有临近节点都加入队列，然后标记下这些顶点离起始顶点的距离为1，并将起始顶点标记为已访问，今后就不会再访问。
 * 然后再从队列中取出最先进队的顶点A，也取出其周边邻近节点，加入队列末尾，将这些顶点的距离相对A再加1，最后离开这个顶点A。
 * 依次下去，直到队列为空为止。
 *
 * 从上面描述的过程我们知道每个顶点被访问的次数最多一次（已访问的节点不会再访问），而对于连通图来说，
 * 每个顶点都会被访问。加上每个顶点的邻接链表都会被遍历，因此BFS的时间复杂度是Θ（V+E），
 * 其中V是顶点个数，E是边数，也就是所有邻接表中的元素个数
 *
 * https://www.cnblogs.com/developerY/p/3323264.html
 *
 */
public class BreadthFirstSearch {


    private static void bfs(HashMap<Character, LinkedList<Character>> graph, HashMap<Character, Integer> dist, char start) {
        Queue<Character> q = new LinkedList<>();
        q.add(start);//将s作为起始顶点加入队列
        dist.put(start, 0);
        int i = 0;
        while (!q.isEmpty()) {
            char top = q.poll();//取出队首元素
            i++;
            System.out.println("The " + i + "th element:" + top + " Distance from s is:" + dist.get(top));
            int d = dist.get(top) + 1;//得出其周边还未被访问的节点的距离
            for (Character c : graph.get(top)) {
                if (!dist.containsKey(c))//如果dist中还没有该元素说明还没有被访问
                {
                    dist.put(c, d);
                    q.add(c);
                }
            }
        }
    }
}
