package cn.ning.algorithm.tree;

import java.util.ArrayList;

public class BNode<K extends Comparable<K>, V> {

    private ArrayList<Entry> entries; // 结点包含的键值对。
    private ArrayList<BNode<K, V>> childs;
    private BNode<K, V> parent; // 父结点。
    private boolean leaf; // 是否为叶子结点。



    /**
     * 初始化结点。
     */
    public BNode() {
        entries = new ArrayList<>();
        childs = new ArrayList<>();
        parent = null;
        leaf = true; // 初始化为叶结点。
    }

    /**
     * 获取结点中的一个键。
     * @param index 键的索引。
     * @return 返回键。
     */
    public K getKey(int index) {
        return entries.get(index).key;
    }

    /**
     * 获取结点中的一个值。
     * @param index 索引。
     * @return 返回值。
     */
    public V getValue(int index) {
        return entries.get(index).val;
    }

    /**
     * 顺序插入键值对。
     */
    public boolean insert(K key, V val) {
        for (int i = 0; i < entries.size(); i++) {
            int compare_res = key.compareTo(entries.get(i).key);
            if (compare_res == 0) {
                return false;
            }
            if (compare_res < 0) {
                entries.add(i, new Entry(key, val)); // 将键值对插入结点中。
                return true;
            }
        }
        entries.add(entries.size(), new Entry(key, val));
        return true;
    }

    /**
     * 删除键值对。
     */
    public boolean delete(K key) {
        for (Entry entry : entries) {
            if (key.compareTo(entry.key) == 0) {
                return entries.remove(entry);
            }
        }
        return false;
    }

    /**
     * 删除键值对。
     */
    public boolean delete(int index) {
        return entries.remove(index) != null;
    }

    /**
     * 获取结点键的个数。
     */
    public int getCount() {
        return entries.size();
    }

    /**
     * 获取子结点。
     */
    public BNode<K, V> getChild(int index) {
        return childs.get(index);
    }

    /**
     * 顺序插入子结点。
     */
    public void insertChild(BNode<K, V> node) {
        K key = node.getKey(0);
        for (int i = 0; i < entries.size(); i++) {
            if (key.compareTo(entries.get(i).key) < 0) {
                childs.add(i, node);
                return;
            }
        }
        childs.add(node);
    }

    /**
     * 删除子结点。
     */
    public boolean deleteChild(BNode<K, V> node) {
        return childs.remove(node);
    }

    /**
     * 删除子结点。
     */
    public BNode<K, V> deleteChild(int index) {
        return childs.remove(index);
    }

    /**
     * 获取父结点。
     */
    public BNode<K, V> getParent() {
        return parent;
    }

    /**
     * 设置父结点。
     */
    public void setParent(BNode<K, V> parent) {
        this.parent = parent;
    }

    /**
     * 是否为叶结点。
     */
    public boolean isLeaf() {
        return leaf;
    }

    /**
     * 设置结点属性。
     */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    private class Entry {
        protected K key;
        protected V val;
        public Entry() {
        }

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
}
