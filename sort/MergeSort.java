package cn.ning.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序
 * 原理：将未排序序列分为两个未排序子序列，分别进行排序，之后将两个有序子序列进行合并。未排序子序列可以一直分解下
 *      去，直到子序列中元素个数为1。简而言之，分而治之。
 * 时间复杂度O(nlogn)以2为底。
 * 空间复杂度O(n)。
 * 稳定。
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] nums = new int[] {3, 5, 3, 0, 8, 11, 6, 12,  6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8,
                9, 7, 3, 1, 2, 57, 9, 200, 130, 7, 4, 0, 2, 6, 10};
        recursiveMergeSort(nums);
        System.out.print("mergeSort: ");
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 递归法
     * @param nums 待排序序列。
     */
    public static void recursiveMergeSort(int[] nums) {
        int[] tmp = new int[nums.length];
        segment(nums, tmp, 0, nums.length-1);
    }

    /**
     * 合并两个有序子序列
     * @param nums 待排序序列。
     * @param tmp 辅助空间。
     * @param start 第一个有序子序列起始位置。
     * @param mid 第一个有序子序列结束位置，第二个有序子序列起始位置mid+1。
     * @param end 第二个有序子序列结束位置。
     */
    private static void merge(int[] nums, int [] tmp, int start, int mid, int end) {
        int index1 = start;
        int index2 = mid + 1;
        int index = 0;
        /* 注意此处index1和index2的顺序，涉及到排序算法的稳定性。 */
        while (index1 <= mid && index2 <= end) tmp[index++] = (nums[index2] < nums[index1]
                ? nums[index2++] : nums[index1++]);
        while (index1 <= mid) tmp[index++] = nums[index1++];
        while (index2 <= end) tmp[index++] = nums[index2++];
        /* ---------------------------------------------------- */
        index = 0;
        while (index < end - start + 1) nums[index+start] = tmp[index++];
    }

    /**
     * 将未排序序列进行分割。
     * @param nums 待排序序列。
     * @param tmp 合并有序子序列所需辅助空间。
     * @param start 未排序（子）序列的起始位置。
     * @param end 未排序（子）序列的结束位置。
     */
    private static void segment(int[] nums, int[] tmp, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        segment(nums, tmp, start, mid);
        segment(nums, tmp, mid + 1, end);
        merge(nums, tmp, start, mid, end);
    }

    /**
     * 非递归归并排序。
     * @param nums 待排序序列
     */
    public static void mergeSort(int[] nums) {
        int[] tmp = new int[nums.length];
        int step = 2;
        while (step < nums.length * 2) {
            for (int start = 0; start < nums.length; start += step) {
                int index = 0;
                int index1 = start;
                int mid = start + step / 2 - 1;
                int index2 = mid + 1;
                if (index2 > nums.length -1) break;
                int end = start + step - 1;
                end = end > nums.length -1 ? nums.length -1 : end;
                while (index1 <= mid && index2 <= end) tmp[index++] = (nums[index2] < nums[index1]
                        ? nums[index2++] : nums[index1++]);
                while (index1 <= mid) tmp[index++] = nums[index1++];
                while (index2 <= end) tmp[index++] = nums[index2++];
                index = 0;
                while (index < end - start + 1) nums[index+start] = tmp[index++];
            }
            step = step * 2;
        }
    }
}
