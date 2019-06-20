package cn.ning.algorithm.sort;

import java.util.Arrays;

public class CountingSort {
    public static void main(String[] args) {
        int[] nums = new int[] {3, 5, 3, 0, 8, 11, 6, 12,  6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8,
                9, 7, 3, 1, 2, 57, 9, 200, 130, 7, 4, 0, 2, 6, 10};
        countingSort(nums, 200);
        System.out.print("countingSort: ");
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 计数排序
     * 计数排序是一种稳定的线性时间排序算法。
     * 原理：假设待排序序列的元素个数为n，对与其中一个元素x，当确定了待排序序列中比x元素值小的个数时，就可一确定
     *      出元素x在排序序列中的位置。
     * 时间复杂度：O(n+k)，n为元素个数，k为最大元素的值。
     * 空间复杂度：O(n+k)。
     * 稳定。
     * @param nums 待排序序列
     * @param k 待排序序列中最大元素的值
     * @return 返回有序序列
     */
    public static void countingSort(int[] nums, int k) {
        int[] index = new int [k + 1]; // 默认初始化为0。
        int[] res = new int[nums.length];
        for (int num : nums) { // 统计tmp中每个元素出现的次数。
            index[num] += 1;
        }
        for (int i = 1; i < index.length; i++) { // 计算索引。
            index[i] += index[i-1];
        }
        for (int i = nums.length-1; i >= 0; i--) { // 元素归位。
            res[index[nums[i]] - 1] = nums[i];
            index[nums[i]] -= 1;
        }
        System.arraycopy(res, 0, nums, 0, res.length);
    }
}
