package cn.ning.algorithm.tree;

/**
 * 二叉树结点。
 *
 */
public class Node <T> {
    /**
     * 红黑树颜色。
     */
    public enum Color {
        red,
        black
    }
    public Color color;
    public Node<T> parent = null;
    public Node<T> left = null;
    public Node<T> right = null;
    public T val = null;
    public int height; // 自平衡二叉树，树的高度。
    public Node() {}
    public Node(T val) {
        this.val = val;
    }
}