package com.mrsnottypants.util.collection;

import com.mrsnottypants.test.Exceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Eric on 7/11/2016.
 */
public class BinaryTreeArrayHeapTest {

    private final List<Integer> SOURCE = Arrays.asList(10, 18, 20, 8, 2, 16, 14, 12, 4, 6);

    Heap<Integer> heap;

    @Before
    public void before() {
        heap = BinaryTreeArray.heapOf(SOURCE);
    }

    @Test
    public void testPop() {

        // order the SOURCE
        List<Integer> inOrder = new ArrayList<>(SOURCE);
        Collections.sort(inOrder);
        Collections.reverse(inOrder);

        // confirm we're popping in order
        for (Integer i : inOrder) {
            Integer fromHeap = heap.pop();
            Assert.assertEquals(i, fromHeap);
        }

        // confirm heap is empty
        Assert.assertTrue(Exceptions.isIllegalState(heap::pop));
    }

    @Test
    public void testPush() {

        // start with an empty heap and push
        heap = BinaryTreeArray.emptyHeap();
        for (Integer i : SOURCE) {
            heap.push(i);
        }

        // order the SOURCE
        List<Integer> inOrder = new ArrayList<>(SOURCE);
        Collections.sort(inOrder);
        Collections.reverse(inOrder);

        // confirm we're popping in order
        for (Integer i : inOrder) {
            Integer fromHeap = heap.pop();
            Assert.assertEquals(i, fromHeap);
        }

        // confirm heap is empty
        Assert.assertTrue(Exceptions.isIllegalState(heap::pop));
    }
}
