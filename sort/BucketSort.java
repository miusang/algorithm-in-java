package cn.ning.algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;

public class BucketSort {
    public static void main(String[] args) {
        int[] nums = new int[] {3, 5, 3, 0, 8, 11, 6, 12,  6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8,
                9, 7, 3, 1, 2, 57, 9, 200, 130, 7, 4, 0, 2, 6, 10};
        bucketSort(nums);
        System.out.print("bucketSort: ");
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 桶排序
     * 原理：将待排序序列均匀分到k个区间或桶内，再在每个桶内分别进行排序，最后将所有的桶内元素合并成一个有序序列。
     * 时间复杂度：最坏O(n^2)，平均O(n+k)，k为桶的数量。
     * 空间复杂度：O(n+k)
     * 稳定性取决于桶内排序的稳定性。
     * @param nums 待排序序列
     */
    public static void bucketSort(int[] nums) {
        int max_num = nums[0]; // 序列最大元素。
        int min_num = nums[0]; // 序列最小元素。
        for (int i : nums) {
            max_num = i > max_num ? i : max_num;
            min_num = i < min_num ? i : min_num;
        }
        int step = 10; // 区间间隔。
        int bucket_num = max_num / step - min_num / step + 1; // 桶的个数。
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(); // 创建桶。
        for (int i = 0; i < bucket_num; i++) {
            buckets.add(new ArrayList<>());
        }
        for (int num : nums) {
            (buckets.get((num - min_num) / step)).add(num);
        }
        ArrayList<Integer> bucket;
        int index = 0;
        for (int i = 0; i < bucket_num; i++) {
            bucket = buckets.get(i);
            singleBucketSort(bucket); // 单个桶内进行排序。
            for (int j : bucket) {
                nums[index++] = j; // 将桶内元素放回有序序列。
            }
        }
    }

    /**
     * 单个桶内进行插入排序
     * @param bucket 桶
     */
    private static void singleBucketSort(ArrayList<Integer> bucket) {
        for (int i = bucket.size() - 2; i >= 0; i--) {
            int index = i + 1;
            int tmp = bucket.get(i);
            while (index < bucket.size() && tmp >bucket.get(index)) {
                bucket.set(index - 1, bucket.get(index++));
            }
            bucket.set(index - 1, tmp);
        }
    }
}
