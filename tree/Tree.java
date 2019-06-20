package cn.ning.algorithm.tree;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public abstract class Tree<T> {
    private Node<T> root;

    /**
     * 获取根结点。
     */
    public Node<T> getRoot() {
        return root;
    }

    /**
     * 设置根结点。
     */
    public void setRoot(Node<T> root) {
        if (root != null) {
            root.parent = null;
        }
        this.root = root;
    }

    /**
     * 二叉树广度遍历(Breadth First Search)。
     */
    public void breadthTraversal() {
        System.out.print("breadth: ");
        if (root == null) {
            return;
        }
        /* 采用队列数据结构进行实现。 */
        Queue<Node> nodes = new LinkedList<>();
        Node cur = root;
        nodes.add(cur);
        while (!nodes.isEmpty()) {
            cur = nodes.remove();
            if (this instanceof BalancedBinaryTree) {
                System.out.print(cur.val + "," + cur.height + " ");
            } else if (this instanceof RedBlackTree) {
                System.out.print(cur.val + "," +
                        (cur.color == Node.Color.red ? "r" : "b") + " ");
            } else {
                System.out.print(cur.val + " ");
            }

            if (cur.left != null) {
                nodes.add(cur.left);
            }
            if (cur.right != null) {
                nodes.add(cur.right);
            }
        }
        System.out.println();
    }

    /**
     * 二叉树非递归后序遍历：left ---> right ---> root
     */
    public void postorderTraversal() {
        System.out.print("postorder: ");
        if (root == null) {
            return;
        }
        /* 实现非递归后序遍历的关键是：标记已访问过的左右子树，防止重复访问。 */
        Node cur = root;
        Node last_visit_right = null;
        Stack<Node> nodes = new Stack<>();
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
     * 二叉树非递归中序遍历：left ---> root ---> right
     */
    public void inorderTraversal() {
        System.out.print("inorder: ");
        if (root == null) {
            return;
        }
        Node cur = root;
        Stack<Node> nodes = new Stack<>();
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
     * 二叉树非递归前序遍历：root ---> left ---> right。
     */
    public void preorderTraversal() {
        System.out.print("preorder: ");
        if (root == null) {
            return;
        }
        Node cur = root;
        Stack<Node> nodes = new Stack<>();
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
     * 二叉树递归后序遍历：left ---> right ---> root。
     */
    public void recursivePostorderTraversal(Node root) {
        if (root == null) return;
        recursivePostorderTraversal(root.left);
        recursivePostorderTraversal(root.right);
        System.out.println(root.val);
    }

    /**
     * 二叉树递归中序遍历：left ---> root ---> right。
     */
    public void recursiveInorderTraversal(Node root) {
        if (root == null) return;
        recursiveInorderTraversal(root.left);
        System.out.println(root.val);
        recursiveInorderTraversal(root.right);
    }

    /**
     * 二叉树递归前序遍历：root ---> left ---> right。
     */
    public void recursivePreorderTraversal(Node root) {
        if (root == null) return;
        System.out.println(root.val);
        recursivePreorderTraversal(root.left);
        recursivePreorderTraversal(root.right);
    }
}
