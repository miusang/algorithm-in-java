package cn.ning.algorithm.dynamic;

/**
 * 最长公共子序列
 * 注意：最长公共子序列(Longest common sequence)和最长公共子串(Longest common substring)不是一回事儿。
 * 子序列：将给定序列中零个或多个元素去掉之后得到的结果；
 * 子串：给定串中任意连续的字符组成的子序列成为该串的子串。
 *
 * 1. 问题描述
 *    给定两个序列，如{1, 3, 4, 5, 5}和{2, 4, 5, 5, 7, 6}，求取它们的最长公共子序列：{4, 5, 5}。
 *
 * 2. 问题求解
 *    (1). 最优子结构：
 *         假设有两个序列X={x1, x2, ..., xm}和Y={y1, y2, ..., yn}。Z={z1, z2, ..., zk}为X和Y的LCS。
 *         a. if xm == yn
 *            则该字符必是X和Y的任一最长公共子序列Z的最后一个字符，即xm == yn == zk。现在只需要找
 *            LCS(Xm-1, Yn-1)，则LCS(Xm-1, Yn-1)就是原问题的一个子问题。
 *         b. if xm != yn
 *            则产生产生了两个子问题：LCS(Xm-1, Yn)和LCS(Xm, Yn-1)。
 *            LCS(Xm-1, Yn)表示在{x1, x2, ..., xm-1}和{y1, y2, ..., yn}中找出其最长公共子序列。
 *            LCS(Xm, Yn-1)表示在{x1, x2, ..., xm}和{y1, y2, ..., yn-1}中找出其最长公共子序列。
 *            求解上面的两个子问题，得到两个最长公共子序列最长的那个就是要求解的，即
 *            LCS(X, Y) = max{LCS(Xm-1, Yn), LCS(Xm, Yn-1)}
 *
 *    (2). 递归式：
 *         设C[i, j]表示Xi和Yi的最长公共子序列的长度。则，
 *         C[i，j] = 0,                                   if i == 0 || j == 0
 *         C[i, j] = C[i - 1, j - 1] + 1,                 if i,j > 0 && xi == yj
 *         C[i, j] = max{C[i, j -1], C[i - 1, j]},        if i,j > 0 && xi != yj
 *         记B[i, j]表示最长公共子序列的搜索方向，
 *         B[i, j] = 1,                                   if xi == yj
 *         B[i, j] = 2,                                   if choose C[i, j - 1]
 *         B[i, j] = 3,                                   if choose C[i - 1, j]
 *
 */
public class LongestCommonSequence {
    public static void main(String[] args) {
        char[] X = new char[] {'b', 'd', 'c', 'a', 'b', 'a'};
        char[] Y = new char[] {'a', 'b', 'c', 'b', 'd', 'a', 'b'};

        int[][] C = new int[X.length + 1][Y.length + 1];
        int[][] B = new int[X.length + 1][Y.length + 1];

        for (int i = 1; i <= X.length; i++) {
            for (int j = 1; j <= Y.length; j++) {
                if (X[i - 1] == Y[j - 1]) {
                    C[i][j] = C[i - 1][j - 1] + 1;
                    B[i][j] = 1;
                } else if (C[i][j - 1] > C[i - 1][j]) {
                    C[i][j] = C[i][j - 1];
                    B[i][j] = 2;
                } else {
                    C[i][j] = C[i - 1][j];
                    B[i][j] = 3;
                }
            }
        }
        printLCS(X, B, X.length, Y.length);
        System.out.println(C[X.length][Y.length]);
    }

    /**
     * 打印最长公共子序列。
     */
    private static void printLCS(char[] X, int[][] B, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (B[i][j] == 1) {
            printLCS(X, B, i - 1, j -1);
            System.out.print(X[i - 1] +" ");
        } else if (B[i][j] == 2) {
            printLCS(X, B, i, j - 1);
        } else {
            printLCS(X, B, i - 1, j);
        }
    }
}
