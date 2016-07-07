package com.mrsnottypants.util.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Nearly complete binary tree, stored in an array(list)
 *
 * Created by Eric on 7/3/2016.
 */
public class BinaryTreeArray<E> implements BinaryTree<E> {

    // our node key
    //
    private static class IndexKey implements NodeKey {
        private final int index;

        IndexKey(int index) { this.index = index; }
        private static NodeKey of(int index) { return new IndexKey(index); }

        public int getIndex() { return index; }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IndexKey)) {
                return false;
            }
            IndexKey indexKey = (IndexKey)o;
            return index == indexKey.getIndex();
        }

        @Override
        public int hashCode() {
            return index;
        }

        @Override
        public String toString() {
            return String.format("Index: %d", index);
        }
    }

    /**
     * Return a new tree, initialized with the passed source
     * @param source to initialize tree
     * @param <F> type of values stored in tree
     * @return new tree
     */
    public static <F> BinaryTree of(final List<F> source) {
        return new BinaryTreeArray(source);
    }

    /**
     * Return a new, empty tree
     * @param <F> type of values stored in tree
     * @return new tree
     */
    public static <F> BinaryTree empty() {
        return new BinaryTreeArray();
    }
    
    // internal storage of tree
    private final List<E> array;

    /**
     * Construct an empty binary tree
     */
    private BinaryTreeArray() {
        array = new ArrayList<>();
    }

    /**
     * Construct a binary tree from a given list of values
     * @param source initial contents of binary tree
     */
    private BinaryTreeArray(final List<E> source) {
        array = new ArrayList<>(source);
    }

    /**
     * Return the count of nodes in the tree
     * @return count of nodex
     */
    @Override
    public int size() {
        return array.size();
    }

    /**
     * Add a value to the tree
     * @param value value to add to tree
     * @return index of newly added value
     */
    @Override
    public NodeKey add(final E value) {
        
        // add to the end of the array
        array.add(value);
        
        // the key is its array index
        return IndexKey.of(array.size() - 1);
    }

    /**
     * Return the value at a given key
     * @param key identifies the value we want to get
     * @return value
     */
    @Override
    public E get(final NodeKey key) {
        
        // confirm good index
        IndexKey indexKey = IndexKey.class.cast(key);
        confirmInBounds(indexKey.getIndex());
        
        // return value at this index
        return array.get(indexKey.getIndex());
    }

    /**
     * Swap the values at the given keys
     * @param key1 swap this value
     * @param key2 swap this value
     */
    @Override
    public void swap(final NodeKey key1, final NodeKey key2) {
        
        // confirm good indexes
        IndexKey indexKey1 = IndexKey.class.cast(key1);
        confirmInBounds(indexKey1.getIndex());
        IndexKey indexKey2 = IndexKey.class.cast(key2);
        confirmInBounds(indexKey2.getIndex());
        
        // swap values at these indexes
        E value1 = get(key1);
        array.set(indexKey1.getIndex(), get(indexKey2));
        array.set(indexKey2.getIndex(), value1);
    }

    /**
     * Return the key of the root node, or empty if tree empty
     * @return key for root node, or empty if tree empty
     */
    @Override
    public Optional<NodeKey> getRoot() {
        return outOfBounds(0) ? Optional.empty() : Optional.of(IndexKey.of(0));
    }

    /**
     * Return true if the node at this key has a parent
     * @param key of interest
     * @return true if it has a parent
     */
    @Override
    public boolean hasParent(final NodeKey key) {
        IndexKey indexKey = IndexKey.class.cast(key);
        confirmInBounds(indexKey.getIndex());
        return !outOfBounds(parentOf(indexKey.getIndex()));
    }

    /**
     * Return the key of the parent of the node picked by this key
     * @param key we want the parent of this key
     * @return key of the parent, or empty if no parent
     */
    @Override
    public Optional<NodeKey> getParent(final NodeKey key) {
        
        // confirm good index, and that it has a parent
        IndexKey indexKey = IndexKey.class.cast(key);
        confirmInBounds(indexKey.getIndex());

        // return parent, or empty if no parent
        return hasParent(key) ? Optional.of(IndexKey.of(parentOf(indexKey.getIndex()))) : Optional.empty();
    }

    /**
     * Return true if the node at this key has a left child
     * @param key of interest
     * @return true if it has a left child
     */
    @Override
    public boolean hasLeft(final NodeKey key) {
        IndexKey indexKey = IndexKey.class.cast(key);
        confirmInBounds(indexKey.getIndex());
        return !outOfBounds(leftOf(indexKey.getIndex()));
    }

    /**
     * Return the key of the left child of the given key
     * @param key we want the left child of this
     * @return key of left child, or empty if no left child
     */
    @Override
    public Optional<NodeKey> getLeft(final NodeKey key) {
        
        // confirm good index, and that it has a left child
        IndexKey indexKey = IndexKey.class.cast(key);
        confirmInBounds(indexKey.getIndex());

        return hasLeft(key) ? Optional.of(IndexKey.of(leftOf(indexKey.getIndex()))) : Optional.empty();
    }

    /**
     * Return true if the node at this key has a right child
     * @param key of interest
     * @return true if it has a right child
     */
    @Override
    public boolean hasRight(final NodeKey key) {
        IndexKey indexKey = IndexKey.class.cast(key);
        confirmInBounds(indexKey.getIndex());
        return !outOfBounds(rightOf(indexKey.getIndex()));
    }

    /**
     * Return the key of the right child of the given key
     * @param key we want the right child of this
     * @return key of right child, or empty if no right child
     */
    @Override
    public Optional<NodeKey> getRight(final NodeKey key) {
        IndexKey indexKey = IndexKey.class.cast(key);
        confirmInBounds(indexKey.getIndex());

        return hasRight(key) ? Optional.of(IndexKey.of(rightOf(indexKey.getIndex()))) : Optional.empty();
    }

    /**
     * Return true if the given index does not reference an element in the heap
     * @param index index we are checking
     * @return true if out of bounds
     */
    private boolean outOfBounds(final int index) {
        return ((index < 0) || (index >= array.size()));
    }

    /**
     * Calculates the parent index of the given index
     * @param index Index whose parent we want
     * @return index of parent
     */
    private int parentOf(final int index) {
        confirmInBounds(index);
        return ((index + 1) / 2) - 1;
    }

    /**
     * Calculates the left index of the given index
     * @param index Index whose left we want
     * @return index of left
     */
    private int leftOf(final int index) {
        confirmInBounds(index);
        return (index * 2) + 1;
    }

    /**
     * Calculates the right index of the given index
     * @param index Index whose right we want
     * @return index of right
     */
    private int rightOf(final int index) {
        confirmInBounds(index);
        return (index * 2) + 2;
    }

    /**
     * Throws an out-of-bounds exception if index not within the heap
     * @param index index to check
     */
    private void confirmInBounds(final int index) {
        if (outOfBounds(index)) {
            throw new IndexOutOfBoundsException(String.format("Index %d out of bounds, size=%d", index, array.size()));
        }
    }
}
