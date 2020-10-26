package com.zcl.algorithm;


import java.util.LinkedList;

/**
 * 1. 前序遍历：根结点 ---> 左子树 ---> 右子树
 * 2. 中序遍历：左子树---> 根结点 ---> 右子树
 * 3. 后序遍历：左子树 ---> 右子树 ---> 根结点
 * 4. 层次遍历：只需按层次遍历即可
 */
public class BinaryTree {

    /**
     *          1
     *      2            3
     *  4       5            6
     *     7       8
     */
    public static void main(String[] args) {
//        TreeNode root = new TreeNode(1);
//
//        TreeNode node5 = new TreeNode(5);
//        node5.left = new TreeNode(7);
//        node5.right = new TreeNode(8);
//
//        TreeNode node2 = new TreeNode(2);
//        node2.left = new TreeNode(4);
//        node2.right = node5;
//
//        TreeNode node3 = new TreeNode(3);
//        node3.right = new TreeNode(6);
//
//        root.left = node2;
//        root.right = node3;
//
////        preOrderTraverse1(root);
////        preOrderTraverse2(root);
////        inOrderTraverse1(root);
////        inOrderTraverse2(root);
////        postOrderTraverse1(root);
//        postOrderTraverse2(root);

        int[] arr = {1,3,0,8,0,4};
        test(arr);
    }

    public static void test(int[] arr) {
        int zero = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                zero++;
            } else if (zero != 0) {
                arr[i - zero] = arr[i];
                arr[i] = 0;
            }
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

    }

        /**
         * 递归前序遍历
         */
    public static void preOrderTraverse1(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + "  ");
            preOrderTraverse1(root.left);
            preOrderTraverse1(root.right);
        }
    }

    /**
     * 非递归前序遍历
     * 1）访问节点，并把结点node入栈，当前结点置为左孩子；
     * 2)判断结点node是否为空，若为空，则取出栈顶结点并出栈，将右孩子置为当前结点；
     * 否则重复1)步直到当前结点为空或者栈为空（可以发现栈中的结点就是为了访问右孩子才存储的）
     */
    public static void preOrderTraverse2(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode pNode = root;
        while (pNode != null || !stack.isEmpty()) {
            if (pNode != null) {
                System.out.print(pNode.val + "  ");
                stack.push(pNode);
                pNode = pNode.left;
            } else { //pNode == null && !stack.isEmpty()
                TreeNode node = stack.pop();
                pNode = node.right;
            }
        }
    }

        /**
         * 递归中序遍历
         *
         * @param root
         */
    public static void inOrderTraverse1(TreeNode root) {
        if (root != null) {
            inOrderTraverse1(root.left);
            System.out.print(root.val + "  ");
            inOrderTraverse1(root.right);
        }
    }



    public static void inOrderTraverse2(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode pNode = root;
        while (pNode != null || !stack.isEmpty()) {
            if (pNode != null) {
                stack.push(pNode);
                pNode = pNode.left;
            } else { //pNode == null && !stack.isEmpty()
                TreeNode node = stack.pop();
                System.out.print(node.val+"  ");
                pNode = node.right;
            }
        }

    }

        /**
         * 递归后序遍历
         *
         * @param root
         */
    public static void postOrderTraverse1(TreeNode root) {
        if (root != null) {
            postOrderTraverse1(root.left);
            postOrderTraverse1(root.right);
            System.out.print(root.val + "  ");
        }
    }

    /**
     * 后序遍历 左右根，是根右左的逆转 可结合前序遍历 进行修改后倒序即可
     * @param root
     */
    public static void postOrderTraverse2(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> result = new LinkedList<>();
        TreeNode pNode = root;
        while (pNode != null || !stack.isEmpty()) {
            if (pNode != null) {
//                System.out.print(pNode.val + "  ");
                result.push(pNode.val);
                stack.push(pNode);
                pNode = pNode.right;
            } else { //pNode == null && !stack.isEmpty()
                TreeNode node = stack.pop();
                pNode = node.left;
            }
        }

        while (!result.isEmpty()) {
            System.out.print(result.pop() + "  ");
        }
    }


    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
