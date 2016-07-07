package com.mrsnottypants.util.collection;

import java.util.Iterator;
import java.util.Optional;

/**
 * In-order traversal of a binary tree, as an iterator and without recursion or stack.
 *
 * We figure out what the next iteration will be before next is called, meaning a call to next() returns the
 * previously calculated next, and then calculates the next next.  This makes it easier to support hasNext().
 *
 * Created by Eric on 7/6/2016.
 */
public class InOrderTraversal<E> implements Iterator<E> {

    private final BinaryTree<E> tree;
    private Optional<NodeKey> nextKey;

    /**
     * Construct an in-order iterator
     * @param tree tree we are iterating
     */
    InOrderTraversal(final BinaryTree<E> tree) {
        this.tree = tree;

        // calculate the first 'next'
        nextKey = tree.getRoot().flatMap(key -> tree.getLeftest(key));
    }

    /**
     * Return true if there is a next node
     * @return true if there is a next node
     */
    @Override
    public boolean hasNext() {
        return nextKey.isPresent();
    }

    /**
     * Return the value held by the next node
     * @return value held by the next node
     */
    @Override
    public E next() {

        // this is what we'll return
        E next = tree.get(nextKey.get());

        // calculate 'next' node
        // 1st - if there is a right branch, the next node is the leftest most node in that right branch
        Optional<NodeKey> leftestFromRight = tree.getRight(nextKey.get()).flatMap(key -> tree.getLeftest(key));

        // 2nd - otherwise, the next node is the first ancestor for which we are in its left branch
        nextKey = leftestFromRight.isPresent() ? leftestFromRight : tree.getParentFromLeft(nextKey.get());

        // done!
        return next;
    }
}
