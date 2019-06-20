package cn.ning.algorithm.dynamic;

/**
 * 装配线调度问题：
 *
 * 1. 问题描述：
 *    某个工厂生产一种产品，有两种装配线选择，每条装配线都有n个装配站。可以单独用，装配线1或2加工生产，也可以使
 *    用装配线i的第j个装配站后，进入另一个装配线的第j+1个装配站继续生产。现想找出通过工厂装配线的最快方法。
 *    |--- 装配线i的第j个装配站表示为S(i,j)，在该站的装配时间是a(i,j)；
 *    |--- 如果从S(i,j)装配站生产后，转移到另一个生产线继续生产所耗费的时间为t(i,j)；
 *    |--- 进入转配线i的花费时间e(i)，完成生产后离开转配线花费时间为x(i)。
 *    |
 *    |  station S(1,1)        -           -           -
 *    |       -> a(1,1) ---> a(1,2) --->  ...  ---> a(1,n)
 *    |     /e(1)      \    /      \    /     \    /      \x(1)
 *    |  起点           t(i,1)      t(i,2)     t(i,n-1)    终点
 *    |     \e(2)      /    \      /    \     /    \     /x(2)
 *    |       -> a(2,1) ---> a(2,2) --->  ...  ---> a(2,n)
 *    |  station S(2,1)        -           -           -
 *
 * 2. 求解方法：
 *    (1). 令f*表示通过生产线路中的最快时间；
 *    (2). 令f(i,j)表示从入口到装配站S(i,j)的最快时间；(i=1,2 ; j=1,2,...,n)
 *         if j == 1:
 *         f(1,1) = e(1) + a(1,1)
 *         f(2,1) = e(2) + a(2,1)
 *         if j >= 2:
 *         f(1,j) = min(f(1,j-1) + a(1,j), f(2,j-1) + t(2,j-1) + a(1,j))
 *         f(2,j) = min(f(1,j-1) + t(1,j-1) + a(2,j), f(2,j-1) + a(2,j))
 *    (3). 得到递归解
 *         f* = min(f(1,n) + x(1), f(2,n) + x(2))
 *
 */
public class ProductionLine {
    public static void main(String[] args) {
        int station_num = 10; // 装配站的个数。
        int[] e = new int[2]; // 进入装配站时间。
        int[] x = new int[2]; // 离开装配线时间。
        int[][] a = new int[2][station_num]; // 装配站的装配时间。
        int[][] t = new int[2][station_num - 1]; // 转运时间。

        int[][] f = new int[2][station_num]; // 中间存储量。
        int[][] way = new int[2][station_num]; // 装配路径。
        f[0][0] = e[0] + a[0][0];
        f[1][0] = e[1] + a[1][0];
        way[0][0] = 0;
        way[1][0] = 1;
        for(int j = 1; j < station_num - 1; j++) {
            int cost11 = f[0][j - 1] + a[0][j];
            int cost21 = f[1][j - 1] + a[1][j] + t[1][j - 1];
            if (cost11 < cost21) {
                f[0][j] = cost11;
                way[0][j - 1] = 0;
            } else {
                f[0][j] = cost21;
                way[0][j - 1] = 1;
            }
            int cost22 = f[1][j - 1] + a[1][j];
            int cost12 = f[0][j - 1] + a[1][j] + t[0][j - 1];
            if (cost22 < cost12) {
                f[1][j] = cost22;
                way[1][j - 1] = 1;
            } else {
                f[1][j] = cost12;
                way[1][j - 1] = 0;
            }
        }
        int cost1 = f[0][station_num - 2] + x[0];
        int cost2 = f[1][station_num - 2] + x[1];
        if (cost1 < cost2) {
            f[0][station_num - 1] = cost1;
            f[1][station_num - 1] = cost1;
            way[0][station_num - 1] = 0;
            way[1][station_num - 1] = 0;
        } else {
            f[0][station_num - 1] = cost2;
            f[1][station_num - 1] = cost2;
            way[0][station_num - 1] = 1;
            way[1][station_num - 1] = 1;
        }
    }
}
