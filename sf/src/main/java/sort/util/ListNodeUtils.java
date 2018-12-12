package sort.util;

import leetcode.data.ListNode;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 16:32 2018/12/12
 */
public class ListNodeUtils {

	public static void print(ListNode node) {
		while (node != null) {
			System.out.println(node.val);
			node = node.next;
		}
	}

	public static ListNode createListNode(int [] values) {
		ListNode dummy = new ListNode(-1), cur = dummy;
		for (int i = 0; i < values.length; i++) {
			cur.next = new ListNode(values[i]);
			cur = cur.next;
		}
		return dummy.next;
	}

}
