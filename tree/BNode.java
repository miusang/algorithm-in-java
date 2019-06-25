package cn.ning.algorithm.tree;

public class BNode {
    private int[] keys; // 结点包含的键。
    private int count; // 键的个数。
    private BNode[] childs; // 子结点。
    private BNode parent; // 父结点。
    private boolean leaf; // 是否为叶子结点。

    /**
     * 初始化结点。
     * @param m 树的阶数。
     */
    public BNode(int m) {
        keys = new int[m - 1];
        count = 0;
        childs = new BNode[m];
        parent = null;
        leaf = true;
    }

    /**
     * 获取结点中的一个键。
     * @param index 键的索引。
     * @return 返回键。
     */
    public int getKey(int index) {
        return keys[index];
    }

    /**
     * 插入键。
     */
    public void setKey(int key, int index) {
        keys[index] = key;
    }

    /**
     * 获取结点键的个数。
     */
    public int getCount() {
        return count;
    }

    /**
     * 设置结点键的个数。
     */
    public void setCount(int count) {
        if (count < 0 || count > keys.length) {
            return;
        }
        this.count = count;
    }

    /**
     * 获取子结点。
     */
    public BNode getChild(int index) {
        return childs[index];
    }

    /**
     * 设置子结点。
     */
    public void setChild(BNode node, int index) {
        childs[index] = node;
    }

    /**
     * 获取父结点。
     */
    public BNode getParent() {
        return parent;
    }

    /**
     * 设置父结点。
     */
    public void setParent(BNode parent) {
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
}
