package LeetCodeJava.LinkedList;

// https://leetcode.com/problems/reverse-linked-list-ii/description/

import LeetCodeJava.DataStructure.ListNode;

/**
 * 92. Reverse Linked List II
 * Solved
 * Medium
 * Topics
 * Companies
 * Given the head of a singly linked list and two integers left and right where left <= right, reverse the nodes of the list from position left to position right, and return the reversed list.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: head = [1,2,3,4,5], left = 2, right = 4
 * Output: [1,4,3,2,5]
 * Example 2:
 *
 * Input: head = [5], left = 1, right = 1
 * Output: [5]
 *
 *
 * Constraints:
 *
 * The number of nodes in the list is n.
 * 1 <= n <= 500
 * -500 <= Node.val <= 500
 * 1 <= left <= right <= n
 *
 *
 * Follow up: Could you do it in one pass?
 *
 */

public class ReverseLinkedList2 {

  /**
   * Definition for singly-linked list.
   * public class ListNode {
   *     int val;
   *     ListNode next;
   *     ListNode() {}
   *     ListNode(int val) { this.val = val; }
   *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
   * }
   */

  // V0
  //    public ListNode reverseBetween(ListNode head, int left, int right) {
  //
  //    }

  // V0-1
  // IDEA: LINKED LIST OP (iteration 1)
  // https://neetcode.io/solutions/reverse-linked-list-ii
  // https://youtu.be/RF_M9tX4Eag?si=vTfAtfbmGwzsmtpi
  public ListNode reverseBetween_0_1(ListNode head, int left, int right) {

      /**
       * NOTE !!!
       *
       *  we init dummy, and point it to head
       */
      ListNode dummy = new ListNode(0);
      dummy.next = head;
      ListNode leftPrev = dummy, cur = head;

      /**
       * NOTE !!!
       *
       *  we move to `left - 1 ` idx (instead of `left`)
       *  -> for simpler operation (less edge cases)
       */
      for (int i = 0; i < left - 1; i++) {
          leftPrev = cur;
          cur = cur.next;
      }

      /**
       * NOTE !!!
       *
       *  1) we init prev as null
       *  2) we move i to `right - left + 1` idx,  (instead of `right - left`)
       */
      ListNode prev = null;
      for (int i = 0; i < right - left + 1; i++) {
          ListNode tmpNext = cur.next;
          cur.next = prev;
          prev = cur;
          cur = tmpNext;
      }

      /**
       * NOTE !!!
       *
       *  1) we point  leftPrev.next to `prev`, which is the head of `reverse linked list`
       *  2) we point  leftPrev.next.next to `cur`, since we still need the remaining linked list which is NOT reversed
       *          (e.g. linked list with idx > r)
       */
      leftPrev.next.next = cur;
      leftPrev.next = prev;

      return dummy.next;
  }

  // V0-2
  // IDEA: LINKED LIST OP (iteration 2)
  // https://neetcode.io/solutions/reverse-linked-list-ii
  // https://youtu.be/RF_M9tX4Eag?si=vTfAtfbmGwzsmtpi
  public ListNode reverseBetween_0_2(ListNode head, int left, int right) {
      ListNode dummy = new ListNode(0);
      dummy.next = head;
      ListNode prev = dummy;

      for (int i = 0; i < left - 1; i++) {
          prev = prev.next;
      }

      ListNode sublistHead = prev.next;
      ListNode sublistTail = sublistHead;
      for (int i = 0; i < right - left; i++) {
          sublistTail = sublistTail.next;
      }

      ListNode nextNode = sublistTail.next;
      sublistTail.next = null;
      prev.next = reverseList(sublistHead);
      sublistHead.next = nextNode;

      return dummy.next;
  }

    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

  // V1
  // IDEA: LINKED LIST (GPT)
  /**
   *  IDEA:
   *
   *  	1.	Use a dummy node to handle cases where left = 1 (reversal starts at the head).
   * 	2.	Traverse to the node before left (prev).
   * 	3.	Reverse the sublist using the standard in-place reversal technique:
   * 	    •	Maintain a start pointer at left.
   * 	    •	Use then to iterate and reverse the segment between left and right.
   * 	4.	Reconnect the reversed sublist properly.
   *
   *   Time & Space Complexity
   * 	•	Time Complexity: O(N) (traverse once and reverse a portion)
   * 	•	Space Complexity: O(1) (in-place reversal, no extra space)
   */
  public ListNode reverseBetween_1(ListNode head, int left, int right) {
        // Edge case: if the list is empty or has only one node
        if (head == null || head.next == null || left == right) {
            return head;
        }

        // Dummy node to handle edge cases where reversal starts at the head
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        // Move `prev` to just before the `left` position
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }

        // `start` will be the first node of the sublist to reverse
        ListNode start = prev.next;
        ListNode then = start.next;

        // Reverse the sublist
        for (int i = 0; i < right - left; i++) {
            start.next = then.next;
            then.next = prev.next;
            prev.next = then;
            then = start.next;
        }

        return dummy.next; // Return the modified list head
    }

    // V2
    // IDEA: LINKED LIST
    // https://leetcode.com/problems/reverse-linked-list-ii/solutions/30666/simple-java-solution-with-clear-explanat-yd1u/
    public ListNode reverseBetween_2(ListNode head, int m, int n) {
        if (head == null)
            return null;
        ListNode dummy = new ListNode(0); // create a dummy node to mark the head of this list
        dummy.next = head;
        ListNode pre = dummy; // make a pointer pre as a marker for the node before reversing
        for (int i = 0; i < m - 1; i++)
            pre = pre.next;

        ListNode start = pre.next; // a pointer to the beginning of a sub-list that will be reversed
        ListNode then = start.next; // a pointer to a node that will be reversed

        // 1 - 2 -3 - 4 - 5 ; m=2; n =4 ---> pre = 1, start = 2, then = 3
        // dummy-> 1 -> 2 -> 3 -> 4 -> 5

        for (int i = 0; i < n - m; i++) {
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }

        // first reversing : dummy->1 - 3 - 2 - 4 - 5; pre = 1, start = 2, then = 4
        // second reversing: dummy->1 - 4 - 3 - 2 - 5; pre = 1, start = 2, then = 5
        // (finish)

        return dummy.next;
    }

    // V3
    // https://leetcode.com/problems/reverse-linked-list-ii/solutions/2311084/javac-tried-to-explain-every-step-by-hi-5w5kl/
    public ListNode reverseBetween_3(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0); // created dummy node
        dummy.next = head;
        ListNode prev = dummy; // intialising prev pointer on dummy node

        for (int i = 0; i < left - 1; i++)
            prev = prev.next; // adjusting the prev pointer on it's actual index

        ListNode curr = prev.next; // curr pointer will be just after prev
        // reversing
        for (int i = 0; i < right - left; i++) {
            ListNode forw = curr.next; // forw pointer will be after curr
            curr.next = forw.next;
            forw.next = prev.next;
            prev.next = forw;
        }
        return dummy.next;
    }

    // V3
    // https://leetcode.com/problems/reverse-linked-list-ii/solutions/4011862/9240-two-pointers-stack-recursion-by-van-sz5v/

}
