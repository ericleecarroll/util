package com.mrsnottypants.util.collection;

import com.mrsnottypants.test.Exceptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

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

        rootKey = tree.getRoot();
        leftKey = tree.getLeft(rootKey);
        rightKey = tree.getRight(rootKey);
        leftLeftKey = tree.getLeft(leftKey);
        leftRightKey = tree.getRight(leftKey);
        rightLeftKey = tree.getLeft(rightKey);
    }

    @Test
    public void testSize() throws Exception {
        Assert.assertEquals(SOURCE.size(), tree.size());
    }

    @Test
    public void testAdd() throws Exception {

        BinaryTree<String> emptyTree = BinaryTreeArray.empty();
        Assert.assertEquals(0, emptyTree.size());

        for (String entry : SOURCE) {
            NodeKey key = emptyTree.add(entry);
            Assert.assertEquals(entry, emptyTree.get(key));
        }

        Assert.assertEquals(SOURCE.size(), emptyTree.size());
    }

    @Test
    public void testGet() throws Exception {

        Assert.assertEquals(SOURCE.get(0), tree.get(rootKey));
        Assert.assertEquals(SOURCE.get(1), tree.get(leftKey));
        Assert.assertEquals(SOURCE.get(2), tree.get(rightKey));
        Assert.assertEquals(SOURCE.get(3), tree.get(leftLeftKey));
        Assert.assertEquals(SOURCE.get(4), tree.get(leftRightKey));
        Assert.assertEquals(SOURCE.get(5), tree.get(rightLeftKey));
    }

    @Test
    public void testSwap() throws Exception {

        tree.swap(rootKey, rightKey);

        Assert.assertEquals(SOURCE.get(0), tree.get(rightKey));
        Assert.assertEquals(SOURCE.get(2), tree.get(rootKey));
        Assert.assertEquals(SOURCE.size(), tree.size());
    }

    @Test
    public void testGetRoot() throws Exception {

        Assert.assertEquals(SOURCE.get(0), tree.get(rootKey));

        // out of bounds
        tree = BinaryTreeArray.empty();
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::getRoot));
    }

    @Test
    public void testHasParent() throws Exception {

        Assert.assertTrue(tree.hasParent(leftKey));
        Assert.assertTrue(tree.hasParent(rightKey));
        Assert.assertTrue(tree.hasParent(leftLeftKey));
        Assert.assertTrue(tree.hasParent(leftRightKey));
        Assert.assertTrue(tree.hasParent(rightLeftKey));

        Assert.assertFalse(tree.hasParent(rootKey));
    }

    @Test
    public void testGetParent() throws Exception {

        Assert.assertEquals(rootKey, tree.getParent(leftKey));
        Assert.assertEquals(rootKey, tree.getParent(rightKey));
        Assert.assertEquals(leftKey, tree.getParent(leftLeftKey));
        Assert.assertEquals(leftKey, tree.getParent(leftRightKey));
        Assert.assertEquals(rightKey, tree.getParent(rightLeftKey));

        // illegal
        Assert.assertTrue(Exceptions.isIllegal(tree::getParent, rootKey));
    }

    @Test
    public void testHasLeft() throws Exception {

        Assert.assertTrue(tree.hasLeft(rootKey));
        Assert.assertTrue(tree.hasLeft(leftKey));
        Assert.assertTrue(tree.hasLeft(rightKey));

        Assert.assertFalse(tree.hasLeft(leftLeftKey));
        Assert.assertFalse(tree.hasLeft(leftRightKey));
        Assert.assertFalse(tree.hasLeft(rightLeftKey));
    }

    @Test
    public void testGetLeft() throws Exception {

        Assert.assertEquals(leftKey, tree.getLeft(rootKey));
        Assert.assertEquals(leftLeftKey, tree.getLeft(leftKey));
        Assert.assertEquals(rightLeftKey, tree.getLeft(rightKey));

        // illegal
        Assert.assertTrue(Exceptions.isIllegal(tree::getLeft, leftLeftKey));
        Assert.assertTrue(Exceptions.isIllegal(tree::getLeft, leftRightKey));
        Assert.assertTrue(Exceptions.isIllegal(tree::getLeft, rightLeftKey));
    }

    @Test
    public void testHasRight() throws Exception {

        Assert.assertTrue(tree.hasRight(rootKey));
        Assert.assertTrue(tree.hasRight(leftKey));

        Assert.assertFalse(tree.hasRight(rightKey));
        Assert.assertFalse(tree.hasRight(leftLeftKey));
        Assert.assertFalse(tree.hasRight(leftRightKey));
        Assert.assertFalse(tree.hasRight(rightLeftKey));
    }

    @Test
    public void testGetRight() throws Exception {

        Assert.assertEquals(rightKey, tree.getRight(rootKey));
        Assert.assertEquals(leftRightKey, tree.getRight(leftKey));

        // illegal
        Assert.assertTrue(Exceptions.isIllegal(tree::getRight, rightKey));
        Assert.assertTrue(Exceptions.isIllegal(tree::getRight, leftLeftKey));
        Assert.assertTrue(Exceptions.isIllegal(tree::getRight, leftRightKey));
        Assert.assertTrue(Exceptions.isIllegal(tree::getRight, rightLeftKey));
    }
}