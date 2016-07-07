package com.mrsnottypants.util.collection;

import com.mrsnottypants.test.Exceptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Eric on 7/3/2016.
 */
public class BinaryTreeArrayTest {

    static final List<String> SOURCE = Arrays.asList("a", "b", "c", "d", "e", "f");

    BinaryTree<String> tree;

    NodeKey rootKey;
    NodeKey leftKey;
    NodeKey rightKey;
    NodeKey leftLeftKey;
    NodeKey leftRightKey;
    NodeKey rightLeftKey;

    @Before
    public void before() {

        tree = BinaryTreeArray.of(SOURCE);

        rootKey = tree.getRoot().get();
        leftKey = tree.getLeft(rootKey).get();
        rightKey = tree.getRight(rootKey).get();
        leftLeftKey = tree.getLeft(leftKey).get();
        leftRightKey = tree.getRight(leftKey).get();
        rightLeftKey = tree.getLeft(rightKey).get();
    }

    @Test
    public void testSize() {
        Assert.assertEquals(SOURCE.size(), tree.size());
    }

    @Test
    public void testAdd() {

        BinaryTree<String> emptyTree = BinaryTreeArray.empty();
        Assert.assertEquals(0, emptyTree.size());

        for (String entry : SOURCE) {
            NodeKey key = emptyTree.add(entry);
            Assert.assertEquals(entry, emptyTree.get(key));
        }

        Assert.assertEquals(SOURCE.size(), emptyTree.size());
    }

    @Test
    public void testGet() {

        Assert.assertEquals(SOURCE.get(0), tree.get(rootKey));
        Assert.assertEquals(SOURCE.get(1), tree.get(leftKey));
        Assert.assertEquals(SOURCE.get(2), tree.get(rightKey));
        Assert.assertEquals(SOURCE.get(3), tree.get(leftLeftKey));
        Assert.assertEquals(SOURCE.get(4), tree.get(leftRightKey));
        Assert.assertEquals(SOURCE.get(5), tree.get(rightLeftKey));
    }

    @Test
    public void testSwap() {

        tree.swap(rootKey, rightKey);

        Assert.assertEquals(SOURCE.get(0), tree.get(rightKey));
        Assert.assertEquals(SOURCE.get(2), tree.get(rootKey));
        Assert.assertEquals(SOURCE.size(), tree.size());
    }

    @Test
    public void testGetRoot() {

        Assert.assertEquals(SOURCE.get(0), tree.get(rootKey));

        // out of bounds
        tree = BinaryTreeArray.empty();
        Optional<NodeKey> root = tree.getRoot();
        Assert.assertFalse(root.isPresent());
    }

    @Test
    public void testHasParent() {

        Assert.assertTrue(tree.hasParent(leftKey));
        Assert.assertTrue(tree.hasParent(rightKey));
        Assert.assertTrue(tree.hasParent(leftLeftKey));
        Assert.assertTrue(tree.hasParent(leftRightKey));
        Assert.assertTrue(tree.hasParent(rightLeftKey));

        Assert.assertFalse(tree.hasParent(rootKey));
    }

    @Test
    public void testGetParent() {

        Assert.assertEquals(rootKey, tree.getParent(leftKey).get());
        Assert.assertEquals(rootKey, tree.getParent(rightKey).get());
        Assert.assertEquals(leftKey, tree.getParent(leftLeftKey).get());
        Assert.assertEquals(leftKey, tree.getParent(leftRightKey).get());
        Assert.assertEquals(rightKey, tree.getParent(rightLeftKey).get());

        // no parent
        Assert.assertFalse(tree.getParent(rootKey).isPresent());
    }

    @Test
    public void testHasLeft() {

        Assert.assertTrue(tree.hasLeft(rootKey));
        Assert.assertTrue(tree.hasLeft(leftKey));
        Assert.assertTrue(tree.hasLeft(rightKey));

        Assert.assertFalse(tree.hasLeft(leftLeftKey));
        Assert.assertFalse(tree.hasLeft(leftRightKey));
        Assert.assertFalse(tree.hasLeft(rightLeftKey));
    }

    @Test
    public void testGetLeft() {

        Assert.assertEquals(leftKey, tree.getLeft(rootKey).get());
        Assert.assertEquals(leftLeftKey, tree.getLeft(leftKey).get());
        Assert.assertEquals(rightLeftKey, tree.getLeft(rightKey).get());

        // no left
        Assert.assertFalse(tree.getLeft(leftLeftKey).isPresent());
        Assert.assertFalse(tree.getLeft(leftRightKey).isPresent());
        Assert.assertFalse(tree.getLeft(rightLeftKey).isPresent());
    }

    @Test
    public void testHasRight() {

        Assert.assertTrue(tree.hasRight(rootKey));
        Assert.assertTrue(tree.hasRight(leftKey));

        Assert.assertFalse(tree.hasRight(rightKey));
        Assert.assertFalse(tree.hasRight(leftLeftKey));
        Assert.assertFalse(tree.hasRight(leftRightKey));
        Assert.assertFalse(tree.hasRight(rightLeftKey));
    }

    @Test
    public void testGetRight() {

        Assert.assertEquals(rightKey, tree.getRight(rootKey).get());
        Assert.assertEquals(leftRightKey, tree.getRight(leftKey).get());

        // no right
        Assert.assertFalse(tree.getRight(rightKey).isPresent());
        Assert.assertFalse(tree.getRight(leftLeftKey).isPresent());
        Assert.assertFalse(tree.getRight(leftRightKey).isPresent());
        Assert.assertFalse(tree.getRight(rightLeftKey).isPresent());
    }
    
    @Test
    public void testIsLeft() {

        Assert.assertTrue(tree.isLeft(rootKey, leftKey));
        Assert.assertFalse(tree.isLeft(leftKey, rootKey));
        Assert.assertFalse(tree.isLeft(rootKey, rightKey));
        Assert.assertFalse(tree.isLeft(leftKey, leftKey));
        Assert.assertFalse(tree.isLeft(leftLeftKey, leftRightKey));
    }

    @Test
    public void testGetLeftest() {

        Assert.assertEquals(leftLeftKey, tree.getLeftest(rootKey).get());
        Assert.assertEquals(leftLeftKey, tree.getLeftest(leftKey).get());
        Assert.assertEquals(rightLeftKey, tree.getLeftest(rightKey).get());
        Assert.assertEquals(leftLeftKey, tree.getLeftest(leftLeftKey).get());
        Assert.assertEquals(rightLeftKey, tree.getLeftest(rightLeftKey).get());
    }

    @Test
    public void testGetLeftestLeaf() {

        // TODO - cannot perform proper test until I implement a sparse tree

        Assert.assertEquals(leftLeftKey, tree.getLeftestLeaf(rootKey).get());
        Assert.assertEquals(leftLeftKey, tree.getLeftestLeaf(leftKey).get());
        Assert.assertEquals(rightLeftKey, tree.getLeftestLeaf(rightKey).get());

        Assert.assertEquals(leftLeftKey, tree.getLeftestLeaf(leftLeftKey).get());
        Assert.assertEquals(rightLeftKey, tree.getLeftestLeaf(rightLeftKey).get());
    }

    @Test
    public void testGetParentFromLeft() {

        Assert.assertEquals(leftKey, tree.getParentFromLeft(leftLeftKey).get());
        Assert.assertEquals(rootKey, tree.getParentFromLeft(leftRightKey).get());
        Assert.assertEquals(rightKey, tree.getParentFromLeft(rightLeftKey).get());
        Assert.assertEquals(rootKey, tree.getParentFromLeft(leftKey).get());

        Assert.assertFalse(tree.getParentFromLeft(rootKey).isPresent());
        Assert.assertFalse(tree.getParentFromLeft(rightKey).isPresent());
    }

    @Test
    public void testGetParentRightFromLeft() {

        Assert.assertEquals(leftRightKey, tree.getParentRightFromLeft(leftLeftKey).get());
        Assert.assertEquals(rightKey, tree.getParentRightFromLeft(leftRightKey).get());
        Assert.assertEquals(rightKey, tree.getParentRightFromLeft(leftKey).get());

        Assert.assertFalse(tree.getParentRightFromLeft(rightLeftKey).isPresent());
        Assert.assertFalse(tree.getParentRightFromLeft(rootKey).isPresent());
        Assert.assertFalse(tree.getParentRightFromLeft(rightKey).isPresent());
    }
}