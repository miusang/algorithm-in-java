package cn.ning.algorithm.tree;


/**
 * 红黑树。
 * 红黑树(red-black tree)是一种自平衡二叉查找树，典型用途是实现关联数组。红黑树结构复杂，但它的操作
 * 有着良好的最坏情况运行时间，并且在实践中高效：可以在O(logn)时间内完成查找、插入和删除。
 *
 * 性质：
 *     1. 结点是红色或黑色；
 *     2. 根结点是黑色；
 *     3. 所有叶子结点都是黑色；(叶子是NIL结点)
 *     4. 每个红色结点必须有两个黑色的子结点；(从每个叶子到根的所有路径上不能有两个连续的红色结点)
 *     5. 从任一结点到其每个叶子的所有简单路径都包含相同数目的黑色结点。
 */
public class RedBlackTree<T extends Comparable<T>> extends Tree<T> {

    /**
     * 构建红黑树。
     * @param order 被构建序列。
     */
    public void build(T[] order) {
        for (T item : order) {
            if (!insert(item)) {
                System.out.println(item + "已经存在。");
            }
        }
    }

    /**
     * 插入节点：
     *     1. 将红黑树当做二叉搜索树，插入节点；
     *     2. 将被插入结点着色为红色；
     *     3. 通过旋转和重新着色等方法对该树进行修正，使之重新成为一颗红黑树。
     *
     * @param val 被插入元素的值。
     */
    public boolean insert(T val) {
        Node<T> cur = this.getRoot();
        if (cur == null) {
            cur = new Node<>(val);
            cur.color = Node.Color.black;
            this.setRoot(cur);
            return true;
        }
        Node<T> parent = null;
        int compare_res = 0;
        while (cur != null) {
            compare_res = val.compareTo(cur.val);
            if (compare_res == 0) {
                return false;
            }
            parent = cur;
            if (compare_res > 0) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        cur = new Node<>(val);
        cur.color = Node.Color.red;
        if (compare_res > 0) {
            parent.right = cur;
        } else {
            parent.left = cur;
        }
        cur.parent = parent;
        insertFixUp(cur);
        return true;
    }

    /**
     * 插入节点后进行修正：
     *     1. 如果被插入的结点是根结点，则将根结点的颜色变为黑色；
     *     2. 如果被插入结点的父结点是黑色的，则红黑树的性质没有遭到破坏，什么都不需要做；
     *     3. 如果被插入结点的父结点是红色的则进一步分为一下三种情况进行操作(右孩子情形与之对称)：
     *        (1). 当前结点的祖父结点的另一个结点(叔叔结点)也是红色：
     *              a. 将父结点设置为黑色；
     *              b. 将叔叔结点设置为黑色；
     *              c. 将祖父结点设置为红色，此时需要对祖父结点进行修正。
     *        (2). 如果叔叔结点是黑色，并且被插入结点是其父结点的右孩子，则：
     *             将父结点设为当前结点，并以当前结点作为支点进行左旋，此时情况转化为(3)。
     *        (3). 如果叔叔结点是黑色的，并且被插入结点是其父结点的左孩子，则：
     *             a. 将其父结点设置为黑色；
     *             b. 将祖父结点设置为红色；
     *             c. 以祖父结点为支点进行右旋。
     */
    public void insertFixUp(Node<T> node) {
        while (node.parent != null) {
            Node<T> parent = node.parent;
            if (parent.color == Node.Color.black) {
                break;
            }
            // 如果父结点的颜色是红色的，则一定有祖父节点。
            Node<T> grandparent = parent.parent;
            if (parent == grandparent.left) {
                Node<T> uncle = grandparent.right;
                if (uncle != null && uncle.color == Node.Color.red) {
                    parent.color = Node.Color.black;
                    uncle.color = Node.Color.black;
                    grandparent.color = Node.Color.red;
                    node = grandparent;
                    continue;
                }
                if (node == parent.right) {
                    node = parent;
                    leftRotate(node);
                    parent = node.parent;
                }
                parent.color = Node.Color.black;
                grandparent.color = Node.Color.red;
                rightRotate(grandparent);
                break;//右旋之后，node结点的父结点变为黑色，在下一次循环中也会跳出循环。
            } else {
                Node<T> uncle = grandparent.left;
                if (uncle != null && uncle.color == Node.Color.red) {
                    parent.color = Node.Color.black;
                    uncle.color = Node.Color.black;
                    grandparent.color = Node.Color.red;
                    node = grandparent;
                    continue;
                }
                if (node == parent.left) {
                    node = parent;
                    rightRotate(node);
                    parent = node.parent;
                }
                parent.color = Node.Color.black;
                grandparent.color = Node.Color.red;
                leftRotate(grandparent);
                break;
            }
        }
        this.getRoot().color = Node.Color.black;
    }

    /**
     * 删除结点。
     * @param val 被删除结点的值。
     */
    public boolean delete(T val) {
        Node<T> delete_node = this.find(val);
        if (delete_node == null) {
            return false;
        }
        Node<T> fix_node = null;
        // fix_parent 和 at_left 在 fix_node 为null时会用到。
        Node<T> fix_parent = null; //
        boolean at_left = false; // 判断被修正结点位于父结点左子树还是右子树。

        Node.Color delete_color;
        if (delete_node.left == null && delete_node.right == null) {
            if (delete_node.parent == null) {
                this.setRoot(null);
                return true;
            } else if (delete_node == delete_node.parent.left) {
                delete_node.parent.left = null;
                at_left = true;
            } else {
                delete_node.parent.right = null;
                at_left = false;
            }
            delete_color = delete_node.color;
            fix_node = null;
            fix_parent = delete_node.parent;

        } else if (delete_node.left != null && delete_node.right != null) {
            Node<T> replace_node = this.findPredecessor(delete_node); // 前驱结点没有右子树。
            delete_node.val = replace_node.val;
            if (replace_node == replace_node.parent.left) {
                replace_node.parent.left = replace_node.left;
                at_left = true;
            } else {
                replace_node.parent.right = replace_node.left;
                at_left = false;
            }
            if (replace_node.left != null) {
                replace_node.left.parent = replace_node.parent;
                fix_node = replace_node.left;
            } else {
                fix_node = null;
            }
            delete_color = replace_node.color;
            fix_parent = replace_node.parent;
        } else if (delete_node.left != null) {
            if (delete_node.parent == null) {
                this.setRoot(delete_node.left);
            } else if (delete_node.parent.left == delete_node) {
                delete_node.parent.left = delete_node.left;
            } else {
                delete_node.parent.right = delete_node.left;
            }
            delete_node.left.parent = delete_node.parent;
            fix_node = delete_node.left;
            delete_color = delete_node.color;
        } else {
            if (delete_node.parent == null) {
                this.setRoot(delete_node.right);
            } else if (delete_node.parent.left == delete_node) {
                delete_node.parent.left = delete_node.right;
            } else {
                delete_node.parent.right = delete_node.right;
            }
            delete_node.right.parent = delete_node.parent;
            fix_node = delete_node.right;
            delete_color = delete_node.color;
        }
        if (delete_color == Node.Color.black) {
            deleteFixUp(fix_node, at_left, fix_parent); // 由于删除了一个黑色结点，会破坏性质2，4或5。
        }
        return true;
    }

    /**
     * 删除结点后，进行修正：
     *     由于自node结点开始的结点缺少一个黑色，因此，将黑色给node，此时从node开始，向根方向移动，
     *     直到出现以下三种状态：
     *     1. node指向一个“红+黑”结点，则将node的颜色属性置为黑色，完成修正。
     *     2. node指向根，将node颜色属性置为黑色，完成修正。
     *     3. 如果不是以上两种情况，则进一步分为以下五种情况：
     *        --------------------------------------------------------------------------
     *                                          P
     *                                        /   \
     *                                    -->N     S
     *                                            / \
     *                                           SL SR              备注：N即node结点。
     *        --------------------------------------------------------------------------
     *        (1). N、S、SL、SR、P都为黑色：
     *             a. 将S的颜色修改为红色；
     *             b. 将P结点看做N结点重新执行调整算法。
     *
     *        (2). N、S、SL、SR都为黑色，P为红色：
     *             将S和P的颜色互换，完成调整。
     *
     *        (3). N、S为黑色，SR是红色(考虑N为P的左孩子情形，N为P的右孩子的情形与之对称)：
     *             a. 对P进行左旋；
     *             b. 交换P和S的颜色；
     *             c. 将SR的颜色改为黑色，完成调整。
     *
     *        (4). N、S是黑色，SL是红色，SR是黑色(考虑N为P的左孩子情形，N为P的右孩子的情形与之对称)：
     *             a. 对S进行右旋；
     *             b. 交换S和SL的颜色；
     *             c. 此时情形转化为(3)，按照情形(3)进行处理即可。
     *
     *        (5). N是黑色，S为红色(考虑N为P的左孩子情形，N为P的右孩子的情形与之对称)：
     *             a. 对P进行左旋；
     *             b. 将S和P的颜色互换；
     *             c. 根据具体情形参考情形(1)、(2)、(3)、(4)进行处理即可。
     *        -----------------------------------------------------------------------------
     *
     */
    public void deleteFixUp(Node<T> node, boolean at_left, Node<T> parent) {
        while (node != this.getRoot()) {
            if (node != null) {
                if (node.color == Node.Color.red) {
                    break;
                }
                at_left = node == node.parent.left;
                parent = node.parent;
            }
            Node<T> brother = null; // 兄弟结点一定不为空。
            if (at_left) {
                brother = parent.right;
                if (brother.color == Node.Color.black ) {
                    if (brother.left != null && brother.left.color == Node.Color.red) {
                        rightRotate(brother);
                        brother.color = Node.Color.red;
                        brother.parent.color = Node.Color.black;
                        brother = brother.parent;
                    }
                    if (brother.right != null && brother.right.color == Node.Color.red) {
                        leftRotate(parent);
                        brother.color = parent.color;
                        parent.color = Node.Color.black;
                        brother.right.color = Node.Color.black;
                        break;
                    }
                } else {
                    leftRotate(parent);
                    parent.color = Node.Color.red;
                    brother.color = Node.Color.black;
                    brother = parent.right;
                }
            } else {
                brother = parent.left;
                if (brother.color == Node.Color.black ) {
                    if (brother.right != null && brother.right.color == Node.Color.red) {
                        leftRotate(brother);
                        brother.color = Node.Color.red;
                        brother.parent.color = Node.Color.black;
                        brother = brother.parent;
                    }
                    if (brother.left != null && brother.left.color == Node.Color.red) {
                        rightRotate(parent);
                        brother.color = parent.color;
                        parent.color = Node.Color.black;
                        brother.left.color = Node.Color.black;
                        break;
                    }
                } else {
                    rightRotate(parent);
                    parent.color = Node.Color.red;
                    brother.color = Node.Color.black;
                    brother = parent.left;
                }
            }
            boolean parent_is_black = parent.color == Node.Color.black;
            boolean brother_is_balck = brother.color == Node.Color.black;

            boolean brother_left_is_black = (brother.left == null ||
                    brother.left.color == Node.Color.black);

            boolean brother_right_is_black = (brother.right == null ||
                    brother.right.color == Node.Color.black);

            if (brother_is_balck && brother_left_is_black && brother_right_is_black) {
                if (parent_is_black) {
                    brother.color = Node.Color.red;
                    node = parent;
                    continue;
                } else {
                    parent.color = Node.Color.black;
                    brother.color = Node.Color.red;
                    break;
                }
            }
        }
        if (node != null) {
            node.color = Node.Color.black;
        }
    }

    /**
     *            左旋。
     * --------------------------+
     *     #1              #2    |
     *    / \             / \    |
     *       #2   ===>   #1      |
     *      / \         / \      |
     *     #3              #3    |
     * --------------------------+
     */
    public void leftRotate(Node<T> node) {
        if (node == null) {
            return;
        }
        Node<T> parent = node.parent;
        Node<T> replace_node = node.right;
        if (replace_node == null) {
            return;
        }

        node.right = replace_node.left;
        if (node.right != null) {
            node.right.parent = node;
        }

        replace_node.left = node;
        replace_node.left.parent = replace_node;

        if (parent == null) {
            this.setRoot(replace_node);
        } else {
            if (node == parent.left) {
                parent.left = replace_node;
            } else {
                parent.right = replace_node;
            }
            replace_node.parent = parent;
        }
    }

    /**
     *            右旋。
     * --------------------------+
     *      #1             #2    |
     *     / \            / \    |
     *    #2      ===>      #1   |
     *   / \               / \   |
     *      #3            #3     |
     * --------------------------+
     */
    public void rightRotate(Node<T> node) {
        if (node == null) {
            return;
        }
        Node<T> parent = node.parent;
        Node<T> replace_node = node.left;
        if (replace_node == null) {
            return;
        }

        node.left = replace_node.right;
        if (node.left != null) {
            node.left.parent = node;
        }

        replace_node.right = node;
        replace_node.right.parent = replace_node;

        if (parent == null) {
            this.setRoot(replace_node);
        } else {
            if (node == parent.left) {
                parent.left = replace_node;
            } else {
                parent.right = replace_node;
            }
            replace_node.parent = parent;
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
