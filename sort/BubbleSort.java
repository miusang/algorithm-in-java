package cn.ning.algorithm.sort;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int[] nums = new int[] {3, 5, 3, 0, 8, 11, 6, 12,  6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8,
                9, 7, 3, 1, 2, 57, 9, 200, 130, 7, 4, 0, 2, 6, 10};
        bubbleSort(nums);
        System.out.print("bubbleSort: ");
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 冒泡排序
     * 原理：从未排序序列起始位置开始，相邻两元素进行比较，将较大（小）元素沉底，直到将未排序序列所有元素比较完，
     *      此时，将最大（小）元素放到排序序列的开头，以此类推，直到所有元素均完成排序。
     * 时间复杂度：最优O(n)，未排序序列本来就是有序的；
     *           最差O(n^2)。
     * 空间复杂度：O(1)。
     * 稳定。
     * @param nums 待排序序列
     */
    public static void bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            boolean flag = true; // 有序标志位。
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                    flag = false;
                }
            }
            if (flag) { // 如果未排序序列有序，则结束排序。
                return;
            }
        }
    }
}
