package cn.ning.algorithm.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
 *                                          二叉树说明
 * -------------------------------------------------------------------------------------------------
 * 定义： 二叉树是每个结点最多有两个子树的树结构。
 *
 * 性质： 1. 二叉树的第k层至多有2^(k - 1)个结点；
 *       2. 深度为k的二叉树至多有2^(k - 1)个结点；
 *       3. 包含n个结点的二叉树的高度至少为：log2(n+1)；
 *       4. 任一二叉树：其终端结点(叶子结点)数为n0，度为2的结点数为n1，则n0 = n1 + 1。
 *
 * 种类： 1. 完美二叉树(满二叉树)，其深度为k且结点数为2^k - 1。
 *       2. 完全二叉树，倒数第二层满足满二叉树的条件，最后一层叶子结点靠左对齐。
 *       3. 完满二叉树，所有非叶子结点的度均为2。
 *       -----------------------------------------------------------
 *       |     perfect BT        complete BT        full BT        |
 *       |         #                 #                 #           |
 *       |       /   \             /   \             /   \         |
 *       |      #     #           #     #           #     #        |
 *       |     / \   / \         / \   /                 / \       |
 *       |    #   # #   #       #   # #                 #   #      |
 *       -----------------------------------------------------------
 *
 * 确定一颗二叉树：
 *      1. 单独的三种遍历顺序无法确定一颗二叉树。
 *      2. 先序和中序可以确定一颗二叉树。
 *      3. 中序和后序可以确定一颗二叉树。
 * -------------------------------------------------------------------------------------------------
 */

/**
 * 二叉树。
 * 实现二叉树的构建、遍历。
 *
 */
public class BinaryTree<T> {
    private Node<T> root = null;

    /**
     * 设置根结点。
     */
    public void setRoot(Node<T> root) {
        this.root = root;
    }

    /**
     * 获取根结点。
     */
    public Node<T> getRoot() {
        return this.root;
    }

    /**
     * 以中序和后序构建二叉树。
     * @param inorder 以中序遍历顺序存储的数组。
     * @param postorder 以后序遍历顺序存储的数组。
     */
    @SuppressWarnings("unchecked")
    public void buildBinaryTreeInPostOrder(T[] inorder, T[] postorder) throws Exception {
        if (inorder.length <= 0 || postorder.length <= 0) {
            return;
        }
        if (inorder.length != postorder.length) {
            throw new Exception("The array length is not equal.");
        }
        Node<T>[] nodes = new Node[postorder.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node<>(postorder[i]);
        }
        boolean[] in_visited = new boolean[inorder.length]; // 中序数组元素访问标记。
        /* 备注： 此处代码待优化，因为buildFind函数和寻找当前结点左右子树根结点有重合部分。 */
        for (int i = nodes.length - 1; i >= 0; i--) { // 更新每个结点的左右孩子。
            int index = buildFind(inorder, postorder[i]);
            in_visited[index] = true;
            boolean break_flag = false;
            int right_index = -1; // 对左右孩子寻找顺序有要求，先找右子树根结点。
            // 备注： 此处for循环本质是寻找两个数组中的相同元素，如何优化？
            for (int j = i - 1; j >= 0; j--) {
                for (int k = index + 1; k < inorder.length; k++) {
                    if (in_visited[k]) {
                        break;
                    }
                    if (postorder[j].equals(inorder[k])) {
                        in_visited[k] = true;
                        right_index = j;
                        break_flag = true;
                        break;
                    }
                }
                if (break_flag) {
                    break;
                }
            }
            if (right_index != -1) {
                nodes[i].right = nodes[right_index];
            }
            break_flag = false;
            int left_index = -1; // 再找左子树根结点。
            for (int j = (right_index == -1 ? i : right_index) - 1; j >= 0; j--) {
                for (int k = index - 1; k >= 0; k--) {
                    if (in_visited[k]) {
                        break;
                    }
                    if (postorder[j].equals(inorder[k])) {
                        in_visited[k] = true;
                        left_index = j;
                        break_flag = true;
                        break;
                    }
                }
                if (break_flag) {
                    break;
                }
            }
            if (left_index != -1) {
                nodes[i].left = nodes[left_index];
            }
        }
        this.root = nodes[nodes.length - 1];
    }

    /**
     * 以前序和中序构建二叉树。
     * @param preorder 以前序遍历顺序存储的数组。
     * @param inorder 以中序遍历顺序存储的数组。
     */
    @SuppressWarnings("unchecked")
    public void buildBinaryTreePreInOrder(T[] preorder, T[] inorder) throws Exception {
        if (preorder.length <= 0 || inorder.length <= 0) {
            return;
        }
        if (preorder.length != inorder.length) {
            throw new Exception("The array length is not equal");
        }
        Node<T>[] nodes = new Node[preorder.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node<>(preorder[i]);
        }
        boolean[] in_visited = new boolean[inorder.length]; // 中序数组中元素访问标记。
        /* 备注： 此处代码待优化，因为buildFind函数和寻找当前结点左右子树根结点有重合部分。 */
        for (int i = 0; i < nodes.length; i++) { // 更新每个结点的左右孩子。
            int index = buildFind(inorder, preorder[i]);
            if (index == -1) {
                throw new Exception("The array elements do not match.");
            }
            in_visited[index] = true;
            int left_index = -1; // 对左右孩子寻找顺序有要求，先寻找当前结点左子树的根结点。
            boolean break_flag = false;
            // 备注： 此处for循环本质是寻找两个数组中的相同元素，如何优化？
            for (int j = i + 1; j < preorder.length; j++) {
                for (int k = index - 1; k >= 0; k--) {
                    if (in_visited[k]) {
                        break;
                    }
                    if (preorder[j].equals(inorder[k])) {
                        left_index = j;
                        in_visited[k] = true;
                        break_flag = true;
                        break;
                    }
                }
                if (break_flag) {
                    break;
                }
            }
            if (left_index != -1) {
                nodes[i].left = nodes[left_index];
            }
            int right_index = -1; // 再寻找当前结点右子树的根结点。
            break_flag = false;
            for (int j = (left_index == -1 ? i : left_index) + 1; j < preorder.length; j++) {
                for (int k = index + 1; k < inorder.length; k++) {
                    if (in_visited[k]) {
                        break;
                    }
                    if (preorder[j].equals(inorder[k])) {
                        right_index = j;
                        in_visited[k] = true;
                        break_flag = true;
                        break;
                    }
                }
                if (break_flag) {
                    break;
                }
            }
            if (right_index != -1) {
                nodes[i].right = nodes[right_index];
            }
        }
        this.root = nodes[0];
    }

    /**
     * 根据中序和后序构建一颗二叉树。
     * @param inorder 以中序顺序存储的数组。
     * @param in_start 中序数组的起始位置。
     * @param in_end 中序数组的结束位置。
     * @param postorder 以后序顺序存储的数组。
     * @param post_start 后序数组的起始位置。
     * @param post_end 后序数组的结束位置。
     * @return 返回子树的根结点。
     * @throws Exception 如果同一颗二叉树的中序和后序数组长度不等，或两个数组中的元素不一致，则抛出异常。
     */
    public Node<T> recursiveBuildBinaryTreeInPostOrder(T[] inorder, int in_start, int in_end,
                    T[] postorder, int post_start, int post_end) throws Exception {
        if (in_end - in_start != post_end - post_start) {
            throw new Exception("The array length is not equal.");
        }
        if (post_end - post_start < 0) return null;
        if (post_end - post_start == 0) return new Node<>(postorder[post_end]);
        Node<T> root = new Node<>(postorder[post_end]);
        int split_index = buildFind(inorder, postorder[post_end]);
        if (split_index == -1) {
            throw new Exception("The array elements do not match.");
        }
        int right_post_end = post_end - 1;
        int right_post_start = post_end - (in_end - split_index);
        int left_post_end = right_post_start - 1;
        int left_post_start = right_post_start - (split_index - in_start);
        root.left = recursiveBuildBinaryTreeInPostOrder(inorder, in_start, split_index - 1,
                postorder, left_post_start, left_post_end);
        root.right = recursiveBuildBinaryTreeInPostOrder(inorder, split_index + 1, in_end,
                postorder, right_post_start, right_post_end);
        return root;
    }

    /**
     * 根据前序和中序递归构建二叉树。
     * @param preorder 按前序顺序存储的数组。
     * @param pre_start 前序数组的起始位置。
     * @param pre_end 前序数组的结束位置。
     * @param inorder 按中序顺序存储的数组。
     * @param in_start 中序数组起始位置。
     * @param in_end 中序数组的结束位置。
     * @return 返回子树的根结点。
     * @throws Exception 如果同一颗二叉树的先序和中序数组长度不等，或两个数组中的元素不一致，则抛出异常。
     */
    public Node<T> recursiveBuildBinaryTreePreInOrder (T[] preorder, int pre_start, int pre_end,
                   T[] inorder, int in_start, int in_end) throws Exception {
        if (pre_end - pre_start != in_end - in_start) {
            throw new Exception("The array length is not equal.");
        }
        if (pre_end - pre_start < 0) return null; // 情况1：序列长度为0。
        if (pre_end - pre_start == 0 ) return new Node<>(preorder[pre_start]); // 情况2：只有根结点。
        Node<T> root = new Node<>(preorder[pre_start]); // 情况3：除了根结点还有左子树或右子树。
        int split_index = buildFind(inorder, preorder[pre_start]);
        if (split_index == -1) {
            throw new Exception("The array elements do not match.");
        }
        int left_pre_start = pre_start + 1; // 左子树在数组中的起始位置。
        int left_pre_end = pre_start + split_index - in_start;
        int right_pre_start = left_pre_end + 1; // 右子树在数组中的起始位置。
        int right_pre_end = left_pre_end + in_end - split_index;
        root.left = recursiveBuildBinaryTreePreInOrder(preorder, left_pre_start, left_pre_end,
                inorder, in_start, split_index - 1);
        root.right = recursiveBuildBinaryTreePreInOrder(preorder, right_pre_start, right_pre_end,
                inorder, split_index + 1, in_end);
        return root;
    }

    /**
     * 查找对应元素在数组中的位置。
     * @param order 待查找数组。
     * @param target 目标元素。
     * @return 找到则返回target对应索引；没找到则返回-1。
     */
    private int buildFind(T[] order, T target) {
        for (int i = 0; i < order.length; i++) {
            if (order[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 广度遍历(Breadth First Search)。
     */
    public void breadthTraversal() {
        if (this.root == null) {
            return;
        }
        /* 采用队列数据结构进行实现。 */
        Queue<Node<T>> nodes = new LinkedList<>();
        Node<T> cur = this.root;
        nodes.add(cur);
        while (!nodes.isEmpty()) {
            cur = nodes.remove();
            System.out.println(cur.val);
            if (cur.left != null) {
                nodes.add(cur.left);
            }
            if (cur.right != null) {
                nodes.add(cur.right);
            }
        }
    }

    /**
     * 非递归后序遍历：left ---> right ---> root
     */
    public void postorderTraversal() {
        System.out.print("postorder: ");
        if (this.root == null) {
            return;
        }
        /* 实现非递归后序遍历的关键是：标记已访问过的左右子树，防止重复访问。 */
        Node<T> cur = this.root;
        Node<T> last_visit_right = null;
        Stack<Node<T>> nodes = new Stack<>();
        while (cur != null || !nodes.isEmpty()) {
            while (cur != null ) {
                nodes.push(cur);
                cur = cur.left;
            }
            cur = nodes.peek();
            if (cur.right == null || cur.right == last_visit_right) {
                System.out.print(cur.val + " ");
                last_visit_right = cur; // 存储已访问过的右子树根结点(标记已访问右子树)。
                nodes.pop();
                // 此时，被访问结点的左子树均已被访问，因此需要将cur置为null,
                // 防止再次访问cur的左子树(标记已访问左子树)。
                cur = null;
            } else {
                cur = cur.right;
            }
        }
        System.out.println();
    }

    /**
     * 非递归中序遍历：left ---> root ---> right
     */
    public void inorderTraversal() {
        System.out.print("inorder: ");
        if (this.root == null) {
            return;
        }
        Node<T> cur = this.root;
        Stack<Node<T>> nodes = new Stack<>();
        while (cur != null || !nodes.isEmpty()) {
            while (cur != null) {
                nodes.push(cur);
                cur = cur.left;
            }
            cur = nodes.pop();
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * 非递归前序遍历：root ---> left ---> right。
     */
    public void preorderTraversal() {
        System.out.print("preorder: ");
        if (this.root == null) {
            return;
        }
        Node<T> cur = this.root;
        Stack<Node<T>> nodes = new Stack<>();
        nodes.push(cur);
        while (!nodes.isEmpty()) {
            cur = nodes.pop();
            System.out.print(cur.val + " ");
            if (cur.right != null) {
                nodes.push(cur.right);
            }
            if (cur.left != null) {
                nodes.push(cur.left);
            }
        }
        System.out.println();
    }

    /**
     * 递归后序遍历：left ---> right ---> root。
     */
    public void recursivePostorderTraversal(Node<T> root) {
        if (root == null) return;
        recursivePostorderTraversal(root.left);
        recursivePostorderTraversal(root.right);
        System.out.println(root.val);
    }

    /**
     * 递归中序遍历：left ---> root ---> right。
     */
    public void recursiveInorderTraversal(Node<T> root) {
        if (root == null) return;
        recursiveInorderTraversal(root.left);
        System.out.println(root.val);
        recursiveInorderTraversal(root.right);
    }

    /**
     * 递归前序遍历：root ---> left ---> right。
     */
    public void recursivePreorderTraversal(Node<T> root) {
        if (root == null) return;
        System.out.println(root.val);
        recursivePreorderTraversal(root.left);
        recursivePreorderTraversal(root.right);
    }
}