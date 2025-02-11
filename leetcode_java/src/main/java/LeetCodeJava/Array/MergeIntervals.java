package LeetCodeJava.Array;

// https://leetcode.com/problems/merge-intervals/
/**
 * 56. Merge Intervals
 * Solved
 * Medium
 * Topics
 * Companies
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
 *
 *
 *
 * Example 1:
 *
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
 * Example 2:
 *
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 *
 *
 * Constraints:
 *
 * 1 <= intervals.length <= 104
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 104
 *
 */
import java.util.*;

public class MergeIntervals {

    /**
     *  Exp 1:
     *      input = [[1,3],[2,6],[8,10],[15,18]]
     *
     *      Step 1) : sort (1st element small -> big, then 2nd element, small -> big)
     *
     *           [[1,3],[2,6],[8,10],[15,18]]
     *
     *     Step 2) : merge interval
     *
     *          [[1,3]]
     *
     *          [[1,6]]
     *
     *          [[1,6], [8,10]]
     *
     *          [[1,6], [8,10], [15,18]]
     */

    // V0
    // IDEA : ARRAY OP + BOUNDARY OP
    public int[][] merge(int[][] intervals) {
        /**
         *
         *
         *  1) Arrays.sort(intervals, ...) is used to sort the intervals array.
         *
         *  2) (a, b) -> Integer.compare(a[0], b[0]) is a lambda expression used as a comparator for sorting.
         *
         *  3) a and b are two intervals (arrays) being compared.
         *     a[0] and b[0] are the first elements of the intervals, which represent the start values of the intervals.
         *     Integer.compare(a[0], b[0])
         *     compares the start values of intervals a and b and
         *     returns -1 if a should come before b,
         *     0 if they are equal,
         *     and 1 if b should come before a.
         *
         *  -> Putting it all together, the Arrays.sort method uses the provided comparator (a, b) -> Integer.compare(a[0], b[0]) to sort the intervals array based on the start values of the intervals.
         *
         */
        // NOTE !!! sort on 1st element
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        // NOTE !!! we set res as linkedlist type (can use queue as well)
        LinkedList<int[]> res = new LinkedList<>();
        for (int[] interval : intervals) {
            // if the list of merged intervals is empty or if the current
            // interval does not overlap with the previous, simply append it.
            if (res.isEmpty() || res.getLast()[1] < interval[0]) {
                res.add(interval);
            }
            // otherwise, there is overlap, so we merge the current and previous
            // intervals.
            else {
                res.getLast()[1] = Math.max(res.getLast()[1], interval[1]);
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    // V0-1
    // IDEA: SORT + ARRAY OP + BOUNDARY HANDLING
    public int[][] merge_0_1(int[][] intervals) {

        if (intervals.length <= 1){
            return intervals;
        }

        /** NOTE !!! array -> list */
        List<int[]> intervalList = new ArrayList<>(Arrays.asList(intervals));

        // sort on 1st element (0 idx)
        intervalList.sort(Comparator.comparingInt(x -> x[0]));

        List<int[]> tmp = new ArrayList<>();
        for (int[] x : intervalList){
            // case 1 : tmp is null
            if (tmp.size() == 0){
                tmp.add(x);
            }
            // case 2 : no overlap
            if (tmp.get(tmp.size() - 1)[1] < x[0]){
                tmp.add(x);
            }
            // case 3 : overlap
            else{
                int[] last = tmp.get(tmp.size()-1);
                tmp.remove(tmp.size()-1);
                tmp.add(new int[]{Math.min(last[0], x[0]), Math.max(last[1], x[1])});
            }
        }

        /** NOTE !!! list -> array */
        return tmp.toArray(new int[tmp.size()][]);
    }

    // V0-2
    // IDEA: ARRAY OP (GPT)
    public int[][] merge_0_2(int[][] intervals) {
        // Edge case: If intervals is null or empty, return an empty array
        if (intervals == null || intervals.length == 0) {
            return new int[][] {}; // Return empty 2D array
        }

        // Edge case: If only one interval, return the same interval
        if (intervals.length == 1) {
            return new int[][] { intervals[0] };
        }

        // Sorting the intervals based on the start of the intervals
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]); // Compare based on the start time
            }
        });

        List<int[]> cache = new ArrayList<>();

        // Iterate through the sorted intervals and merge them if necessary
        for (int i = 0; i < intervals.length; i++) {
            // If the cache is empty or no overlap with the last interval
            if (cache.isEmpty() || cache.get(cache.size() - 1)[1] < intervals[i][0]) {
                cache.add(new int[] { intervals[i][0], intervals[i][1] });
            } else {
                // Overlapping intervals: merge them
                int[] last = cache.get(cache.size() - 1);
                last[1] = Math.max(last[1], intervals[i][1]); // Merge the intervals by updating the end time
            }
        }

        // Convert the List<int[]> to a 2D array and return the result
        /** NOTE !!! below op */
        return cache.toArray(new int[cache.size()][]);
    }

    // V1
    // IDEA : Sorting
    // https://leetcode.com/problems/merge-intervals/editorial/
    public int[][] merge_1(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        // NOTE !!! we set res as linkedlist type
        LinkedList<int[]> merged = new LinkedList<>();
        for (int[] interval : intervals) {
            // if the list of merged intervals is empty or if the current
            // interval does not overlap with the previous, simply append it.
            if (merged.isEmpty() || merged.getLast()[1] < interval[0]) {
                merged.add(interval);
            }
            // otherwise, there is overlap, so we merge the current and previous
            // intervals.
            else {
                merged.getLast()[1] = Math.max(merged.getLast()[1], interval[1]);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }

    // V2
    // IDEA : Connected Components
    // https://leetcode.com/problems/merge-intervals/editorial/
    private Map<int[], List<int[]>> graph;
    private Map<Integer, List<int[]>> nodesInComp;
    private Set<int[]> visited;

    // return whether two intervals overlap (inclusive)
    private boolean overlap(int[] a, int[] b) {
        return a[0] <= b[1] && b[0] <= a[1];
    }

    // build a graph where an undirected edge between intervals u and v exists
    // iff u and v overlap.
    private void buildGraph(int[][] intervals) {
        graph = new HashMap<>();
        for (int[] interval : intervals) {
            graph.put(interval, new LinkedList<>());
        }

        for (int[] interval1 : intervals) {
            for (int[] interval2 : intervals) {
                if (overlap(interval1, interval2)) {
                    graph.get(interval1).add(interval2);
                    graph.get(interval2).add(interval1);
                }
            }
        }
    }

    // merges all of the nodes in this connected component into one interval.
    private int[] mergeNodes(List<int[]> nodes) {
        int minStart = nodes.get(0)[0];
        for (int[] node : nodes) {
            minStart = Math.min(minStart, node[0]);
        }

        int maxEnd = nodes.get(0)[1];
        for (int[] node : nodes) {
            maxEnd = Math.max(maxEnd, node[1]);
        }

        return new int[] {minStart, maxEnd};
    }

    // use depth-first search to mark all nodes in the same connected component
    // with the same integer.
    private void markComponentDFS(int[] start, int compNumber) {
        Stack<int[]> stack = new Stack<>();
        stack.add(start);

        while (!stack.isEmpty()) {
            int[] node = stack.pop();
            if (!visited.contains(node)) {
                visited.add(node);

                if (nodesInComp.get(compNumber) == null) {
                    nodesInComp.put(compNumber, new LinkedList<>());
                }
                nodesInComp.get(compNumber).add(node);

                for (int[] child : graph.get(node)) {
                    stack.add(child);
                }
            }
        }
    }

    // gets the connected components of the interval overlap graph.
    private void buildComponents(int[][] intervals) {
        nodesInComp = new HashMap<>();
        visited = new HashSet<>();
        int compNumber = 0;

        for (int[] interval : intervals) {
            if (!visited.contains(interval)) {
                markComponentDFS(interval, compNumber);
                compNumber++;
            }
        }
    }

    public int[][] merge_2(int[][] intervals) {
        buildGraph(intervals);
        buildComponents(intervals);

        // for each component, merge all intervals into one interval.
        List<int[]> merged = new LinkedList<>();
        for (int comp = 0; comp < nodesInComp.size(); comp++) {
            merged.add(mergeNodes(nodesInComp.get(comp)));
        }

        return merged.toArray(new int[merged.size()][]);
    }

}
