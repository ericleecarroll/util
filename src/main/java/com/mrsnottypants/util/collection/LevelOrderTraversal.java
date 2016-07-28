package com.mrsnottypants.util.collection;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * Level order traversal of a binary tree
 *
 * Created by Eric on 7/11/2016.
 */
public class LevelOrderTraversal<E> implements Iterator<E> {

    private final BinaryTree<E> tree;
    private final Queue<NodeKey> nextQueue;

    public LevelOrderTraversal(BinaryTree<E> tree) {
        this.tree = tree;

        // queue root as the next node
        nextQueue = new ArrayDeque<>();
        tree.getRoot().ifPresent(key -> nextQueue.add(key));
    }

    @Override
    public boolean hasNext() {
        return !nextQueue.isEmpty();
    }

    @Override
    public E next() {

        // sanity check - there is a next
        if (!hasNext()) { throw new IllegalStateException("There is no next node"); }

        // next node is at the head of the queue
        NodeKey next = nextQueue.remove();

        // add left and right children to queue
        tree.getLeft(next).ifPresent(key -> nextQueue.add(key));
        tree.getRight(next).ifPresent(key -> nextQueue.add(key));

        // done!
        return tree.get(next);
    }
}
