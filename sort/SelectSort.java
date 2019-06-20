package cn.ning.algorithm.sort;

import java.util.Arrays;

public class SelectSort {
    public static void main(String[] args) {
        int[] nums = new int[] {3, 5, 3, 0, 8, 11, 6, 12,  6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8,
                9, 7, 3, 1, 2, 57, 9, 200, 130, 7, 4, 0, 2, 6, 10};
        selectSort(nums);
        System.out.print("selectSort: ");
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 选择排序
     * 原理：首先在未排序序列中找到最小（大）元素，放到排序序列的起始位置，之后从剩余未排序序列中找到最小（大）
     *      元素，放到排序序列的末尾。以此类推，直到所有元素均完成排序。
     * 时间复杂度：O(n^2)。
     * 空间复杂度：O(1)。
     * 不稳定。
     * @param nums 待排序序列
     */
    public static void selectSort(int[] nums) {
        for (int i=0; i < nums.length - 1; i++) {
            int min_index = i + 1; // 最小元素索引。
            for (int j = i + 1; j < nums.length; j++) { // 从未排序序列中找到最小元素。
                if (nums[j] < nums[min_index])
                    min_index = j;
            }
            if (nums[min_index] < nums[i]) { // 将最小元素置于排序序列末尾
                int tmp = nums[i];
                nums[i] = nums[min_index];
                nums[min_index] = tmp;
            }
        }
    }
}
