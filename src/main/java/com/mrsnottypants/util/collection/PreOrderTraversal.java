package com.mrsnottypants.util.collection;

import java.util.Iterator;
import java.util.Optional;

/**
 * Pre-order traversal of a binary tree, as an iterator and without recursion or stack.
 *
 * We figure out what the next iteration will be before next is called, meaning a call to next() returns the
 * previously calculated next, and then calculates the next next.  This makes it easier to support hasNext().
 *
 * Created by Eric on 7/6/2016.
 */
public class PreOrderTraversal<E> implements Iterator<E> {

    private final BinaryTree<E> tree;
    private Optional<NodeKey> nextKey;

    /**
     * Construct a pre-order iterator
     * @param tree tree we are iterating
     */
    public PreOrderTraversal(BinaryTree<E> tree) {
        this.tree = tree;
        nextKey = tree.getRoot();
    }

    /**
     * Return true if there is a next node
     * @return True if there is a next node
     */
    @Override
    public boolean hasNext() {
        return nextKey.isPresent();
    }

    /**
     * Return the value held by the next node
     * @return value of next node
     */
    @Override
    public E next() {

        // remember the 'next' value before we recalculate
        E next = tree.get(nextKey.get());

        // calculate next
        // 1st - if there is a left child, that is our next node
        Optional<NodeKey> left = tree.getLeft(nextKey.get());

        // 2nd - if there is no left child, the next node is the right child of the first ancestor for which we are
        // in its left branch
        nextKey = left.isPresent() ? left : tree.getParentRightFromLeft(nextKey.get());

        // done!
        return next;
    }
}
