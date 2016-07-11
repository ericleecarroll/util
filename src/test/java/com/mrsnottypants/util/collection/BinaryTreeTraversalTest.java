package com.mrsnottypants.util.collection;

import com.mrsnottypants.test.Exceptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Eric on 7/6/2016.
 */
public class BinaryTreeTraversalTest {

    private BinaryTree<Character> tree;

    @Before
    public void before() {
        tree = BinaryTreeArray.treeOf(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'));
    }

    @Test
    public void testPreOrder() {

        final List<Character> expected = Arrays.asList('a', 'b', 'd', 'h', 'i', 'e', 'j', 'c', 'f', 'g');

        // get iterator
        Iterator<Character> iterator = BinaryTreeTraversal.PRE_ORDER.iteratorFor(tree);
        for (Character c : expected) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertEquals(c, iterator.next());
        }
        Assert.assertFalse(iterator.hasNext());

        // iterate off end
        Assert.assertTrue(Exceptions.isIllegalState(iterator::next));
    }

    @Test
    public void testInOrder() {

        final List<Character> expected = Arrays.asList('h', 'd', 'i', 'b', 'j', 'e', 'a', 'f', 'c', 'g');

        // get iterator
        Iterator<Character> iterator = BinaryTreeTraversal.IN_ORDER.iteratorFor(tree);
        for (Character c : expected) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertEquals(c, iterator.next());
        }
        Assert.assertFalse(iterator.hasNext());

        // iterate off end
        Assert.assertTrue(Exceptions.isIllegalState(iterator::next));
    }

    @Test
    public void testPostOrder() throws Exception {

        final List<Character> expected = Arrays.asList('h', 'i', 'd', 'j', 'e', 'b', 'f', 'g', 'c', 'a');

        // get iterator
        Iterator<Character> iterator = BinaryTreeTraversal.POST_ORDER.iteratorFor(tree);
        for (Character c : expected) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertEquals(c, iterator.next());
        }
        Assert.assertFalse(iterator.hasNext());

        // iterate off end
        Assert.assertTrue(Exceptions.isIllegalState(iterator::next));
    }

    @Test
    public void testLevelOrder() throws Exception {

        final List<Character> expected = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');

        // get iterator
        Iterator<Character> iterator = BinaryTreeTraversal.LEVEL_ORDER.iteratorFor(tree);
        for (Character c : expected) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertEquals(c, iterator.next());
        }
        Assert.assertFalse(iterator.hasNext());

        // iterate off end
        Assert.assertTrue(Exceptions.isIllegalState(iterator::next));
    }
}