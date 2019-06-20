package cn.ning.algorithm.sort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] nums = new int[] {3, 5, 3, 0, 8, 11, 6, 12,  6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8,
                9, 7, 3, 1, 2, 57, 9, 200, 130, 7, 4, 0, 2, 6, 10};
        quickSort(nums, 0, nums.length - 1);
        System.out.print("quickSort: ");
        System.out.println(Arrays.toString(nums));
    }
    /**
     * 快速排序
     * 原理：选取待排序序列中的一个元素作为基准，将序列中比基准元素小的元素放到基准元素前面，比基准元素大的元素放
     *      到基准元素后面，此时基准元素将整个序列分成两个未排序子序列，按照上述方法递归地处理每个子序列，直到子
     *      序列的元素个数为1。处理完所有子序列后，整个序列有序。
     * 时间复杂度：平均、最优（每次基准总是划分在序列中间）O(nlogn)，
     *           最坏（未排序序列为正序或倒序）O(n^2)。
     * 空间复杂度：平均、最优O(logn)，最坏O(n)，取决于递归的深度。
     * 不稳定。
     * @param nums 待排序序列。
     * @param start 待排序序列起始索引。
     * @param end 待排序序列末尾索引。
     */
    public static void quickSort(int[] nums, int start, int end) {
        if (start >= end) return;
        int i = start - 1; // 比基点pivot大元素的索引。
        for (int j = start; j < end; j++) {
            if (nums[j] <= nums[end]) {
                i++;
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
        }
        int pivot = i + 1; // 将序列分割的基准点。
        int tmp = nums[pivot];
        nums[pivot] = nums[end];
        nums[end] = tmp;
        quickSort(nums, start, pivot - 1);
        quickSort(nums, pivot + 1, end);
    }
}
