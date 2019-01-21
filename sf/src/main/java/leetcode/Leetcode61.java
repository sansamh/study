package leetcode;

import leetcode.data.ListNode;
import utils.ListNodeUtils;

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
     * <p>
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
        ListNodeUtils.print(m2(ListNodeUtils.createListNode(new int[]{1, 2, 3, 4, 5}), 2));
    }

    public static ListNode m2(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode index = head;
        int len = 1;
        while (index.next != null) {
            index = index.next;
            len++;
        }
        //形成环
        index.next = head;

        for (int i = 1; i < len - k % len; i ++) {
            head = head.next;
        }
        // head指向新头节点的前一个节点 res新的头结点
        ListNode res = head.next;
        //前一个节点 断开 新的头结点
        head.next = null;
        return res;
    }
}
