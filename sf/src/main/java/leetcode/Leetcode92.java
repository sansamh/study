package leetcode;

import leetcode.data.ListNode;
import sort.util.ListNodeUtils;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 11:39 2018/12/12
 */
public class Leetcode92 {

	/**
	 * Reverse a linked list from position m to n. Do it in one-pass.
	 *
	 * Note: 1 ≤ m ≤ n ≤ length of list.
	 *
	 * Example:
	 *
	 * Input: 1->2->3->4->5->NULL, m = 2, n = 4
	 * Output: 1->4->3->2->5->NULL
	 */

	public static ListNode reverse(ListNode head, int m, int n) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode pre = dummy;
		for (int i = 0; i < m - 1; i++) {
			//i=0 pre=head;
			pre = pre.next;
		}
		// 因为 1 ≤ m ≤ n ≤ length 假设[1,2,3,4,5] m = 3，i<2, max(i) = 1, 经过2次交换 pre指向2

		//定义cur指针 指向m，post指针指向m+1
		ListNode cur = pre.next;
		ListNode post = cur.next;
		//只需要m-n次交换即可
		for (int j = 0; j < n - m; j++) {
			//交换
			cur.next = post.next;
			post.next = pre.next;
			pre.next = post;
			//移动post指针
			post = cur.next;
		}

		return dummy.next;
	}

    public static void main(String[] args) {
        ListNodeUtils.print(reverse(ListNodeUtils.createListNode(new int[]{1,2,3,4,5}), 2, 4));
    }
}
