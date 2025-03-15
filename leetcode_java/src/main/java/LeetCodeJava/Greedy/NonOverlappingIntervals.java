package LeetCodeJava.Greedy;

// https://leetcode.com/problems/non-overlapping-intervals/description/
/**
 * 435. Non-overlapping Intervals
 * Solved
 * Medium
 * Topics
 * Companies
 * Given an array of intervals intervals where intervals[i] = [starti, endi], return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
 *
 * Note that intervals which only touch at a point are non-overlapping. For example, [1, 2] and [2, 3] are non-overlapping.
 *
 *
 *
 * Example 1:
 *
 * Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
 * Output: 1
 * Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.
 * Example 2:
 *
 * Input: intervals = [[1,2],[1,2],[1,2]]
 * Output: 2
 * Explanation: You need to remove two [1,2] to make the rest of the intervals non-overlapping.
 * Example 3:
 *
 * Input: intervals = [[1,2],[2,3]]
 * Output: 0
 * Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
 *
 *
 * Constraints:
 *
 * 1 <= intervals.length <= 105
 * intervals[i].length == 2
 * -5 * 104 <= starti < endi <= 5 * 104
 *
 */
import java.util.Arrays;
import java.util.Stack;
import java.util.Comparator;

public class NonOverlappingIntervals {

    // V0
    // IDEA : sorting + intervals (modified by GPT)
    // https://github.com/yennanliu/CS_basics/blob/master/leetcode_python/Greedy/non-overlapping-intervals.py
    public int eraseOverlapIntervals(int[][] intervals) {

        /** NOTE !!!
         *
         *   sort on 2nd element in Ascending order (small -> big)
         *
         *   same as this op: `Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));`
         *
         *
         *
         *   1) Why Not Sort by Start Time ?
         *
         *    -> If we sorted by the start time (intervals[i][0]),
         *       it would not necessarily minimize the number of intervals to
         *       remove because we wouldn't have the benefit of always selecting the
         *       interval that finishes the earliest, which is crucial for maximizing
         *       the number of non-overlapping intervals.
         *
         *    -> In conclusion, sorting by end time provides the correct and
         *       optimal solution for this problem.
         *
         *
         *  2)  Key Concept:
         *     -> The goal of the problem is to remove the minimum number of intervals to
         *        ensure that no two intervals overlap. To solve this optimally,
         *        sorting by the end time is the preferred approach. This is because:
         *
         *     -> Once you have sorted intervals by their `end time`,
         *        you can greedily choose the intervals that end earliest,
         *        leaving more room for subsequent intervals. This helps you avoid
         *        overlap and minimize the number of intervals you need to remove.
         *
         *
         *  3) Why Sorting by Start Time is Problematic:
         *     -> If you sort by the start time instead of the end time,
         *        you might end up with overlapping intervals that are harder to manage.
         */
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));

        int cnt = 0;
        int[] last = intervals[0];

        // NOTE !! start from idx = 1
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < last[1]) {
                cnt++;
            } else {
                /**
                 *  NOTE !!!
                 *
                 *  if NOT overlap, need to use `bigger` val as 2nd element in last element
                 *
                 *  e.g.
                 *  // No overlap, update the lastEndTime
                 */
                last[1] = Math.max(intervals[i][1], last[1]);
            }
        }
        return cnt;
    }

    // V0-1
    // IDEA : array + boundary op (GPT)
    public int eraseOverlapIntervals_0_1(int[][] intervals) {

        if (intervals.length <= 1) {
            return 0;
        }

        int res = 0;

        // Sort intervals by their end time (second element)
        /** NOTE !!!
         *
         *   sort on 2nd element
         *
         *   -> Ascending order (small -> big)
         *
         *   same as this op: `Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));`
         */
        Arrays.sort(intervals, Comparator.comparingInt(x -> x[1]));

        // Keep track of the end time of the last interval that doesn't overlap
        int lastEndTime = intervals[0][1];

        // NOTE !! start from idx = 1
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < lastEndTime) {
                // Overlapping interval, increment the count
                res++;
            } else {
                // No overlap, update the lastEndTime
                lastEndTime = intervals[i][1];
            }
        }

        return res;
    }

    // V0-2
    // IDEA: SORT + ARRAY OP (GPT)
    public int eraseOverlapIntervals_0_2(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // Step 1: Sort intervals by `end` time
        /** NOTE !!!
         *
         *   sort on 2nd element
         *
         *   -> Ascending order (small -> big)
         *
         *   same as this op: `Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));`
         */
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        int count = 0;
        int prevEnd = intervals[0][1]; // Track last added interval's end

        // Step 2: Iterate through intervals
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < prevEnd) {
                count++; // Overlapping, remove one interval
            } else {
                prevEnd = intervals[i][1]; // Update end time if no overlap
            }
        }

        return count; // Number of intervals removed
    }


    // V0''
    // TODO : implement it
    // https://github.com/yennanliu/CS_basics/blob/master/leetcode_python/Greedy/non-overlapping-intervals.py
//    public int eraseOverlapIntervals(int[][] intervals) {
//        return 0;
//    }

    // V1
    // IDEA : STACK
    // https://leetcode.com/problems/non-overlapping-intervals/solutions/4683650/4-line-solution-easy-to-understand-java-stack/
    public int eraseOverlapIntervals_1(int[][] intervals) {
        Arrays.sort(intervals,(a, b)->a[1]-b[1]);
        Stack<int[]> stk = new Stack<>();
        int res=0;
        for(int[] i : intervals){
            if(!stk.isEmpty() && i[0]< stk.peek()[1]){
                res++;
            }else{
                stk.push(i);
            }
        }

        return res;
    }

    // V2
    // IDEA : SORT
    // https://leetcode.com/problems/non-overlapping-intervals/solutions/3786438/java-easy-solution-using-sorting-explained/
    public int eraseOverlapIntervals_2(int[][] intervals) {
        // Sort by ending time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        int prev = 0, count  = 1;
        // if end is same, sort by start, bigger start in front
        for(int i = 0; i < intervals.length; i ++) {
            if(intervals[i][0] >= intervals[prev][1]) {
                prev = i;
                count ++;
            }
        }
        return intervals.length - count;
    }

    // V3
    // https://leetcode.com/problems/non-overlapping-intervals/submissions/1202459919/
    public int eraseOverlapIntervals_3(int[][] in) {
        Arrays.sort(in, (a,b)->a[1]-b[1]);
        int res=-1, p[]=in[0];
        for(int[] i: in)
            if(i[0]<p[1]) res++;
            else p=i;
        return res;
    }

}
