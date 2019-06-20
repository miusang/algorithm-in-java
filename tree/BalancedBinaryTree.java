package cn.ning.algorithm.tree;

/**
 * AVL树，其发明者是Adelson-Velskii和Landis。
 *
 * 定义：
 *     1. 左右子树高度差小于等于1。
 *     2. 左右子树均为平衡二叉树，平衡二叉树首选是一颗二叉排序树。
 *
 * 平衡因子：
 *     二叉树根结点的左子树减去右子树高度的值。对于AVL树，所有包括分支结点和叶节点的平衡因子只可能是-1,0，或1，
 *     否则该二叉树就是不平衡的。
 *
 * 最小不平衡子树：
 *     距离插入节点最近的且以平衡因子的绝对值大于1的结点为根结点的子树。
 */
public class BalancedBinaryTree<T extends Comparable<T>> extends Tree<T> {
    /**
     * 构建AVL树。
     */
    public void build(T[] order) {
        for(int i = 0; i < order.length; i++) {
            if (!this.insert(order[i])) {
                System.out.println("值为" + order[i] + "的元素已经存在。");
            }
        }
    }

    /**
     * 插入元素。
     * @param val 被插入元素的值。
     * @return 如果不存在重复元素则插入成功，返回ture；否则，返回false。
     */
    public boolean insert(T val) {
        Node<T> cur = this.getRoot();
        if (cur == null) { // 树为空。
            this.setRoot(new Node<>(val));
            return true;
        }
        Node<T> parent = null;
        int compare_res = 0;
        while (cur != null) {
            compare_res = val.compareTo(cur.val);
            if (compare_res == 0) {
                return false; // 存在重复元素，返回false。
            }
            parent = cur;
            if (compare_res > 0) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        cur = new Node<>(val);
        if (compare_res > 0) {
            parent.right = cur;
            cur.parent = parent;
        } else {
            parent.left = cur;
            cur.parent = parent;
        }
        insertBalance(cur); // 对新的树进行平衡化操作。
        return true;
    }

    /**
     * 删除元素。
     * @param val 被删除元素的值。
     * @return 若果存在钙元素，删除并返回ture；否则，返回false。
     */
    public boolean delete(T val) {
        Node<T> delete_node = this.find(val);
        if (delete_node == null) {
            return false; // 树中不存在该元素，返回false。
        }
        Node<T> parent = null;
        if (delete_node.left == null && delete_node.right == null) {
            if (delete_node.parent == null) {
                this.setRoot(null);
            } else if (delete_node == delete_node.parent.left) {
                delete_node.parent.left = null;
            } else {
                delete_node.parent.right = null;
            }
            parent = delete_node.parent;
        } else if (delete_node.left != null && delete_node.right != null) {
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
            parent = replace_node.parent;
        } else if (delete_node.left != null) {
            if (delete_node.parent == null) {
                this.setRoot(delete_node.left);
            } else if (delete_node.parent.left == delete_node) {
                delete_node.parent.left = delete_node.left;
            } else {
                delete_node.parent.right = delete_node.left;
            }
            delete_node.left.parent = delete_node.parent;
            parent = delete_node.parent;
        } else {
            if (delete_node.parent == null) {
                this.setRoot(delete_node.right);
            } else if (delete_node.parent.left == delete_node) {
                delete_node.parent.left = delete_node.right;
            } else {
                delete_node.parent.right = delete_node.right;
            }
            delete_node.right.parent = delete_node.parent;
            parent = delete_node.parent;
        }
        deleteBalance(parent);
        return true;
    }

    /**
     * 查找元素。
     * @param val 被查找元素的值。
     * @return 如果找到，返回true；否则，返回false。
     */
    public Node<T> find(T val) {
        Node<T> cur = this.getRoot();
        int compare_res = 0;
        while (cur != null) {
            compare_res = val.compareTo(cur.val);
            if (compare_res == 0) {
                return cur;
            }
            if (compare_res > 0) {
                cur = cur.right;
            } else {
                cur = cur.left;
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

    /**
     * 获取结点的高度。
     */
    public int getHeight(Node<T> node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    /**
     * 计算平衡因子。
     */
    public int getBalanceFactor(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return this.getHeight(node.left) - this.getHeight(node.right);
    }

    /**
     *                  左旋。
     * -----------------------------------------
     *       #1                       #3       |
     *     /   \                    /   \      |
     *    #2    #3      ===>       #1    #5    |
     *         / \                / \          |
     *        #4  #5             #2  #4        |
     * -----------------------------------------
     */
    public void leftRotate(Node<T> node) {
        if (node == null) {
            return;
        }
        Node<T> parent = node.parent;
        Node<T> replace_node = node.right;
        if (replace_node == null) {
             System.out.println("could not left rotate.");
             return;
        }
        if (parent == null) {
            this.setRoot(replace_node);
        } else {
            replace_node.parent = parent; // 3. 更新replace_node结点父结点的指向。
            if (node == parent.left) {
                parent.left = replace_node;
            } else {
                parent.right = replace_node;
            }
        }

        node.right = replace_node.left; // 1. 更新node结点右子树。
        if (node.right != null) {
            node.right.parent = node; // 2. 更新node结点右子树父结点的指向。
        }
        // 3. 更新node结点高度。
        node.height = Math.max(this.getHeight(node.left), this.getHeight(node.right)) + 1;

        replace_node.left = node; // 1. 更新replace_node结点左子树。
        replace_node.left.parent = replace_node; // 2. 更新replace_node结点左子树父结点的指向。
        replace_node.height = Math.max(this.getHeight(replace_node.left),
                this.getHeight(replace_node.right)) + 1; // 3. 更新replace_node结点的高度。
    }

    /**
     *                   右旋。
     * -----------------------------------------
     *         #1                     #2       |
     *       /   \                  /   \      |
     *      #2    #3     ===>      #4    #1    |
     *     / \                          / \    |
     *    #4  #5                       #5  #3  |
     * -----------------------------------------
     */
    public void rightRotate(Node<T> node) {
        if (node == null) {
            return;
        }
        Node<T> replace_node = node.left;
        if (node.left == null) {
            System.out.println("could not right rotate.");
            return;
        }
        Node<T> parent = node.parent;
        if (parent == null) {
            this.setRoot(replace_node);
        } else {
            replace_node.parent = parent;
            if (node == parent.left) {
                parent.left = replace_node;
            } else {
                parent.right = replace_node;
            }
        }
        node.left = replace_node.right;
        if (node.left != null) {
            node.left.parent = node;
        }
        node.height = Math.max(this.getHeight(node.left), this.getHeight(node.right)) + 1;

        replace_node.right = node;
        replace_node.right.parent = replace_node;
        replace_node.height = Math.max(this.getHeight(replace_node.left),
                this.getHeight(replace_node.right)) + 1;
    }

    /**
     * 插入结点后，对二叉树进行平衡化操作。
     * 插入操作只需要一次平衡化就可以完成整个二叉树的平衡化。
     * @param node 被插入的结点。
     */
    public void insertBalance(Node<T> node) {
        if (node == null) {
            return;
        }
        Node<T> cur = node;
        Node<T> parent = cur.parent;
        while (parent != null) {
            // 插入结点对其父结点的高度没有影响，则不需要进行平衡化操作。
            if (parent.height - cur.height > 0) {
                break;
            }
            parent.height++;
            int balance_factor = getBalanceFactor(parent); // 获取父结点的平衡因子。
            // 1. 如果以当前父结点为根结点的二叉树是平衡的，则需要向上回溯，因为当前父结点的高度变了，需要向上更新。
            if (Math.abs(balance_factor) < 2) {
                cur = parent;
                parent = cur.parent;
                continue;
            }
            // 2. 如果以当前父结点为根结点的二叉树不平衡，则只需要一次平衡化操作，同时结束向上回溯。
            //    备注：平衡化操作中会改变当前父结点高度。
            this.balanceTree(parent);
            break;
        }
    }

    /**
     * 删除结点后，对二叉树进行平衡化操作。
     * @param node 真正被删除结点的父结点。
     */
    public void deleteBalance(Node<T> node) {
        if (node == null) {
            return;
        }
        Node<T> parent = node;
        while (parent != null) {
            parent.height = Math.max(this.getHeight(parent.left),
                    this.getHeight(parent.right)) + 1;
            int balance_factor = this.getBalanceFactor(parent);
            if (Math.abs(balance_factor) < 2) {
                parent = parent.parent;
                continue;
            }
            this.balanceTree(parent);
            break;
        }
    }

    /**
     * 二叉树的平衡化操作。
     * 此时以node结点为根结点的二叉树是不平衡的，造成不平衡的原因可以分为一下四种：
     * ----------------------------------------------
     *    (1)LL      (2)LR      (3)RL     (4)RR     |
     *      #          #          #          #      |
     *     / \        / \        / \        / \     |
     *    #          #              #          #    |
     *   / \        / \            / \        / \   |
     *  #              #          #              #  |
     * ----------------------------------------------
     * 需要根据parent结点的平衡银子以及左右子树的平衡因子，来判断不平衡的原因属于哪一种：
     * ----------------------------------------------------------------------
     * balance_factor | left_balance_factor | right_balance_factor |  case  |
     *      +         |          +          |           #          |   LL   |
     *      +         |          -          |           #          |   LR   |
     *      -         |          #          |           +          |   RL   |
     *      -         |          #          |           -          |   RR   |
     * ----------------------------------------------------------------------
     */
    public void balanceTree(Node<T> node) {
        int balance_factor = this.getBalanceFactor(node);
        int left_balance_factor = getBalanceFactor(node.left);
        int right_balance_factor = getBalanceFactor(node.right);
        if (balance_factor > 0) {
            if (left_balance_factor > 0) {
                this.rightRotate(node);
            } else {
                this.leftRotate(node.left);
                this.rightRotate(node);
            }
        } else {
            if (right_balance_factor > 0) {
                this.rightRotate(node.right);
                this.leftRotate(node);
            } else {
                this.leftRotate(node);
            }
        }
    }
}
