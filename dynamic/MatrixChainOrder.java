package cn.ning.algorithm.dynamic;

/**
 * 矩阵链乘法
 *
 * 1. 问题描述
 *        给定n个矩阵构成的一个链<A1, A2, A3, ... , An>，其中矩阵Ai的维数为pi-1pi，对于乘积A1*A2*A3...*An
 *    以一种最小化标量乘法次数的方式进行加括号。
 *        例如：矩阵链<A1, A2, A3>，三个矩阵规模分别为10x100，100x5，5x50。按照以下两种方式进行计算，其标量
 *    运算次数是不一样的：方式一，(A1A2)A3，需要10*100*5 + 10*5*50 = 7500次运算；方式二，A1(A2A3)，
 *    需要100*5*50 + 10*100*50 = 75000次运算。可以看到两种运算方式之间的效率差别非常大。
 *
 * 2. 问题求解
 *    (1). 最优加全部括号的结构
 *         动态规划的第一步是寻找一个最优的子结构。假设现在要计算AiAi+1...Aj的值，那么肯定会存在某个k值
 *         (i <= k < j)，将Ai...j分成两部分，使得Ai...j的计算量最小。从而将原问题分解成Ai...k和Ak+1...j两个
 *         子问题。
 *    (2). 一个递归解
 *         设m[i, j]为计算矩阵Ai...j所需的标量乘法运算次数的最小值，则A1...n的解为m[1, n]。
 *         m[i, j] = 0, if i == j;
 *         m[i, j] = min{m[i, k] + m[k+1, j] + pi-1pkpj}, i <= k < j.
 *    (3). 求解
 *         采用自底向上的求解方法，
 *         用m[n][n]二维矩阵来保存矩阵链A1...n的最优计算代价；
 *         用s[n][n]二维矩阵来保存矩阵链A1...n的最优划分位置。
 *
 */
public class MatrixChainOrder {
    public static void main(String[] args) {
        int[] matrix_dims = new int[]{30, 35, 15, 5, 10, 20, 25}; // 共6个矩阵。
        int matrix_count = matrix_dims.length - 1;
        int[][] m = new int[matrix_count][matrix_count];
        int[][] s = new int[matrix_count][matrix_count];
        for (int i = 0; i < matrix_count; i++) {
            m[i][i] = 0;
        }
        for (int chain_count = 2; chain_count <= matrix_count; chain_count++) { // 矩阵链中矩阵个数。
            for (int i = 0; i <= matrix_count - chain_count; i++) {
                int j = i + chain_count - 1;
                m[i][j] = -1;
                for (int k = i; k < j; k++) { // 遍历所有可能的划分点k。
                    int cost = m[i][k] + m[k + 1][j] + matrix_dims[i] * matrix_dims[k + 1]
                            * matrix_dims[j + 1]; // 计算此处划分的计算代价。
                    if (cost < m[i][j] || m[i][j] == -1) {
                        m[i][j] = cost;
                        s[i][j] = k;
                    }
                }
            }
        }
        printOptimalParens(s, 0, matrix_count - 1);
    }

    /**
     * 打印最优划分。
     */
    private static void printOptimalParens(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("A" + i);
        } else {
            System.out.print("(");
            printOptimalParens(s, i, s[i][j]);
            printOptimalParens(s, s[i][j] + 1, j);
            System.out.print(")");
        }
    }
}
