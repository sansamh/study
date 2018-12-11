package leetcode;

import leetcode.data.TreeNode;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
            //当一个节点没有左节点时 才是第一个出栈的节点 出栈后需要判断是否有右节点
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

    /**
     * 层遍历 广度优先 使用队列
     */
    public List<List<Integer>> bfs(TreeNode root) {
		List<List<Integer>> res = new ArrayList<>();
		if (root == null) {
			return res;
		}
		Queue<TreeNode> queue = new LinkedBlockingQueue<>();
		queue.offer(root);

		while (!queue.isEmpty()) {
			//当前树层 有多少个节点
			int levelSize = queue.size();
			List<Integer> levelData = new ArrayList<>();
			for (int i = 0; i < levelSize; i++) {
				TreeNode poll = queue.poll();
				if (poll.left != null) {
					queue.offer(poll.left);
				}
				if (poll.right != null) {
					queue.offer(poll.right);
				}
				levelData.add(poll.val);
			}

			res.add(levelData);
		}

		return res;
    }
    /**
     * 层遍历 广度优先 不使用队列
	 * 其实就是每次都只存储一层的节点，然后遍历这一层的节点，是真正的按层遍历的思想。每次遍历的都是当前层，记录的都是当前层的下一层。
     */
    public List<List<Integer>> bfs2(TreeNode root) {
		List<List<Integer>> res = new ArrayList<>();
		if (root == null) {
			return res;
		}
		List<TreeNode> list = new ArrayList<>();
		list.add(root);

		while (!list.isEmpty()) {
			List<TreeNode> temp = new ArrayList<>();
			List<Integer> levelData = new ArrayList<>();

			for (TreeNode node : list) {
				levelData.add(node.val);
				if (node.left != null){
					temp.add(node.left);
				}
				if (node.right != null){
					temp.add(node.right);
				}
			}
			list = temp;
			res.add(levelData);
		}

		return res;
    }
}
