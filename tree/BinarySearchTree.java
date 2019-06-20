package cn.ning.algorithm.tree;


/**
 * 二叉查找树。
 * 通常采用二叉链表作为二叉查找树的存储结构。
 *
 * 定义：
 *     1. 若任意结点的左子树不为空，则左子树上所有结点的值均小于它的根结点的值；
 *     2. 若任意结点的右子树不为空，则右子树上所有结点的值均大于它的根结点的值；
 *     3. 任意结点的左、右子树也是二叉查找树；
 *     4. 没有键值相等的结点。
 *
 * 性质：
 *     对二叉查找树进行中序遍历，即可得到有序序列。
 *
 * 优势：
 *     查找、插入的时间复杂度较低。
 *
 */
public class BinarySearchTree<T extends Comparable<T>> extends Tree<T> {

    /**
     * 构建二叉查找树。
     * @param order 被构建序列。
     */
    public void build(T[] order) {
        if (order.length <= 0) {
            return;
        }
        for (T item : order) {
            this.insert(item);
        }
    }

    /**
     * 查找。
     * @param val 待查找元素的键。
     * @return 如果找到key，则返回该结点；否则返回null。
     * 在此处元素的键值设置为同一个。
     */
    public Node<T> find(T val) {
        Node<T> cur = this.getRoot();
        while (cur != null) {
            int compare_result = cur.val.compareTo(val);
            if (compare_result == 0) {
                return cur;
            }
            if (compare_result > 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return null;
    }

    /**
     * 二叉查找树值最大结点。
     * @param root 根结点
     */
    public Node<T> findMax(Node<T> root) {
        Node<T> cur = root;
        if (cur == null) {
            return null;
        }
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur;
    }

    /**
     * 二叉查找树值最小结点。
     * @param root 根结点
     */
    public Node<T> findMin(Node<T> root) {
        Node<T> cur = root;
        if (cur == null) {
            return null;
        }
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    /**
     * 插入结点。
     * @param val 被插入结点的值。
     * @return 若果不存在重复元素，则插入该元素且返回true；否则，返回false。
     */
    public boolean insert(T val) {
        Node<T> cur = this.getRoot();
        if (cur == null) {
            this.setRoot(new Node<>(val));
            return true;
        }
        int compare_result = 0;
        Node<T> parent = null;
        while (cur != null) {
            compare_result = cur.val.compareTo(val);
            if (compare_result == 0) { // 存在重复元素。
                return false;
            }
            parent = cur;
            if (compare_result < 0) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        if (compare_result > 0) {
            parent.left = new Node<>(val);
            parent.left.parent = parent;
        } else {
            parent.right = new Node<>(val);
            parent.right.parent = parent;
        }
        return true;
    }

    /**
     * 删除结点。
     * @param val 被删除结点的值
     * @return 如果二叉树中不存在值为val的结点，则返回false；否则，删除该结点，并返回true。
     */
    public boolean delete(T val) {
        Node<T> delete_node = this.find(val);
        if (delete_node == null) {
            return false;
        }
        if (delete_node.left == null && delete_node.right == null) {
            if (delete_node.parent == null) {
                this.setRoot(null);
                return true;
            }
            if (delete_node == delete_node.parent.left) {
                delete_node.parent.left = null;
            } else {
                delete_node.parent.right = null;
            }
            return true;
        }
        if (delete_node.left != null && delete_node.right != null) { // 用前驱或后继结点替换被删除结点。
            Node<T> replace_node = this.findPredecessor(delete_node); // 前驱结点没有右子树。
            delete_node.val = replace_node.val;
            if (replace_node == replace_node.parent.left) {
                replace_node.parent.left = replace_node.left;
            } else {
                replace_node.parent.right = replace_node.left;
            }
            if (replace_node.left != null) {
                replace_node.left.parent = replace_node.parent;
            }
//            Node<T> replace_node = this.findSuccessor(delete_node); // 后继结点没有左子树。
//            delete_node.val = replace_node.val;
//            if (replace_node == replace_node.parent.left) {
//                replace_node.parent.left = replace_node.right;
//            } else {
//                replace_node.parent.right = replace_node.right;
//            }
//            if (replace_node.right != null) {
//                replace_node.right.parent = replace_node.parent;
//            }
            return true;
        }
        if (delete_node.left != null) {
            if (delete_node.parent == null) {
                this.setRoot(delete_node.left);
                return true;
            }
            if (delete_node.parent.left == delete_node) {
                delete_node.parent.left = delete_node.left;
            } else {
                delete_node.parent.right = delete_node.left;
            }
            delete_node.left.parent = delete_node.parent;
            return true;
        }
        if (delete_node.parent == null) {
            this.setRoot(delete_node.right);
            return true;
        }
        if (delete_node.parent.left == delete_node) {
            delete_node.parent.left = delete_node.right;
        } else {
            delete_node.parent.right = delete_node.right;
        }
        delete_node.right.parent = delete_node.parent;
        return true;
    }

    /**
     * 寻找二叉搜索树中node结点的前驱，查找规则：
     *  1. 若待查找结点node，有左子树，则node结点的前驱是其左子树中值最大元素对应的结点。
     *  2. 若待查找结点node，没有左子树，则需要判读该结点与其父结点之间的关系：
     *     (1)若node结点是其父结点的右儿子，那么父结点即为该结点的前驱。
     *     (2)若node结点是其父结点的左儿子，则需沿着其父结点一直向树的顶端寻找，直到找到一个结点P，
     *       P结点是其父结点Q的右儿子，那么结点Q即为该结点的前驱。
     */
    public Node<T> findPredecessor(Node<T> node) {
        if (node == null) {
            return null;
        }
        if (node.left != null) {
            return this.findMax(node.left);
        }
        while (node.parent != null) {
            if (node == node.parent.right) {
                return node.parent;
            }
            node = node.parent;
        }
        return null;
    }

    /**
     * 寻找二叉搜索树中node结点的后继，查找规则：
     *  1. 若待查找结点有右子树，则node结点的后继是其右子树中值最小元素对应的结点。
     *  2. 若待查找结点没有右子树，则需判断node结点与其父结点之间的关系：
     *     (1)若node结点是其父结点的左儿子，那么父节点即为该结点的后继。
     *     (2)若node结点是其父结点的右儿子，则需沿着其父结点一直向树的顶端寻找，直达找到一个结点P，P
     *        结点是其父结点Q的左儿子，那么结点Q即为该结点的后继。
     */
    public Node<T> findSuccessor(Node<T> node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            return findMin(node.right);
        }
        while (node.parent != null) {
            if (node == node.parent.left) {
                return node.parent;
            }
            node = node.parent;
        }
        return null;
    }

}
