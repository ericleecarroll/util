package com.mrsnottypants.util.collection;

/**
 * A binary tree - each node can have a parent, and a left and right child
 *
 * Created by Eric on 7/3/2016.
 */
public interface BinaryTree<V> {

    /**
     * Return a count of nodes in the tree
     * @return count of nodes
     */
    int size();

    /**
     * Add a node, with this value, to the tree
     * @param value node's value
     * @return key identifying where the node was added
     */
    NodeKey add(V value);

    /**
     * Return the value of the node at this key
     * @param key we want this node
     * @return value of node
     */
    V get(NodeKey key);

    /**
     * Swap two nodes
     * @param key1 swap this node
     * @param key2 swap this node
     */
    void swap(NodeKey key1, NodeKey key2);

    /**
     * Return a key for the root node
     * @return root node
     */
    NodeKey getRoot();

    /**
     * Return true if this node has a parent
     * @param key the node whose parent we want
     * @return true if it has a parent
     */
    boolean hasParent(NodeKey key);

    /**
     * Return the key for the parent of a node
     * @param key the node whose parent we want
     * @return key for the parent node
     */
    NodeKey getParent(NodeKey key);

    /**
     * Return true if this node has a left child
     * @param key the node whose left child we want
     * @return true if it has a left child
     */
    boolean hasLeft(NodeKey key);

    /**
     * Return the key for the left child of a node
     * @param key the node whose left child we want
     * @return key for the left child node
     */
    NodeKey getLeft(NodeKey key);

    /**
     * Return true if this node has a right child
     * @param key the node whose right child we want
     * @return true if it has a right child
     */
    boolean hasRight(NodeKey key);

    /**
     * Return the key for the right child of a node
     * @param key the node whose right child we want
     * @return key for the right child node
     */
    NodeKey getRight(NodeKey key);
}
