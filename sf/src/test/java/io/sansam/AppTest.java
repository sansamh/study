package io.sansam;

import static org.junit.Assert.assertTrue;

import leetcode.data.ListNode;
import org.junit.Test;
import utils.ListNodeUtils;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    public static ListNode reverse(ListNode h) {
        if (h == null || h.next == null) return h;
        ListNode lastOne = reverse(h.next);
        //h倒数第二个
        h.next.next = h;
       h.next = null;
       return lastOne;
    }

    public static void main(String[] args) {
        ListNode h = ListNodeUtils.createListNode(new int[]{1, 2, 3});
        ListNodeUtils.print(reverse(h));
    }
}
