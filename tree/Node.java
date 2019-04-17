package cn.ning.algorithm.tree;

/**
 * 二叉树结点。
 *
 */
public class Node <T> {
    public Node<T> left = null;
    public Node<T> right = null;
    public T val = null;
    public Node() {}
    public Node(T val) {
        this.val = val;
    }
}