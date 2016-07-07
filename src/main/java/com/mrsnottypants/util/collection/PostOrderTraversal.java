package com.mrsnottypants.util.collection;

import java.util.Iterator;
import java.util.Optional;

/**
 * Post-order traversal of a binary tree, as an iterator and without recursion or stack.
 *
 * We figure out what the next iteration will be before next is called, meaning a call to next() returns the
 * previously calculated next, and then calculates the next next.  This makes it easier to support hasNext().
 *
 * Created by Eric on 7/6/2016.
 */
public class PostOrderTraversal<E> implements Iterator<E> {

    private final BinaryTree<E> tree;
    private Optional<NodeKey> nextKey;

    /**
     * Construct a post-order iterator
     * @param tree tree we are iterating
     */
    public PostOrderTraversal(BinaryTree<E> tree) {
        this.tree = tree;
        nextKey = tree.getRoot().flatMap(key -> tree.getLeftestLeaf(key));
    }

    /**
     * Return true if there is a next value
     * @return True if there is a next
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

        // save current 'next' value before recalculating
        E next = tree.get(nextKey.get());

        // calculate next
        // if there is no parent, and we are at the root, we are done (optional will be empty)
        Optional<NodeKey> parent = tree.getParent(nextKey.get());

        // 1st - if we are the left child of our parent, and our parent also has a right branch,
        // the next node is the leftest leaf within that right branch
        Optional<NodeKey> leftestLeafOfRight = parent.filter(k -> tree.isLeft(k, nextKey.get()))
                .flatMap(k -> tree.getRight(k)).flatMap(k -> tree.getLeftestLeaf(k));

        // 2nd - either we are the right child of our parent, or our parent has no right branch,
        // the next node is our parent either way
        nextKey = leftestLeafOfRight.isPresent() ? leftestLeafOfRight : parent;

        // done!
        return next;
    }
}
