package com.mrsnottypants.util.collection;

import java.util.List;
import java.util.Optional;

/**
 * Created by Eric on 7/3/2016.
 */
public class MaxHeap<V extends Comparable<V>> implements Heap<V> {

    private final BinaryTree<V> tree;

    private MaxHeap() {
        tree = BinaryTreeArray.empty();
    }

    private MaxHeap(List<V> source) {
        tree = BinaryTreeArray.of(source);
    }

    /**
     * Unordered tree -> max-heap
     */
    private void buildMaxHeap() {

        // TODO

        // call max-heapify on all but the leafs
//        for (int root = (tree.size() / 2) - 1 ; root >= 0 ; root--) {
//            maxHeapify(root);
//        }
    }

    /**
     * Assumes the trees rooted at left and right are max heaps, but that the specified root might be smaller than a
     * child.  This method ensures the tree rooted at the specified root is a max heap.
     * @param rootKey index of root to max-heapify
     */
    private void maxHeapify(final NodeKey rootKey) {

        // default to root
        Optional<NodeKey> largest = Optional.of(rootKey);

        // check left
        final Optional<NodeKey> left = tree.getLeft(rootKey);
        if (isLargerThan(left, largest)) {
            largest = left;
        }

        // check right
        final Optional<NodeKey> right = tree.getRight(rootKey);
        if (isLargerThan(right, largest)) {
            largest = right;
        }

        // if root is not largest, swap with largest and continue downwards
        largest = largest.filter(key -> key != rootKey);
        if (largest.isPresent()) {
            tree.swap(largest.get(), rootKey);
            maxHeapify(largest.get());
        }
    }

    /**
     * Returns true if the value held by key1 is larger than the value held by key2
     * @param key1 comparing this
     * @param key2 comparing this
     * @return key1's value is larger
     */
    private boolean isLargerThan(Optional<NodeKey> key1, Optional<NodeKey> key2) {

        // optional values from optional keys!
        Optional<V> value1 = key1.map(tree::get);
        Optional<V> value2 = key2.map(tree::get);

        // candidate not set
        if (!value1.isPresent()) { return false; }

        // compare
        return value2.map(v -> v.compareTo(value2.get()) <= 0).orElse(false);
    }
}
