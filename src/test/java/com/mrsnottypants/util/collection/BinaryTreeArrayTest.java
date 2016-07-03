package com.mrsnottypants.util.collection;

import com.mrsnottypants.test.Exceptions;

import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Eric on 7/3/2016.
 */
public class BinaryTreeArrayTest {

    private static final List<String> SOURCE = Arrays.asList("a", "b", "c", "d", "e", "f");

    @Test
    public void testSize() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.of(SOURCE);

        Assert.assertEquals(SOURCE.size(), tree.size());
    }

    @Test
    public void testAdd() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.empty();
        Assert.assertEquals(0, tree.size());

        for (int j = 0 ; j < SOURCE.size() ; j++) {
            Assert.assertEquals(j, tree.add(SOURCE.get(j)));
            Assert.assertEquals(SOURCE.get(j), tree.get(j));
            Assert.assertEquals(j + 1, tree.size());
        }
    }

    @Test
    public void testGet() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.of(SOURCE);

        for (int j = 0 ; j < SOURCE.size() ; j++) {
            Assert.assertEquals(SOURCE.get(j), tree.get(j));
        }

        // out of bounds
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::get, -1));
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::get, SOURCE.size()));
    }

    @Test
    public void testSwap() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.of(SOURCE);
        tree.swap(0, 2);

        Assert.assertEquals(SOURCE.get(0), tree.get(2));
        Assert.assertEquals(SOURCE.get(2), tree.get(0));
        Assert.assertEquals(SOURCE.size(), tree.size());

        // out of bounds
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::swap, -1, 0));
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::swap, 0, SOURCE.size()));
    }

    @Test
    public void testGetRoot() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.of(SOURCE);

        Assert.assertEquals(0, tree.getRoot());

        // out of bounds
        tree = BinaryTreeArray.empty();
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::getRoot));
    }

    @Test
    public void testHasParent() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.of(SOURCE);

        Assert.assertTrue(tree.hasParent(1));
        Assert.assertTrue(tree.hasParent(2));
        Assert.assertTrue(tree.hasParent(3));
        Assert.assertTrue(tree.hasParent(4));
        Assert.assertTrue(tree.hasParent(5));

        Assert.assertFalse(tree.hasParent(tree.getRoot()));

        // out of bounds
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::hasParent, -1));
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::hasParent, SOURCE.size()));
    }

    @Test
    public void testGetParent() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.of(SOURCE);

        Assert.assertEquals(0, tree.getParent(1));
        Assert.assertEquals(0, tree.getParent(2));
        Assert.assertEquals(1, tree.getParent(3));
        Assert.assertEquals(1, tree.getParent(4));
        Assert.assertEquals(2, tree.getParent(5));

        // out of bounds
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::getParent, -1));
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::getParent, SOURCE.size()));

        // illegal
        Assert.assertTrue(Exceptions.isIllegal(tree::getParent, 0));
    }

    @Test
    public void testHasLeft() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.of(SOURCE);

        Assert.assertTrue(tree.hasLeft(0));
        Assert.assertTrue(tree.hasLeft(1));
        Assert.assertTrue(tree.hasLeft(2));

        Assert.assertFalse(tree.hasLeft(3));
        Assert.assertFalse(tree.hasLeft(4));
        Assert.assertFalse(tree.hasLeft(5));

        // out of bounds
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::hasLeft, -1));
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::hasLeft, SOURCE.size()));
    }

    @Test
    public void testGetLeft() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.of(SOURCE);

        Assert.assertEquals(1, tree.getLeft(0));
        Assert.assertEquals(3, tree.getLeft(1));
        Assert.assertEquals(5, tree.getLeft(2));

        // out of bounds
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::getLeft, -1));
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::getLeft, SOURCE.size()));

        // illegal
        Assert.assertTrue(Exceptions.isIllegal(tree::getLeft, 3));
        Assert.assertTrue(Exceptions.isIllegal(tree::getLeft, 4));
        Assert.assertTrue(Exceptions.isIllegal(tree::getLeft, 5));
    }

    @Test
    public void testHasRight() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.of(SOURCE);

        Assert.assertTrue(tree.hasRight(0));
        Assert.assertTrue(tree.hasRight(1));

        Assert.assertFalse(tree.hasRight(2));
        Assert.assertFalse(tree.hasRight(3));
        Assert.assertFalse(tree.hasRight(4));
        Assert.assertFalse(tree.hasRight(5));

        // out of bounds
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::hasRight, -1));
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::hasRight, SOURCE.size()));
    }

    @Test
    public void testGetRight() throws Exception {

        BinaryTreeArray<String> tree = BinaryTreeArray.of(SOURCE);

        Assert.assertEquals(2, tree.getRight(0));
        Assert.assertEquals(4, tree.getRight(1));

        // out of bounds
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::getRight, -1));
        Assert.assertTrue(Exceptions.isOutOfBounds(tree::getRight, SOURCE.size()));

        // illegal
        Assert.assertTrue(Exceptions.isIllegal(tree::getRight, 2));
        Assert.assertTrue(Exceptions.isIllegal(tree::getRight, 3));
        Assert.assertTrue(Exceptions.isIllegal(tree::getRight, 4));
        Assert.assertTrue(Exceptions.isIllegal(tree::getRight, 5));
    }
}