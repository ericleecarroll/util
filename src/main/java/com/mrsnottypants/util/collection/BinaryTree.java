package com.mrsnottypants.util.collection;

import java.util.Iterator;
import java.util.Optional;

/**
 * A binary tree - each node can have a parent, and a left and right child
 *
 * Note - methods that return keys tend to return Optionals, which will be empty if not key fits the method's criteria
 *
 * Created by Eric on 7/3/2016.
 */
public interface BinaryTree<E> {

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
    NodeKey add(E value);

    /**
     * Return the value of the node at this key
     * @param key we want this node
     * @return value of node
     */
    E get(NodeKey key);

    /**
     * Swap two nodes
     * @param key1 swap this node
     * @param key2 swap this node
     */
    void swap(NodeKey key1, NodeKey key2);

    /**
     * Return a key for the root node
     * @return root node, or empty for an empty tree
     */
    Optional<NodeKey> getRoot();

    /**
     * Return true if this node has a parent
     * @param key the node whose parent we want
     * @return true if it has a parent
     */
    boolean hasParent(NodeKey key);

    /**
     * Return the key for the parent of a node
     * @param key the node whose parent we want
     * @return key for the parent node, or empty if it has no parent
     */
    Optional<NodeKey> getParent(NodeKey key);

    /**
     * Return true if this node has a left child
     * @param key the node whose left child we want
     * @return true if it has a left child
     */
    boolean hasLeft(NodeKey key);

    /**
     * Return the key for the left child of a node
     * @param key the node whose left child we want
     * @return key for the left child node, or empty if no left child
     */
    Optional<NodeKey> getLeft(NodeKey key);

    /**
     * Return true if this node has a right child
     * @param key the node whose right child we want
     * @return true if it has a right child
     */
    boolean hasRight(NodeKey key);

    /**
     * Return the key for the right child of a node
     * @param key the node whose right child we want
     * @return key for the right child node, or empty if no left child
     */
    Optional<NodeKey> getRight(NodeKey key);

    /**
     * Return an iterator the enforces a given traversal order
     * @param traversal pre-order, in-order, etc
     * @return iterator
     */
    default Iterator<E> traverse(BinaryTreeTraversal traversal) {
        return traversal.iteratorFor(this);
    }

    /**
     * Return true if left is the left child of parent
     * @param parent looking at this node's left child
     * @param left looking to match this
     * @return true if left is the left child
     */
    default boolean isLeft(final NodeKey parent, final NodeKey left) {

        // sanity check
        if ((parent == null) || (left == null)) {
            throw new IllegalArgumentException("Neither parent nor left can be null");
        }

        // check node itself, not contained value (there can be duplicate values)
        return getLeft(parent).map(key -> left.equals(key)).orElse(false);
    }

    /**
     * Return the furthest left descendant from a given root
     * @param key want furthest left from here
     * @return furthest left descendant
     */
    default Optional<NodeKey> getLeftest(final NodeKey key) {

        // start with passed key
        Optional<NodeKey> leftest = Optional.of(key);

        // drill left while there is a left
        while (leftest.map(k -> hasLeft(k)).orElse(false)) {
            leftest = getLeft(leftest.get());
        }

        // return leftest
        return leftest;
    }

    /**
     * Return the furthest leftest leaf from a given root
     * If leftest descendant has a right child, we'll return its leftest leaf - and so on
     * @param key want furthest left leaf from here
     * @return furthest left leaf
     */
    default Optional<NodeKey> getLeftestLeaf(final NodeKey key) {

        // start with the passed key
        Optional<NodeKey> leaf = Optional.of(key);

        // while we have a node, which has a left, we know there is a leaf further down
        while (leaf.map(k -> hasLeft(k)).orElse(false)) {

            // drill down to the left as far as possible
            leaf = getLeftest(leaf.get());

            // if it has a right this isn't a leaf - we'll look for leftest in right branch
            if (leaf.map(k -> hasRight(k)).orElse(false)) {
                leaf = getRight(leaf.get());
            }
        }

        // done!
        return leaf;
    }

    /**
     * Return first ancestor where we are part of its left branch
     * Return null if such an ancestor does not exist
     * @param key want left branch ancestor from here
     * @return ancestor we are in left branch of, or empty if no such ancestor exists
     */
    default Optional<NodeKey> getParentFromLeft(final NodeKey key) {

        // start with the parent of the passed key
        Optional<NodeKey> child = Optional.of(key);
        Optional<NodeKey> parent = getParent(key);

        // while a parent exists, that we did not find from climbing up the left branch,
        // we'll keep climbing up
        while (parent.isPresent() && !isLeft(parent.get(), child.get())) {
            child = parent;
            parent = getParent(parent.get());
        }

        // done!
        return parent;
    }

    /**
     * Return the right child of the first ancestor that both has a right child and where we are part of its left branch
     * Return null if such an ancestor does not exist
     * @param key want right child of left branch ancestor from here
     * @return right child of ancestor we are in left branch of
     */
    default Optional<NodeKey> getParentRightFromLeft(NodeKey key) {

        // we'll start with the first ancestor we are within the left branch of
        Optional<NodeKey> parent = getParentFromLeft(key);

        // while such an ancestor exists, but without a right branch, we'll continue to climb up
        while (parent.map(k -> !hasRight(k)).orElse(false)) {
            parent = getParentFromLeft(parent.get());
        }

        // if we found a matching ancestor, return the root of its right branch
        return parent.map(k -> getRight(k).get());
    }
}
