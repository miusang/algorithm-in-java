package cn.ning.algorithm.sort;

import java.util.Arrays;

public class InsertSort {
    public static void main(String[] args) {
        int[] nums = new int[] {3, 5, 3, 0, 8, 11, 6, 12,  6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8,
                9, 7, 3, 1, 2, 57, 9, 200, 130, 7, 4, 0, 2, 6, 10};
        insertSort(nums);
        System.out.print("insertSort: ");
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 插入排序
     * 原理：首先将未排序序列末尾元素置于排序序列的末尾。从未排序序列末尾取一元素，接着从头到尾依次扫描已排序序列
     *      中的元素，如果该元素（未排序）小于扫描元素，则将该元素插入到扫描元素之前，同时停止本次扫描。重复上述
     *      操作，直到所有元素完成排序。
     * 时间复杂度：最优O(n)，未排序序列为有序序列。
     *            最差O(n^2)，未排序序列为逆序序列。
     * 空间复杂度：O(1)。
     * 稳定。
     * @param nums 待排序序列
     */
    public static void insertSort(int[] nums) {
        /*
        // 第一种写法。
        for (int i = 0; i < nums.length - 1; i++) {
            int unindex = nums.length - 2 - i;
            int index = unindex + 1;
            int tmp = nums[unindex];
            while (index < nums.length && tmp > nums[index]) nums[unindex++] = nums[index++];
            nums[unindex] = tmp;
        }*/
        // 第二种写法。
        for (int i = 1; i < nums.length; i++) {
            int unindex = i;
            int index = unindex - 1;
            int tmp = nums[unindex];
            // 为了减少比较此数，可以采用二分查找来确定新元素的位置。
            while (index >= 0  && tmp < nums[index]) nums[unindex--] = nums[index--];
            nums[unindex] = tmp;
        }
    }
}
