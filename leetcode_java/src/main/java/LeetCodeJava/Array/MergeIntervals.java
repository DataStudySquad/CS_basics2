package LeetCodeJava.Array;

// https://leetcode.com/problems/merge-intervals/

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

    // TODO : fix V0
    // V0
    // IDEA : ARRAY OP + BOUNDARY OP
//    public int[][] merge(int[][] intervals) {
//
//        if (intervals == null || intervals.length == 0){
//            return intervals;
//        }
//
//        // sort
////        Arrays.stream(intervals).sorted();
////
////        Arrays.stream(intervals).sorted((x,y) -> {
////            // Step 1) compare first element
////            if (x[0] > 0) {
////                return 1;
////            } else if (x[0] > 0) {
////                return -1;
////            }
////
////            // Step 2) compare second element
////
////            return 0;
////        });
//
//        int[] tmp = new int[]{};
//
//        return intervals;
//    }

    // V1
    // IDEA : Sorting
    // https://leetcode.com/problems/merge-intervals/editorial/
    public int[][] merge_1(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
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