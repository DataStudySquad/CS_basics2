package LeetCodeJava.BackTrack;

// https://leetcode.com/problems/n-queens/description/

import java.util.*;

/**
 * 51. N-Queens
 * Hard
 * Topics
 * Companies
 * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
 *
 * Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
 *
 * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: n = 4
 * Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above
 * Example 2:
 *
 * Input: n = 1
 * Output: [["Q"]]
 *
 *
 * Constraints:
 *
 * 1 <= n <= 9
 *
 */
public class NQueens {

    // V0
//    public List<List<String>> solveNQueens(int n) {
//
//    }

    // V1-1
    // https://neetcode.io/problems/n-queens
    // IDEA: BACKTRACK
    public List<List<String>> solveNQueens_1_1(int n) {
        List<List<String>> res = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        backtrack(0, board, res);
        return res;
    }

    private void backtrack(int r, char[][] board, List<List<String>> res) {
        if (r == board.length) {
            List<String> copy = new ArrayList<>();
            for (char[] row : board) {
                copy.add(new String(row));
            }
            res.add(copy);
            return;
        }
        for (int c = 0; c < board.length; c++) {
            if (isSafe(r, c, board)) {
                board[r][c] = 'Q';
                backtrack(r + 1, board, res);
                board[r][c] = '.';
            }
        }
    }

    private boolean isSafe(int r, int c, char[][] board) {
        for (int i = r - 1; i >= 0; i--) {
            if (board[i][c] == 'Q') return false;
        }
        for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }
        for (int i = r - 1, j = c + 1; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }
        return true;
    }

    // V1-2
    // https://neetcode.io/problems/n-queens
    // IDEA: BACKTRACK (HASHSET)
    Set<Integer> col = new HashSet<>();
    Set<Integer> posDiag = new HashSet<>();
    Set<Integer> negDiag = new HashSet<>();
    List<List<String>> res = new ArrayList<>();

    public List<List<String>> solveNQueens_1_2(int n) {
        char[][] board = new char[n][n];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }

        backtrack(0, n, board);
        return res;
    }

    private void backtrack(int r, int n, char[][] board) {
        if (r == n) {
            List<String> copy = new ArrayList<>();
            for (char[] row : board) {
                copy.add(new String(row));
            }
            res.add(copy);
            return;
        }

        for (int c = 0; c < n; c++) {
            if (col.contains(c) || posDiag.contains(r + c)
                    || negDiag.contains(r - c)) {
                continue;
            }

            col.add(c);
            posDiag.add(r + c);
            negDiag.add(r - c);
            board[r][c] = 'Q';

            backtrack(r + 1, n, board);

            col.remove(c);
            posDiag.remove(r + c);
            negDiag.remove(r - c);
            board[r][c] = '.';
        }
    }

    // V1-3
    // https://neetcode.io/problems/n-queens
    // IDEA: BACKTRACK  (Visited Array)
    boolean[] col_1_3, posDiag_1_3, negDiag_1_3;
    List<List<String>> res_1_3;
    char[][] board;

    public List<List<String>> solveNQueens_1_3(int n) {
        col_1_3 = new boolean[n];
        posDiag_1_3 = new boolean[2 * n];
        negDiag_1_3 = new boolean[2 * n];
        res_1_3 = new ArrayList<>();
        board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        backtrack(0, n);
        return res_1_3;
    }

    private void backtrack(int r, int n) {
        if (r == n) {
            List<String> copy = new ArrayList<>();
            for (char[] row : board) {
                copy.add(new String(row));
            }
            res_1_3.add(copy);
            return;
        }
        for (int c = 0; c < n; c++) {
            if (col_1_3[c] || posDiag_1_3[r + c] || negDiag_1_3[r - c + n]) {
                continue;
            }
            col_1_3[c] = true;
            posDiag_1_3[r + c] = true;
            negDiag_1_3[r - c + n] = true;
            board[r][c] = 'Q';

            backtrack(r + 1, n);

            col_1_3[c] = false;
            posDiag_1_3[r + c] = false;
            negDiag_1_3[r - c + n] = false;
            board[r][c] = '.';
        }
    }


    // V1-4
    // https://neetcode.io/problems/n-queens
    // IDEA: BACKTRACK  (Bit Mask)
//    int col = 0, posDiag = 0, negDiag = 0;
//    List<List<String>> res;
//    char[][] board;
//
//    public List<List<String>> solveNQueens_1_4(int n) {
//        res = new ArrayList<>();
//        board = new char[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                board[i][j] = '.';
//            }
//        }
//        backtrack(0, n);
//        return res;
//    }
//
//    private void backtrack(int r, int n) {
//        if (r == n) {
//            List<String> copy = new ArrayList<>();
//            for (char[] row : board) {
//                copy.add(new String(row));
//            }
//            res.add(copy);
//            return;
//        }
//        for (int c = 0; c < n; c++) {
//            if ((col & (1 << c)) > 0 || (posDiag & (1 << (r + c))) > 0
//                    || (negDiag & (1 << (r - c + n))) > 0) {
//                continue;
//            }
//            col ^= (1 << c);
//            posDiag ^= (1 << (r + c));
//            negDiag ^= (1 << (r - c + n));
//            board[r][c] = 'Q';
//
//            backtrack(r + 1, n);
//
//            col ^= (1 << c);
//            posDiag ^= (1 << (r + c));
//            negDiag ^= (1 << (r - c + n));
//            board[r][c] = '.';
//        }
//    }


    // V2
    // https://leetcode.com/problems/n-queens/solutions/6463038/easy-backtracking-stepwise-with-explanat-4exf/
    public List<List<String>> solveNQueens_2(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        queen(0, n, board, ans);
        return ans;
    }

    public void queen(int col, int n, char[][] board, List<List<String>> ans) {
        // Base case
        if (col == n) {
            ans.add(construct(board, n));
            return;
        }
        // Iterating over each row in particular col
        for (int row = 0; row < n; row++) {
            // checking the safe spot of the current queen
            if (isSafe(row, col, board, n)) {
                board[row][col] = 'Q';
                // Go to next col inorder to put the next queen
                queen(col + 1, n, board, ans);
                board[row][col] = '.';// Backtracking
            }
        }
    }

    public boolean isSafe(int row, int col, char[][] board, int n) {
        int duprow = row;
        int dupcol = col;
        // Upper diagonal
        while (row >= 0 && col >= 0) {
            if (board[row][col] == 'Q')
                return false;
            row--;
            col--;
        }
        row = duprow;
        col = dupcol;
        // left rows checking
        while (col >= 0) {
            if (board[row][col] == 'Q')
                return false;
            col--;
        }
        row = duprow;
        col = dupcol;
        // Lower diagonals check
        while (row < n && col >= 0) {
            if (board[row][col] == 'Q')
                return false;
            row++;
            col--;
        }
        // All checks are succesfully done and we do not find any queens that can attack
        // the current queen so return true.
        return true;
    }

    public List<String> construct(char[][] board, int n) {
        List<String> resRowise = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String rowElement = new String(board[i]);
            resRowise.add(rowElement);
        }
        return resRowise;
    }

    // V3
    // https://leetcode.com/problems/n-queens/solutions/5988136/beats-100-list-of-common-backtracking-pr-thfa/
    // Helper function to check if placing a queen at position (row,col) is safe
    private boolean isSafePlace(int n, char[][] nQueens, int row, int col) {
        // Check if there's any queen in the same column above current position
        for (int i = 0; i < n; i++) {
            if (nQueens[i][col] == 'Q') {
                return false;
            }
        }

        // Check upper-left diagonal for any queen
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (nQueens[i][j] == 'Q') {
                return false;
            }
        }

        // Check upper-right diagonal for any queen
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (nQueens[i][j] == 'Q') {
                return false;
            }
        }

        // If no conflicts found, position is safe
        return true;
    }

    // Recursive helper function to solve N-Queens problem
    private void solveNQueens_3(int n, List<List<String>> output, char[][] nQueens, int row) {
        // Base case: if we've placed queens in all rows, we found a valid solution
        if (row == n) {
            List<String> solution = new ArrayList<>();
            for (char[] rowArray : nQueens) {
                solution.add(new String(rowArray));
            }
            output.add(solution);
            return;
        }

        // Try placing queen in each column of current row
        for (int col = 0; col < n; col++) {
            // If current position is safe
            if (isSafePlace(n, nQueens, row, col)) {
                // Place queen
                nQueens[row][col] = 'Q';
                // Recursively solve for next row
                solveNQueens_3(n, output, nQueens, row + 1);
                // Backtrack: remove queen for trying next position
                nQueens[row][col] = '.';
            }
        }
    }

    // Main function to solve N-Queens problem
    public List<List<String>> solveNQueens_3(int n) {
        List<List<String>> output = new ArrayList<>(); // Stores all valid solutions
        char[][] nQueens = new char[n][n]; // Initialize empty board

        // Fill the board with dots
        for (int i = 0; i < n; i++) {
            Arrays.fill(nQueens[i], '.');
        }

        solveNQueens_3(n, output, nQueens, 0); // Start solving from row 0
        return output;
    }

}
