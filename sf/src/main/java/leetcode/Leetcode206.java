package leetcode;

import leetcode.data.ListNode;

/**
 * @version 1.0
 * @description: 单链表反转
 * @author: 侯春兵
 * @Date: 9:52 2018/12/12
 */
public class Leetcode206 {

	/**
	 * 递归
	 * @param head
	 * @return
	 */
	public static ListNode reverse(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		//head为倒数第二个节点时 n = 最后一个节点
		ListNode n = reverse(head.next);
		//head.next为最后一个节点 即是n， head为倒数第二个节点
		head.next.next = head;
		head.next = null;
		return n;
	}

	/**
	 * 非递归
	 * @param head
	 * @return
	 */
	public static ListNode reverse1(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode pre = null;
		ListNode cur = head;

		while (cur != null) {
			ListNode next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		return pre;
	}

	/**
	 * 非递归2 用值形成新的node
	 * @param head
	 * @return
	 */
	public static ListNode reverse2(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode dummy = new ListNode(head.val);
		while (head.next != null) {
			ListNode next = new ListNode(head.next.val);
			next.next = dummy;
			dummy = next;
			head = head.next;
		}
		return dummy;
	}
}
