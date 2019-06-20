package cn.ning.algorithm.sort;

import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        int[] nums = new int[] {3, 5, 3, 0, 8, 11, 6, 12,  6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8,
                9, 7, 3, 1, 2, 57, 9, 200, 130, 7, 4, 0, 2, 6, 10};
        heapSort(nums);
        System.out.print("heapSort: ");
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 堆排序
     * 原理：首先将未排序序列构建成一个最大堆，由于此时堆顶元素最大，将序列末尾元素与堆顶元素进行交换，此时剩余
     *      未排序序列失去最大堆特性，重复上述操作，直到所有元素完成排序。
     * 时间复杂度：O(nlogn)。
     * 空间复杂度：O(1)。
     * 不稳定。
     * @param nums 未排序序列
     */
    public static void heapSort(int[] nums) {
        buildMaxHeap(nums);
        for (int i = nums.length - 1; i > 0; i--) {
            int tmp = nums[0];
            nums[0] = nums[i];
            nums[i] = tmp;
            maxHeapify(nums, 0, i);
        }
    }

    /**
     * 使一个子树都是二叉最大堆的二叉树保持最大堆的特性。
     * @param nums 完全二叉树序列
     * @param i 二叉树的根结点索引
     */
    private static void maxHeapify(int[] nums, int i, int len) {
        if (i * 2 + 1 > len - 1) return;
        int parent = nums[i];
        int left = nums[i*2 + 1];
        if (i * 2 + 2 > len -1) {
            if (left > parent) {
                nums[i] = left;
                nums[i*2 + 1] = parent;
                maxHeapify(nums, i * 2 + 1, len);
            }
            return;
        }
        int right = nums[i * 2 + 2];
        if (left >= right && left > parent) {
            nums[i] = left;
            nums[i*2 + 1] = parent;
            maxHeapify(nums, i * 2 + 1, len);
        } else if (right >= left && right > parent) {
            nums[i] = right;
            nums[i * 2 + 2] = parent;
            maxHeapify(nums, i*2 + 2, len);
        }
    }
    /**
     * 将一个完全二叉树变成二叉堆（最大堆）。
     * @param nums 完全二叉树序列
     */
    private static void buildMaxHeap(int[] nums) {
        int i = nums.length / 2 - 1;
        while (i >= 0) {
            maxHeapify(nums, i, nums.length);
            i--;
        }
    }
}
