package leetcode;

import leetcode.data.ListNode;
import sort.util.ListNodeUtils;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 16:53 2018/12/12
 */
public class Leetcode61 {

	/**
	 * Given a linked list, rotate the list to the right by k places, where k is non-negative.
	 *
	 * Example 1:
	 *
	 * Input: 1->2->3->4->5->NULL, k = 2
	 * Output: 4->5->1->2->3->NULL
	 * Explanation:
	 * rotate 1 steps to the right: 5->1->2->3->4->NULL
	 * rotate 2 steps to the right: 4->5->1->2->3->NULL
	 * Example 2:
	 *
	 * Input: 0->1->2->NULL, k = 4
	 * Output: 2->0->1->NULL
	 * Explanation:
	 * rotate 1 steps to the right: 2->0->1->NULL
	 * rotate 2 steps to the right: 1->2->0->NULL
	 * rotate 3 steps to the right: 0->1->2->NULL
	 * rotate 4 steps to the right: 2->0->1->NULL
	 */

	/**
	 * 61. Rotate List
	 *
	 * First Pass: Find the length of the list and get the tail node
	 * % k by length
	 * Second Pass: Find the (length - k) node (the new head node)
	 * Set tail next to head, new tail to null
	 *
	 * @param head
	 * @param k
	 * @return
	 */
	public static ListNode rotateRight(ListNode head, int k) {
		if (head == null || head.next == null) return head;
		int len = 1;
		//尾节点
		ListNode tail = head;
		while (tail.next != null) {
			len++;
			tail = tail.next;
		}
		//需要前置的节点个数 从尾节点向前数
		k = k % len;
		if (k == 0) return head;

		//新的尾巴结点
		ListNode cur = head;
		for (int i = 0; i < len - k - 1; i++) {
			cur = cur.next;
		}

		//pre = [3,4,5]
		ListNode pre = cur;
		//cur = [4,5]
		cur = cur.next;
		//tail = [5] 这一步形成了循环链表
		tail.next = head;
		pre.next = null;

		return cur;
	}

	public static void main(String[] args) {
		ListNodeUtils.print(rotateRight(ListNodeUtils.createListNode(new int[] {1,2,3,4,5}), 2));
	}
}
