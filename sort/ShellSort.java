package cn.ning.algorithm.sort;

import java.util.Arrays;

public class ShellSort {
    public static void main(String[] args) {
        int[] nums = new int[] {3, 5, 3, 0, 8, 11, 6, 12,  6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8,
                9, 7, 3, 1, 2, 57, 9, 200, 130, 7, 4, 0, 2, 6, 10};
        shellSort(nums);
        System.out.print("shellSort: ");
        System.out.println(Arrays.toString(nums));
    }
    /**
     * 希尔排序
     * 原理：将未排序序列按一定步长分为几个子序列，使未排序序列中的元素可以一次性朝最终的位置前进一大步，从而提升
     *      插入排序的性能。接着步长逐渐减小到1，最后经过直接插入排序得到有序序列。
     * 时间复杂度：由步长决定。最好，O(n(logn)^2)
     * 空间复杂度：O(1)，最坏O(n)。
     * 不稳定。
     */
    public static void shellSort(int[] nums) {
        for (int array_num = nums.length / 2; array_num > 0; array_num /= 2) {
            for (int i = array_num; i < nums.length - array_num + 1; i++) { // 对每个子序列进行插入排序。
                int unindex = i;
                int index = i - array_num;
                int tmp = nums[unindex];
                while (index >= (i % array_num) && tmp < nums[index]) {
                    nums[unindex] = nums[index];
                    unindex -= array_num;
                    index -= array_num;
                }
                nums[unindex] = tmp;
            }
        }
    }
}
