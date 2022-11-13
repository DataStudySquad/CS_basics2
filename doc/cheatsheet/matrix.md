# Matrix
> N dimension (2-D in most cases) linear data structure

## 0) Concept  

### 0-1) Types

- Types
    - [greedy.md](https://github.com/yennanliu/CS_basics/blob/master/doc/cheatsheet/greedy.md)
    - [array.md](https://github.com/yennanliu/CS_basics/blob/master/doc/cheatsheet/array.md)

- Algorithm
    - [fucking algorithm : 二维数组的花式遍历技巧](https://labuladong.github.io/algo/2/20/26/)
        - LC 151
        - LC 48
        - LC 54
        - LC 59

- Data structure
    - array

### 0-2) Pattern

## 1) General form

### 1-1) Basic OP

#### 1-1-1) Matrix

- Properties
    - diagonal, anti-diagonal
        - https://leetcode.com/problems/n-queens-ii/solutions/1146740/n-queens-ii/
        - diagonal
            - For each square on a given diagonal, the difference between the row and column indexes (row - col) will be constant. Think about the diagonal that starts from (0, 0) - the i th square has coordinates (i, i), so the difference is always 0.
            - <img src ="https://github.com/yennanliu/CS_basics/blob/master/doc/pic/diagonal.png" ></p>
        - anti-diagonal
            - For each square on a given anti-diagonal, the sum of the row and column indexes (row + col) will be constant. If you were to start at the highest square in an anti-diagonal and move downwards, the row index increments by 1 (row + 1), and the column index decrements by 1 (col - 1).
            - <img src ="https://github.com/yennanliu/CS_basics/blob/master/doc/pic/anti-diagonal.png" ></p>

```python
# 1) init matrix 
# LC 73
### NOTE : 
# -> for matrix[i][j]:
#    -> y is FIRST element  (i)
#    -> x is SECOND element (j)
```

```python
#--------------------------------
# transpose (i,j) -> (j, i)
#--------------------------------
# LC  048 Rotate Image
matrix = [
    [1,2,4],
    [4,5,6],
    [7,8,9]
]

l = len(matrix)
w = len(matrix[0])
for i in range(l):
    """
    NOTE !!!
        -> j start from i+1 to len(matrix[0])
    """
    for j in range(i+1, w): # NOTE THIS !!!!
        matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]

print (matrix)

# output
# i = 0 j = 1
# i = 0 j = 2
# i = 1 j = 2
# In [36]: matrix
# Out[36]: [[1, 4, 7], [2, 5, 8], [4, 6, 9]]


matrix = [
    [1,2,3,4],
    [5,6,7,8],
    [9,10,11,12],
    [13,14,15,16]
]

# output
# i = 0 j = 1
# i = 0 j = 2
# i = 0 j = 3
# i = 1 j = 2
# i = 1 j = 3
# i = 2 j = 3
# [[1, 5, 9, 13], [2, 6, 10, 14], [3, 7, 11, 15], [4, 8, 12, 16]]
```

```python
#----------------------
# Rotate matrix
#----------------------
# LC  048 Rotate Image
class Solution:
    def rotate(self, matrix):
        ### NOTE : TRANSPOSE matrix
        n = len(matrix)
        # transpose
        for i in range(n):
             for j in range(i+1, n):
                matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]
        # reverse
        for i in range(n):
            matrix[i].reverse()
        return matrix
```

```python
#----------------------
# 2) get avg value of matrix
#----------------------
# LC 661
# some code
# M : matrix
row, col = len(M), len(M[0])
res = [[0]*col for i in range(row)]
# get tmp sum
dirs = [[0,0],[0,1],[0,-1],[1,0],[-1,0],[1,1],[-1,-1],[-1,1],[1,-1]]
temp = [M[i+m][j+n] for m,n in dirs if 0<=i+m<row and 0<=j+n<col]
# get avg value
res[i][j] = sum(temp)//len(temp)
# some code
```

#### 1-1-11) Matrix relative problems
```python
# Transpose matrix
# LC  048 Rotate Image
# V0
# IDEA : TRANSPOSE -> REVERSE 
class Solution:
    def rotate(self, matrix):
        ### NOTE : TRANSPOSE matrix
        n = len(matrix)
        # transpose
        for i in range(n):
             for j in range(i+1, n):
                matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]
        # reverse
        for i in range(n):
            matrix[i].reverse()
        return matrix

# Spiral matrix
# LC 054 Spiral Matrix
# V0
# IDEA : 4 cases : right, down, left, up + boundary condition
# PATTERN:
# while condition:
#     # right
#     for ..
#     # down
#     for ...
#     # left
#     for ...
#     # up
#     for ...
class Solution(object):
    def spiralOrder(self, matrix):
        # edge case
        if not matrix:
            return
        res=[]
        """
        NOTE this : we define 4 boundaries
        """
        left = 0
        right = len(matrix[0])-1
        top = 0
        bottom = len(matrix)-1
        """
        NOTE : this condition
        """
        while left <= right and top <= bottom:
            # NOTE !!! we use for loop INSTEAD of while
            # right
            for j in range(left, right+1):  # note : range(left, right+1)
                res.append(matrix[top][j])
            # down
            for i in range(top+1, bottom):  # note : range(top+1, bottom)
                res.append(matrix[i][right])
            # left
            for j in range(left, right+1)[::-1]: # note : range(left, right+1)[::-1]
                """
                NOTE : this condition
                """
                if top < bottom:
                    res.append(matrix[bottom][j])
            # up
            for i in range(top+1, bottom)[::-1]: # note : range(top+1, bottom)[::-1]
                """
                NOTE : this condition
                """
                if left < right:
                    res.append(matrix[i][left])

            # NOTE !!! we do boundary update AFTER each "right-down-left-up" iteration
            left += 1
            right -= 1
            top += 1
            bottom -= 1
            
        return res

# V0'
class Solution(object):
    # @param matrix, a list of lists of integers
    # @return a list of integers
    def spiralOrder(self, matrix):
        result = []
        if matrix == []:
            return result

        left, right, top, bottom = 0, len(matrix[0]) - 1, 0, len(matrix) - 1

        while left <= right and top <= bottom:
            # right
            for j in range(left, right + 1):
                result.append(matrix[top][j])
            # down
            for i in range(top + 1, bottom):
                result.append(matrix[i][right])
            # left
            for j in (range(left, right + 1))[::-1]:
                if top < bottom: # notice
                    result.append(matrix[bottom][j])
            # up
            for i in range(top + 1, bottom)[::-1]:
                if left < right: # notice
                    result.append(matrix[i][left])
            left, right, top, bottom = left + 1, right - 1, top + 1, bottom - 1

        return result
```

```python
# -------------------------
# get diagonal sum of matrix
# -------------------------
# LC 348. Design Tic-Tac-Toe
# ...
n = len(self.grid)
sum_of_row = sum([self.grid[row][c] == mark for c in range(n)])
sum_of_col = sum([self.grid[r][col]== mark for r in range(n)])
sum_of_left_d = sum([self.grid[i][i] == mark for i in range(n)])
sum_of_right_d = sum([self.grid[i][n-1-i] == mark for i in range(n)])
# ....
```

```python
# LC 311. Sparse Matrix Multiplication
# V0 
# TODO : OPTIMIZE THE PROCESS DUE TO THE SPARSE-MATRIX CONDITION 
class Solution(object):
    def multiply(self, A, B):
        """
        :type A: List[List[int]]
        :type B: List[List[int]]
        :rtype: List[List[int]]
        """
        m, n, l = len(A), len(A[0]), len(B[0])
        res = [[0 for _ in range(l)] for _ in range(m)]
        for i in range(m):
            for k in range(n):
                if A[i][k]:
                    for j in range(l):
                        res[i][j] += A[i][k] * B[k][j]
        return res
```

```python
# LC 498. Diagonal Traverse
# V0 
# IDEA : while loop + boundary conditions
### NOTE : the "directions" trick
class Solution(object):
    def findDiagonalOrder(self, matrix):
        if not matrix or not matrix[0]: return []
        ### NOTE this trick
        directions = [(-1, 1), (1, -1)]
        count = 0
        res = []
        i, j = 0, 0
        M, N = len(matrix), len(matrix[0])
        while len(res) < M * N:
            if 0 <= i < M and 0 <= j < N:
                res.append(matrix[i][j])
                direct = directions[count % 2]
                i, j = i + direct[0], j + direct[1]
                continue
            elif i < 0 and 0 <= j < N:
                i += 1
            elif 0 <= i < M and j < 0:
                j += 1
            elif i < M and j >= N:
                i += 2
                j -= 1
            elif i >= M and j < N:
                j += 2
                i -= 1
            count += 1
        return res
```
 
## 2) LC Example


### 2-1) Set Matrix Zeroes
```python
# LC 73. Set Matrix Zeroes
# V0
class Solution(object):
    def setZeroes(self, matrix):   

        if not matrix:
            return matrix

        def help(matrix, xy):
            ### NOTE : 
            #          -> for cases matrix[i][j]:
            #            -> y is FIRST element  (i)
            #            -> x is SECOND element (j)
            x = xy[1]
            y = xy[0]
            matrix[y] = [0] * len(matrix[0])
            for j in range(len(matrix)):
                matrix[j][x] = 0
            return matrix

        _list = []
        for i in range(len(matrix)):
            for j in range(len(matrix[0])):
                if matrix[i][j] == 0:
                    _list.append([i,j])

        for xy in _list:
            matrix = help(matrix, xy)
        return matrix
```

### 2-5) Image Smoother
```python
# LC 661 Image Smoother
class Solution:
    def imageSmoother(self, M):
        row, col = len(M), len(M[0])
        res = [[0]*col for i in range(row)]
        dirs = [[0,0],[0,1],[0,-1],[1,0],[-1,0],[1,1],[-1,-1],[-1,1],[1,-1]]
        # note we need to for looping row, col
        for i in range(row):
            for j in range(col):
                # and to below op for each i, j (row, col)
                temp = [M[i+m][j+n] for m,n in dirs if 0<=i+m<row and 0<=j+n<col]
                ### NOTE : this trick for getting avg
                res[i][j] = sum(temp)//len(temp)
        return res
```

### 2-6) Search a 2D Matrix
```python
# LC 74 Search a 2D Matrix
# LC 240. Search a 2D Matrix II
"""
NOTE !!!  boundary condition
"""
# V0'
# IDEA : DFS
class Solution(object):
    def searchMatrix(self, matrix, target):
        def dfs(matrix, target, x, y):
            if matrix[y][x] == target:
                res.append(True)
            matrix[y][x] = "#"
            moves = [[0,1],[0,-1],[1,0],[-1,0]]
            for move in moves:
                _x = x + move[1]
                _y = y + move[0]
                #print ("_x = " + str(_x) + " _y = " + str(_y))
                if 0 <= _x < w and 0 <= _y < l:
                    if matrix[_y][_x] != "#":
                        dfs(matrix, target, _x, _y)

        if not matrix:
            return False
        l = len(matrix)
        w = len(matrix[0])
        res = []
        dfs(matrix, target, 0, 0)
        return True in res
```