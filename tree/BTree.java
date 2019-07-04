package cn.ning.algorithm.tree;

import java.util.ArrayList;

/**
 * B树
 * B树是一种专用的m阶树，可广泛应用于磁盘访问。m阶B树最多可以有m-1个键和m个子树。使用B树的主要原因之一是她能够在
 * 单个节点中存储大量键，并通过保持树的高度相对较小来存储大键值。
 *
 * 1. B树结构：
 *    (1). 每个节点最多包含m个子节点(考虑到查找效率，m >= 3)；
 *    (2). 除根结点和叶节点外，每个节点至少包含ceil(m / 2)个子节点；
 *    (3). 根节点必须至少包含2个节点(特殊情况除外：没有孩子的根节点)；
 *    (4). 所有叶子节点均处于同一层。
 *

 *
 *    (2). 删除：
 *         > 寻找被删除的元素，如果找不到，则删除失败并结束；
 *         > 如果被删除元素位于非叶子节点上，则用其后继或前驱覆盖该元素，然后用后继或前驱元素更新为被删除元素，
 *           此时被删除的元素位于叶子节点上；
 *         > 如果被删除元素所在节点的键的个数大于等于ceil(m / 2) - 1，则直接删除该元素并结束；
 *         > 否则：
 *           a. 如果左侧兄弟所在节点的键的个数大于等于ceil(m / 2) - 1，则将其最大元素推送到其父节点，并
 *              将原来其原来父节点元素下移到删除键的节点；
 *           b. 如果右侧兄弟所在节点的键的个数大于等于ceil(m / 2) - 1，则将其最小元素推送到父节点，并将父节点
 *              元素下移到删除键的节点。
 *         > 如果兄弟节点的键的个数都小于ceil(m / 2) - 1，则将父节点中的键下移与当前节点及它的兄弟节点中的键
 *           合并，形成一个新的节点。
 *         > 如果父节点的键的个数小于ceil(m / 2) - 1，则应将在父节点应用上述过程。
 */
public class BTree<K extends Comparable<K>, V> {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("12");
        list.add("23");
        list.add("34");
        for (String str : list) {
            if (str.compareTo("23") == 0) {
                System.out.println(list.remove(str));
            }
        }
        System.out.println(list);
    }

    private int degree; // B树的阶数。
    private BNode<K, V> root; // 根结点。

    public BTree(int m) {
        degree = m;
    }

    /**
     * 获取B树的阶数。
     */
    public int getDegree() {
        return degree;
    }

    /**
     * 获取根结点。
     */
    public BNode getRoot() {
        return root;
    }

    /**
     * 设置根结点。
     */
    public void setRoot(BNode<K, V> root) {
        this.root = root;
    }

    /**
     * 查找。
     */
    public BNode find(K key) {
        BNode<K, V> cur = root;
        while (cur != null) {
            for (int i = 0; i < cur.getCount(); i++) {
                int compare_res = key.compareTo(cur.getKey(i));
                if (compare_res == 0) {
                    return cur;
                } else if (compare_res < 0) {
                    cur = cur.getChild(i);
                    break;
                } else if (i == cur.getCount() - 1) {
                    cur = cur.getChild(cur.getCount());
                    break;
                }
            }
        }
        return null;
    }

    /**
     * 构建B树。
     */
    public void build() {
    }

    /**
     * 插入结点。
     *  > 遍历B树并找到可插入节点的适当叶节点；
     *  > 如果叶节点包含少于m-1个键，则按递增顺序插入元素；
     *  > 如果叶节点包含m-1个键，则按照以下步骤进行操作：
     *    a. 按元素的递增顺序插入新元素；
     *    b. 将节点拆分为以中值元素为中心拆分为左右两个节点，并将中值元素推送到其父节点；
     *    c. 如果父节点在推送前还包含m-1个键，则按照相同的步骤将其进行拆分。
     */
    public boolean insert(K key, V val) {
        BNode<K, V> cur = root;
        while (cur != null && !cur.isLeaf()) {
            for (int i = 0; i < cur.getCount(); i++) {
                int compare_res = key.compareTo(cur.getKey(i));
                if (compare_res == 0) {
                    return false; // 重复插入。
                } else if (compare_res < 0) {
                    cur = cur.getChild(i);
                    break;
                } else if (i == cur.getCount() - 1) {
                    cur = cur.getChild(cur.getCount());
                    break;
                }
            }
        }
        if (cur == null) { // 说明是空树。
            root = new BNode<>();
            return root.insert(key, val);
        }
        if (cur.getCount() < degree - 1) { // 叶结点的键的个数少于m-1个。
            return cur.insert(key, val);
        }
        for (int i = 0; i < cur.getCount(); i++) { // 防止重复插入。
            if (key.compareTo(cur.getKey(i)) == 0) {
                return false;
            }
        }
        split(cur, key, val); // 叶结点的键的个数等于m-1个。
        return true;
    }

    private void split(BNode<K, V> cur, K key, V val) {
        BNode<K, V> parent = cur.getParent();
        cur.insert(key, val);
        int index = (cur.getCount() - 1) / 2;
        key = cur.getKey(index);
        val = cur.getValue(index);
        cur.delete(index);
        if (parent != null && parent.getCount() >= degree - 1) {
            split(parent, key, val);
            return;
        }
        BNode<K, V> child2 = new BNode<>();
        while (cur.getCount() > index) {
            int last_index = cur.getCount() - 1;
            child2.insert(cur.getKey(last_index), cur.getValue(last_index));
            cur.delete(last_index);
        }
        if (parent == null) {
            root = new BNode<>();
            root.setLeaf(false);
            root.insertChild(cur);
            root.insertChild(child2);
            cur.setParent(root);
            child2.setParent(root);
            return;
        }
        parent.setLeaf(false);
        parent.insert(key, val);
        parent.insertChild(child2);
        child2.setParent(parent);
    }

    /**
     * 删除结点。
     */
    public boolean delete() {
        return false;
    }

    /**
     * 查找前驱结点。
     */
    public BNode findPredecessor() {
        return null;
    }

    /**
     * 查找后继结点。
     */
    public BNode findSuccessor() {
        return null;
    }
}
