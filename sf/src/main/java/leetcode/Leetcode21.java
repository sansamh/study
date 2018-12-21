package leetcode;

import leetcode.data.ListNode;
import utils.ListNodeUtils;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 16:09 2018/12/12
 */
public class Leetcode21 {

	/**
	 * 递归
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		if (l1 == null) return l2;
		if (l2 == null) return l1;
		System.out.println(l1.val+" "+l2.val);
		if (l1.val < l2.val) {
			l1.next = mergeTwoLists(l1.next, l2);
			ListNodeUtils.print(l1);
			System.out.println("---------return l1----------");
			return l1;
		} else {
			l2.next = mergeTwoLists(l1, l2.next);
			ListNodeUtils.print(l2);
			System.out.println("---------return l2----------");
			return l2;
		}
	}

	/**
	 * 非递归
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
		if (l1 == null) return l2;
		if (l2 == null) return l1;

		ListNode dummy = new ListNode(0), cur = dummy;
		//当两个节点 其中一个为空时结束循环
		while (l1 != null && l2 != null) {
			if (l1.val < l2.val) {
				cur.next = l1;
				l1 = l1.next;
			} else {
				cur.next = l2;
				l2 = l2.next;
			}
			cur = cur.next;
			ListNodeUtils.print(cur);
			System.out.println("-------------------");
		}
		cur.next = (l1 == null ? l2 : l1);
		return dummy.next;
	}


	public static void main(String[] args) {
		ListNode l1_1 = new ListNode(1);
		ListNode l1_2 = new ListNode(2);
		ListNode l1_3 = new ListNode(4);

		ListNode l2_1 = new ListNode(1);
		ListNode l2_2 = new ListNode(3);
		ListNode l2_3 = new ListNode(4);

		l1_1.next = l1_2;
		l1_2.next = l1_3;

		l2_1.next = l2_2;
		l2_2.next = l2_3;

		ListNode listNode = mergeTwoLists2(l1_1, l2_1);

		System.out.println("---------main----------");
		ListNodeUtils.print(listNode);
	}
}
