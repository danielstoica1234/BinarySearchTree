import com.sun.source.tree.Tree;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Binary Search Tree implementation in Java.
 *
 * @author [Your Name]
 * @date [Current Date]
 */
public class BinarySearchTree {

    private TreeNode root;

    /**
     * Constructor - initializes an empty BST.
     */
    public BinarySearchTree() {
        this.root = null;
    }

    public void test1() {
        TreeNode node1 = new TreeNode(50);
        TreeNode node2 = new TreeNode(40);
        TreeNode node3 = new TreeNode(30);
        TreeNode node4 = new TreeNode(75);
        TreeNode node5 = new TreeNode(100);
        root = node1;
        node1.left = node2;
        node2.left = node3;
        node1.right = node4;
        node4.right = node5;
    }

    /**
     * Insert a value into the BST.
     * If the value already exists, do not insert it (no duplicates allowed).
     *
     * @param value The value to insert
     */
    public void insert(int value) {
        // TODO: Implement this method
        // Hint: Use a recursive helper method
        root = insertVal(root, value);
    }

    private TreeNode insertVal(TreeNode node, int value){
        if(node == null){
            return new TreeNode(value);
        }

        if(value < node.data){
            node.left = insertVal(node.left, value);
        } else if (value > node.data){
            node.right = insertVal(node.right, value);
        }

        return node;
    }
    /**
     * Search for a value in the BST.
     *
     * @param value The value to search for
     * @return true if the value exists in the tree, false otherwise
     */
    public boolean search(int value) {
        // TODO: Implement this method
        // Hint: Use recursion and leverage BST property
        TreeNode cur = root;

        while (cur != null){
            if(value == cur.data){
                return true;
            } else if (value < cur.data){
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }

        return false;
    }

    /**
     * Delete a value from the BST.
     * If the value doesn't exist, do nothing.
     *
     * @param value The value to delete
     */
    public void delete(int value) {
        // TODO: Implement this method
        // Hint: Handle three cases - leaf, one child, two children
        // For two children, use inorder successor or predecessor
        root = deleteVal(root, value);
    }

    private TreeNode deleteVal(TreeNode node, int value){
        if(node == null){
            return null;
        }

        if(value < node.data){
            node.left = deleteVal(node.left, value);
        } else if (value > node.data){
            node.right = deleteVal(node.right, value);
        } else {
            if(node.left == null && node.right == null){
                return null;
            }
            if(node.left == null){
                return node.right;
            }
            if(node.right == null){
                return node.left;
            }

            TreeNode successor = node.right;
            while(successor.left != null){
                successor = successor.left;
            }
            node.data = successor.data;
            node.right = deleteVal(node.right, successor.data);
        }
        return node;
    }

    /**
     * Find the minimum value in the BST.
     *
     * @return The minimum value
     * @throws IllegalStateException if the tree is empty
     */
    public int findMin() {
        // TODO: Implement this method
        // Hint: Keep going left!
        if(isEmpty()) {
            throw new IllegalStateException("Empty tree!");
        }
        TreeNode cur = root;
        while(cur.left != null){
            cur = cur.left;
        }
        return cur.data;
    }

    /**
     * Find the maximum value in the BST.
     *
     * @return The maximum value
     * @throws IllegalStateException if the tree is empty
     */
    public int findMax() {
        // TODO: Implement this method
        // Hint: Keep going right!
        if(isEmpty()) {
            throw new IllegalStateException("Empty tree!");
        }
        TreeNode cur = root;
        while(cur.right != null){
            cur = cur.right;
        }
        return cur.data;
    }

    /**
     * Perform an inorder traversal of the BST.
     *
     * @return A list of values in inorder sequence
     */
    public List inorderTraversal() {
        // TODO: Implement this method
        // Hint: Left -> Root -> Right
        // Should return values in sorted order!
        List<Integer> list = new ArrayList<>();
        inorderRet(root, list);
        return list;
    }

    private void inorderRet(TreeNode node, List<Integer> list){
        if(node == null){
            return;
        }
        inorderRet(node.left, list);
        list.add(node.data);
        inorderRet(node.right, list);
    }

    /**
     * Perform a preorder traversal of the BST.
     *
     * @return A list of values in preorder sequence
     */
    public List preorderTraversal() {
        // TODO: Implement this method
        // Hint: Root -> Left -> Right
        List<Integer> list = new ArrayList<>();
        preorderRet(root, list);
        return list;
    }

    private void preorderRet(TreeNode node, List<Integer> list){
        if(node == null){
            return;
        }
        list.add(node.data);
        inorderRet(node.left, list);
        inorderRet(node.right, list);
    }

    /**
     * Perform a postorder traversal of the BST.
     *
     * @return A list of values in postorder sequence
     */
    public List postorderTraversal() {
        // TODO: Implement this method
        // Hint: Left -> Right -> Root
        List<Integer> list = new ArrayList<>();
        postorderRet(root, list);
        return list;
    }

    private void postorderRet(TreeNode node, List<Integer> list){
        if(node == null){
            return;
        }
        inorderRet(node.left, list);
        inorderRet(node.right, list);
        list.add(node.data);
    }

    /**
     * Calculate the height of the BST.
     * Height is defined as the number of edges on the longest path from root to leaf.
     * An empty tree has height -1, a tree with one node has height 0.
     *
     * @return The height of the tree
     */
    public int height() {
        // TODO: Implement this method
        // Hint: Use recursion - height = 1 + max(left height, right height)
        return heightFind(root);
    }

    private int heightFind(TreeNode node){
        if(isEmpty()){
            return -1;
        }
        return 1 + Math.max(heightFind(node.left), heightFind(node.right));
    }

    /**
     * Count the number of nodes in the BST.
     *
     * @return The number of nodes
     */
    public int size() {
        // TODO: Implement this method
        // Hint: Recursively count nodes
        return sizeIterate(root);
    }

    private int sizeIterate(TreeNode node){
        if(node == null){
            return 0;
        }
        return 1 + sizeIterate(node.left) + sizeIterate(node.right);
    }

    /**
     * Check if the BST is empty.
     *
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        // TODO: Implement this method
        return root == null;
    }

    /**
     * Get the root of the tree (for testing purposes).
     *
     * @return The root node
     */
    public TreeNode getRoot() {
        return this.root;
    }

    // ========================================
    // HELPER METHODS
    // You may add private helper methods below
    // ========================================

    // Example helper method structure:
    // private TreeNode insertHelper(TreeNode node, int value) {
    //     // Your code here
    // }

}