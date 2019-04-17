package cn.ning.algorithm.tree;

/**
 * 用于测试树。
 *
 */
public class TestTree {
    public static void main (String[] args) throws Exception {
        testBinaryTree();
    }

    /**
     * 测试二叉树。
     */
    static void testBinaryTree() throws Exception {
        /*
         *                                Binary Tree
         * -----------------------------------------------------------------------------------------
         *                                     1
         *                                 /       \
         *                                2         3
         *                              /   \     /   \
         *                             4     5   6     7
         *                                  /         / \
         *                                 8         9  10
         * -----------------------------------------------------------------------------------------
         */
        Integer[] preorder = new Integer[] {1, 2, 4, 5, 8, 3, 6, 7, 9, 10};
        Integer[] inorder = new Integer[] {4, 2, 8, 5, 1, 6, 3, 9, 7, 10};
        Integer[] postorder = new Integer[] {4, 8, 5, 2, 6, 9, 10, 7, 3, 1};
        BinaryTree<Integer> binary_tree = new BinaryTree<>();
        // 1. 根据前序和中序递归构建二叉树。
//        binary_tree.setRoot(binary_tree.recursiveBuildBinaryTreePreInOrder(preorder,
//                0, preorder.length - 1, inorder, 0, inorder.length - 1));
        // 2. 根据中序和后序递归构建二叉树。
//        binary_tree.setRoot(binary_tree.recursiveBuildBinaryTreeInPostOrder(inorder,
//                0, inorder.length -1, postorder, 0, postorder.length - 1));
        // 3. 根据前序和中序非递归构建二叉树。
//        binary_tree.buildBinaryTreePreInOrder(preorder, inorder);
        // 4. 根据中序和后序非递归构建二叉树
        binary_tree.buildBinaryTreeInPostOrder(inorder, postorder);

        binary_tree.preorderTraversal();
        binary_tree.inorderTraversal();
        binary_tree.postorderTraversal();
    }
}
