package cn.ning.algorithm.sort;

import java.util.Arrays;

public class RadixSort {
    public static void main(String[] args) {
        int[] nums = new int[] {3, 5, 3, 0, 8, 11, 6, 12,  6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8,
                9, 7, 3, 1, 2, 57, 9, 200, 130, 7, 4, 0, 2, 6, 10};
        radixSort(nums, 3);
        System.out.print("radixSort: ");
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 基数排序
     * 原理：将待排序序列中所有元素（正整数）统一成同样的数字长度，然后按照位数从低位到高位对所有元素进行排序
     *     （调用计数排序），最终整个序列变成一个有序序列。此处按照十进制位进行排序。
     * 时间复杂度：O(len*n)。
     * 空间复杂度：O(len + n)。
     * 稳定。
     * @param nums 待排序序列
     * @param len 序列中最大元素的位数（十进制）
     */
    public static void radixSort(int[] nums, int len) {
        int[] res = new int[nums.length]; // 中间变量。
        int[] index = new int[10];
        int radix = 1;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 10; j++) {
                index[j] = 0; // 清零计数器。
            }
            for (int num : nums) {
                index[num / radix % 10] += 1;
            }
            for (int j = 1; j < 10; j++) {
                index[j] += index[j-1];
            }
            for (int j = nums.length - 1; j >= 0; j--) {
                res[index[nums[j] / radix % 10] - 1] = nums[j];
                index[nums[j] / radix % 10] -= 1;
            }
            System.arraycopy(res, 0, nums, 0, res.length);
            radix *= 10; // 基数上移。
        }
    }
}
