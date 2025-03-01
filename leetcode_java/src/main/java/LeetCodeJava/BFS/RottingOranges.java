package LeetCodeJava.BFS;

// https://leetcode.com/problems/rotting-oranges/
/**
 * 994. Rotting Oranges
 * Solved
 * Medium
 * Topics
 * Companies
 * You are given an m x n grid where each cell can have one of three values:
 *
 * 0 representing an empty cell,
 * 1 representing a fresh orange, or
 * 2 representing a rotten orange.
 * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
 *
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 * Example 2:
 *
 * Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
 * Example 3:
 *
 * Input: grid = [[0,2]]
 * Output: 0
 * Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
 *
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 10
 * grid[i][j] is 0, 1, or 2.
 */
import java.util.*;

public class RottingOranges {

    // TODO : fix below
    // VO
    // IDRA : BFS
//    public int orangesRotting(int[][] grid) {
//
//        class Pair<U, V> {
//            U key;
//            V value;
//
//            Pair(U key, V value) {
//                this.key = key;
//                this.value = value;
//            }
//
//            U getKey() {
//                return this.key;
//            }
//
//            V getValue() {
//                return this.value;
//            }
//
//        }
//
//        int len = grid.length;
//        int width = grid[0].length;
//
//        int ans = 0;
//        int fresh_cnt = 0;
//
//        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
//        Queue<Pair> q = new LinkedList<>();
//
//        // collect rotting orange
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < len; j++) {
//                if (grid[j][i] == 2) {
//                    q.add(new Pair(i, j));
//                } else if (grid[j][i] == 1) {
//                    fresh_cnt += 1;
//                }
//            }
//        }
//
//        if (fresh_cnt == 0) {
//            return 0; // ?
//        }
//
//        // bfs
//        while (!q.isEmpty()) {
//
//            Pair p = q.poll();
//            int x = (int) p.getKey();
//            int y = (int) p.getValue();
//
//            boolean finish_cycle = false;
//
//            for (int[] dir : dirs) {
//
//                int dx = dir[0];
//                int dy = dir[1];
//
//                int new_x = x + dx;
//                int new_y = y + dy;
//
//                if (new_x >= 0 && new_x < width && new_y >= 0 && new_y < len) {
//                    if (grid[new_y][new_x] == 1) {
//                        q.add(new Pair(new_x, new_y));
//                        grid[new_y][new_x] = 2; // become rotting orange
//                        fresh_cnt -= 1;
//
//                    }
//                }
//
//                if (fresh_cnt == 0) {
//                    return ans;
//                }
//
//                finish_cycle = true;
//            }
//
//            if (finish_cycle) {
//                ans += 1;
//            }
//        }
//
//        System.out.println("fresh_cnt = " + fresh_cnt + " ans = " + ans);
//        return fresh_cnt == 0 ? ans : -1;
//    }

    // TODO : fix below
    // V0
    // IDEA : BFS
    // https://github.com/yennanliu/CS_basics/blob/master/leetcode_python/Breadth-First-Search/rotting-oranges.py
//    public int orangesRotting(int[][] grid) {
//
//        class Pair<U, V, W> {
//
//            U key;
//            V value;
//            W value2;
//
//            Pair(U key, V value, W value2) {
//                this.key = key;
//                this.value = value;
//                this.value2 = value2;
//            }
//
//            public U getKey() {
//                return this.key;
//            }
//
//            public V getValue() {
//                return this.value;
//            }
//
//            public W getValue2() {
//                return this.value2;
//            }
//
//        }
//
//        // Queue<Pair<Integer, Integer>> queue = new ArrayDeque();
//        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
//        int fresh = 0;
//        Queue<Pair<Integer, Integer, Integer>> rotting = new LinkedList<>();
//        HashSet<String> set = new HashSet<>();
//
//        int _len = grid.length;
//        int _width = grid[0].length;
//
//        for (int i = 0; i < _width; i++) {
//            for (int j = 0; j < _len; j++) {
//                if (grid[j][i] == 2) {
//                    // TODO : check difference : offer VS add
//                    //rotting.offer(new Pair<>(i, j));
//                    rotting.add(new Pair<>(i, j, 0));
//                }
//                if (grid[j][i] == 1) {
//                    fresh += 1;
//                }
//            }
//        }
//
//        if (fresh == 0) {
//            return 0;
//        }
//
//        // bfs
//        while (!rotting.isEmpty()) {
//
//            Pair<Integer, Integer, Integer> _cur = rotting.poll();
//            int x = _cur.getKey();
//            int y = _cur.getValue();
//            int _time = _cur.getValue2();
//
//            // go 4 directions
//            for (int[] dir : dirs) {
//                int dx = dir[0];
//                int dy = dir[1];
//                int _x = x + dx;
//                int _y = y + dy;
//                String idx = String.valueOf(x) + "-" + String.valueOf(y); // to avoid redo op (fresh -= 1, rotting.add) "visited" points
//                if (_x >= 0 && x < _width - 1 && _y >= 0 && _y < _len - 1) {
//                    if (grid[_y][_x] == 1 && !set.contains(idx)){
//                        grid[_y][_x] = 2;
//                        fresh -= 1;
//                        set.add(idx);
//                        System.out.println(">>> fresh = " + fresh + " idx = " + idx);
//                        if (fresh == 0) {
//                            return _time+1;
//                        }
//                        rotting.add(new Pair<>(_x, _y, _time+1));
//                    }
//                }
//            }
//        }
//
//        return fresh == 0 ? 0 : -1;
//    }

    // V1-1
    // https://neetcode.io/problems/rotting-fruit
    // IDEA: BFS
    public int orangesRotting_1_1(int[][] grid) {
        Queue<int[]> q = new ArrayDeque<>();
        int fresh = 0;
        int time = 0;

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) {
                    fresh++;
                }
                if (grid[r][c] == 2) {
                    q.offer(new int[]{r, c});
                }
            }
        }

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (fresh > 0 && !q.isEmpty()) {
            int length = q.size();
            for (int i = 0; i < length; i++) {
                int[] curr = q.poll();
                int r = curr[0];
                int c = curr[1];

                for (int[] dir : directions) {
                    int row = r + dir[0];
                    int col = c + dir[1];
                    if (row >= 0 && row < grid.length &&
                            col >= 0 && col < grid[0].length &&
                            grid[row][col] == 1) {
                        grid[row][col] = 2;
                        q.offer(new int[]{row, col});
                        fresh--;
                    }
                }
            }
            time++;
        }
        return fresh == 0 ? time : -1;
    }

    // V1-2
    // https://neetcode.io/problems/rotting-fruit
    // IDEA: BFS (No Queue)
    public int orangesRotting_1_2(int[][] grid) {
        int ROWS = grid.length, COLS = grid[0].length;
        int fresh = 0, time = 0;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == 1) fresh++;
            }
        }

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (fresh > 0) {
            boolean flag = false;
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (grid[r][c] == 2) {
                        for (int[] d : directions) {
                            int row = r + d[0], col = c + d[1];
                            if (row >= 0 && col >= 0 &&
                                    row < ROWS && col < COLS &&
                                    grid[row][col] == 1) {
                                grid[row][col] = 3;
                                fresh--;
                                flag = true;
                            }
                        }
                    }
                }
            }

            if (!flag) return -1;

            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (grid[r][c] == 3) grid[r][c] = 2;
                }
            }

            time++;
        }

        return time;
    }

    // V2
    // IDEA : BFS
    // https://leetcode.com/problems/rotting-oranges/editorial/
    class Pair<U, V> {

        U key;
        V value;

        Pair(U key, V value) {
            this.key = key;
            this.value = value;
        }

        public U getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

    }

    public int orangesRotting_2(int[][] grid) {
        Queue<Pair<Integer, Integer>> queue = new ArrayDeque();

        // Step 1). build the initial set of rotten oranges
        int freshOranges = 0;
        int ROWS = grid.length, COLS = grid[0].length;

        for (int r = 0; r < ROWS; ++r)
            for (int c = 0; c < COLS; ++c)
                if (grid[r][c] == 2)
                    queue.offer(new Pair(r, c));
                else if (grid[r][c] == 1)
                    freshOranges++;

        // Mark the round / level, _i.e_ the ticker of timestamp
        queue.offer(new Pair(-1, -1));

        // Step 2). start the rotting process via BFS
        int minutesElapsed = -1;
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> p = queue.poll();
            int row = p.getKey();
            int col = p.getValue();
            if (row == -1) {
                // We finish one round of processing
                minutesElapsed++;
                // to avoid the endless loop
                if (!queue.isEmpty())
                    queue.offer(new Pair(-1, -1));
            } else {
                // this is a rotten orange
                // then it would contaminate its neighbors
                for (int[] d : directions) {
                    int neighborRow = row + d[0];
                    int neighborCol = col + d[1];
                    if (neighborRow >= 0 && neighborRow < ROWS &&
                            neighborCol >= 0 && neighborCol < COLS) {
                        if (grid[neighborRow][neighborCol] == 1) {
                            // this orange would be contaminated
                            grid[neighborRow][neighborCol] = 2;
                            freshOranges--;
                            // this orange would then contaminate other oranges
                            queue.offer(new Pair(neighborRow, neighborCol));
                        }
                    }
                }
            }
        }

        // return elapsed minutes if no fresh orange left
        return freshOranges == 0 ? minutesElapsed : -1;
    }

    // V3
    // IDEA : in place BFS
    // https://leetcode.com/problems/rotting-oranges/editorial/
    // run the rotting process, by marking the rotten oranges with the timestamp
    public boolean runRottingProcess(int timestamp, int[][] grid, int ROWS, int COLS) {
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        // flag to indicate if the rotting process should be continued
        boolean toBeContinued = false;
        for (int row = 0; row < ROWS; ++row)
            for (int col = 0; col < COLS; ++col)
                if (grid[row][col] == timestamp)
                    // current contaminated cell
                    for (int[] d : directions) {
                        int nRow = row + d[0], nCol = col + d[1];
                        if (nRow >= 0 && nRow < ROWS && nCol >= 0 && nCol < COLS)
                            if (grid[nRow][nCol] == 1) {
                                // this fresh orange would be contaminated next
                                grid[nRow][nCol] = timestamp + 1;
                                toBeContinued = true;
                            }
                    }
        return toBeContinued;
    }

    public int orangesRotting_3(int[][] grid) {
        int ROWS = grid.length, COLS = grid[0].length;
        int timestamp = 2;
        while (runRottingProcess(timestamp, grid, ROWS, COLS))
            timestamp++;

        // end of process, to check if there are still fresh oranges left
        for (int[] row : grid)
            for (int cell : row)
                // still got a fresh orange left
                if (cell == 1)
                    return -1;


        // return elapsed minutes if no fresh orange left
        return timestamp - 2;
    }

}
