package cn.ning.algorithm.tree;


/**
 * 用于测试树。
 *
 */
public class TestTree {
    public static void main (String[] args) throws Exception {
        //testBinaryTree(3);
        //testBinarySearchTree();
        //testBalancedBinaryTree();
        testRedBlackTree();
    }

    /**
     * 测试二叉树。
     */
    static void testBinaryTree(int select) throws Exception {
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

        if (select == 1) {
            // 1. 根据前序和中序递归构建二叉树。
            binary_tree.setRoot(binary_tree.recursiveBuildBinaryTreePreInOrder(preorder,
                    0, preorder.length - 1, inorder, 0, inorder.length - 1));
        } else if (select == 2) {
            // 2. 根据中序和后序递归构建二叉树。
            binary_tree.setRoot(binary_tree.recursiveBuildBinaryTreeInPostOrder(inorder,
                    0, inorder.length -1, postorder, 0, postorder.length - 1));
        } else if (select == 3) {
            // 3. 根据前序和中序非递归构建二叉树。
            binary_tree.buildBinaryTreePreInOrder(preorder, inorder);
        } else {
            // 4. 根据中序和后序非递归构建二叉树
            binary_tree.buildBinaryTreeInPostOrder(inorder, postorder);
        }
        binary_tree.preorderTraversal();
        binary_tree.inorderTraversal();
        binary_tree.postorderTraversal();
    }

    /**
     * 测试二叉查找树。
     */
    static void testBinarySearchTree() throws Exception {
        Integer[] order = new Integer[] {4, 2, 8, 5, 1, 6, 3, 9, 7, 10};
        BinarySearchTree<Integer> bsTree = new BinarySearchTree<>();
        bsTree.build(order);
        bsTree.inorderTraversal();
        for(int i = 0; i < order.length; i++) {
            System.out.println(bsTree.delete(order[i]));
            //bsTree.preorderTraversal();
            bsTree.inorderTraversal();
            //bsTree.postorderTraversal();
            System.out.println();

        }

    }

    /**
     * 测试平衡二叉树。
     */
    static void testBalancedBinaryTree() {
        Integer[] order = new Integer[] {1, 4, 3, 6, 9, 7, 2, 5, 8, 10};
        BalancedBinaryTree<Integer> bTree = new BalancedBinaryTree<>();
        bTree.build(order);
        bTree.breadthTraversal();
        bTree.preorderTraversal();
        bTree.inorderTraversal();
        System.out.println();

        for (int i = 0; i < order.length; i++) {
            bTree.delete(order[i]);
            bTree.breadthTraversal();
            bTree.preorderTraversal();
            bTree.inorderTraversal();
            System.out.println();
        }
    }

    static void testRedBlackTree() {
        Integer[] order = new Integer[] {1, 4, 3, 6, 9, 7, 2, 5, 8, 10};
        RedBlackTree tree = new RedBlackTree();
        tree.build(order);
        tree.breadthTraversal();
        tree.inorderTraversal();
        System.out.println();
        for (int i = 0; i < order.length; i++) {
            tree.delete(order[i]);
            tree.breadthTraversal();
            tree.inorderTraversal();
            System.out.println();
        }
    }
}
