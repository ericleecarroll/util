package com.mrsnottypants.util.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Nearly complete binary tree, stored in an array(list)
 *
 * Created by Eric on 7/3/2016.
 */
public class BinaryTreeArray<V> {

    /**
     * Return a new tree, initialized with the passed source
     * @param source to initialize tree
     * @param <U> type of values stored in tree
     * @return new tree
     */
    public static <U> BinaryTreeArray of(final List<U> source) {
        return new BinaryTreeArray(source);
    }

    /**
     * Return a new, empty tree
     * @param <U> type of values stored in tree
     * @return new tree
     */
    public static <U> BinaryTreeArray empty() {
        return new BinaryTreeArray();
    }
    
    // internal storage of tree
    private final List<V> array;

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
    private BinaryTreeArray(final List<V> source) {
        array = new ArrayList<>(source);
    }

    /**
     * Return the count of nodes in the tree
     * @return count of nodex
     */
    public int size() {
        return array.size();
    }

    /**
     * Add a value to the tree
     * @param value value to add to tree
     * @return index of newly added value
     */
    public int add(final V value) {
        
        // add to the end of the array
        array.add(value);
        
        // the key is its array index
        return array.size() - 1;
    }

    /**
     * Return the value at a given index
     * @param index the value we want to get
     * @return value
     */
    public V get(final int index) {
        
        // confirm good index
        confirmInBounds(index);
        
        // return value at this index
        return array.get(index);
    }

    /**
     * Swap the values at the given indexes
     * @param index1 swap this value
     * @param index2 swap this value
     */
    public void swap(final int index1, final int index2) {
        
        // confirm good indexes
        confirmInBounds(index1);
        confirmInBounds(index2);
        
        // swap values at these indexes
        V value1 = get(index1);
        array.set(index1, get(index2));
        array.set(index2, value1);
    }

    /**
     * Return the index of the root node
     * @return root index
     */
    public int getRoot() {
        confirmInBounds(0);
        return 0;
    }

    /**
     * Return true if the node at this index has a parent
     * @param index of interest
     * @return true if it has a parent
     */
    public boolean hasParent(final int index) {
        confirmInBounds(index);
        return !outOfBounds(parentOf(index));
    }

    /**
     * Return the index of the parent of the given index
     * @param index we want the parent of this
     * @return index of the parent
     */
    public int getParent(final int index) {
        
        // confirm good index, and that it has a parent
        confirmInBounds(index);
        if (!hasParent(index)) {
            throw new IllegalArgumentException(String.format("Index %d does not have a parent", index));
        }
        
        // return index of parent
        return parentOf(index);
    }

    /**
     * Return true if the node at this index has a left child
     * @param index of interest
     * @return true if it has a left child
     */
    public boolean hasLeft(final int index) {
        confirmInBounds(index);
        return !outOfBounds(leftOf(index));
    }

    /**
     * Return the index of the left child of the given index
     * @param index we want the left child of this
     * @return index of left child
     */
    public int getLeft(final int index) {
        
        // confirm good index, and that it has a left child
        confirmInBounds(index);
        if (!hasLeft(index)) {
            throw new IllegalArgumentException(String.format("Index %d does not have a left child", index));
        }
        
        // return index of left child
        return leftOf(index);
    }

    /**
     * Return true if the node at this index has a right child
     * @param index of interest
     * @return true if it has a right child
     */
    public boolean hasRight(final int index) {
        confirmInBounds(index);
        return !outOfBounds(rightOf(index));
    }

    /**
     * Return the index of the right child of the given index
     * @param index we want the right child of this
     * @return index of right child
     */
    public int getRight(final int index) {
        if (!hasRight(index)) {
            throw new IllegalArgumentException(String.format("Index %d does not have a right child", index));
        }
        return rightOf(index);
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
