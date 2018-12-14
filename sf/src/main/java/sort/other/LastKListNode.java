package sort.other;

import leetcode.data.ListNode;
import sort.util.ListNodeUtils;

public class LastKListNode {
    /**
     * 求倒数第K个节点
     * 快慢指针 倒数第k个指针 和 倒数第一个指针相差 k-1 步
     * 当快慢指针相差1步 快指针比慢指针先走1步 也就是快指针比慢指针 先走k-1步 然后同时走 当快指针到达尾部时 慢指针就是倒数第k个指针
     */

    public static ListNode findLastK1(ListNode h, int k) {
        if (h == null) return null;
        ListNode f = h, s = h;
        int count = 1;
        for (int i = 0; i < k - 1; i++) {
			count ++;
            if (f.next != null) {
				f = f.next;
				continue;
            }
            return null;

        }
        while (f.next != null) {
            count ++;
            f = f.next;
            s = s.next;
        }

        if (count < k) return null;
        return s;
    }

    /**
     *  什么时候 快慢指针开始同时走呢 当快指针比慢指针多走K-1步的时候
     */
    public static ListNode findLastK(ListNode head, int k) {
        int count = 0;
        ListNode p = head, q = head;
        while (p != null) {
            //count = 1 时 p q相等 -> count -1 > k - 1;
            count ++;
            p = p.next;
            if (count > k) {
                q = q.next;
            }
        }
        if (count < k) return null;
        return q;
    }

    public static void main(String[] args) {
        ListNode listNode = ListNodeUtils.createListNode(new int[]{1, 2, 3, 4, 5});
        System.out.println(findLastK1(listNode, 5).val);
    }
}
