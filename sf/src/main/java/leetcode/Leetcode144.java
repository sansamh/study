package leetcode;

import leetcode.data.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Leetcode144 {
    /**
     * 树
     */

    List<Integer> result = new LinkedList<>();

    /**
     * 递归 前序遍历
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        }

        result.add(root.val);
        preorderTraversal(root.left);
        preorderTraversal(root.right);

        return result;
    }

    /**
     * 非递归 前序 根 左 右
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            result.add(pop.val);
            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }
        return result;
    }

    /************************************************************************************/

    /**
     * 递归 中序遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        }

        preorderTraversal(root.left);
        result.add(root.val);
        preorderTraversal(root.right);

        return result;
    }

    /**
     * 非递归 中序 左 根 右
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        if (root == null) {
            return result;
        }

        //        1
        //    2       3
        //      4   5   6
        // 2 4 1 5 3 6

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);   //stack 1
        while (!stack.isEmpty()) {
            //先看左边的节点 不为空就取出来放进stack
            TreeNode pop = stack.peek();    // (1 pop = 1   // (2 pop = 2   // (3 pop = 4
            if (pop.left != null) { // (1 1.left = 2; stack = [2,1]; l.left == null; // (2 pop.left == null
                stack.push(pop.left);
                pop.left = null;
            } else {    // (2 result.add(2); stack = [1]
                result.add(pop.val);    // (3 result.add(4)
                stack.pop();
                if (pop.right != null) {    // (2 stack = [4,1]; (3 stack = [1]
                    stack.push(pop.right);
                }

            }
        }
        return result;
    }

    /************************************************************************************/
    /**
     * 递归 后序遍历
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        }

        postorderTraversal(root.left);
        postorderTraversal(root.right);
        result.add(root.val);

        return result;
    }

    /**
     * 非递归 后序 左 右 根
     * 一个节点 只有左右节点都被访问了 才能被访问
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal2(TreeNode root) {
        if (root == null) {
            return result;
        }

        //  1
        //2   3

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);   //stack 1
        while (!stack.isEmpty()) {
            TreeNode peek = stack.peek();
            if (peek.left == null && peek.right == null) {
                result.add(peek.val);
                stack.pop();
            } else {    // [2,3,1]
                if (peek.right != null) {
                    stack.push(peek.right);
                    peek.right = null;
                }
                if (peek.left != null) {
                    stack.push(peek.left);
                    peek.left = null;
                }
            }
        }
        return result;
    }
}
