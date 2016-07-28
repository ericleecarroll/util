package com.mrsnottypants.util.iterator;

import static org.junit.Assert.*;

import com.mrsnottypants.test.Exceptions;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Eric on 7/27/2016.
 */
public class CharSequenceIteratorTest {

    @Test
    public void testIterator() {

        final String source = "abcd";
        Iterator<Character> iterator = new CharSequenceIterator(source);

        assertTrue(iterator.hasNext());
        assertEquals(Character.valueOf('a'), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(Character.valueOf('b'), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(Character.valueOf('c'), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(Character.valueOf('d'), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testNullString() {

        Iterator<Character> iterator = new CharSequenceIterator(null);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testNoSuchElement() {

        Iterator<Character> iterator = new CharSequenceIterator("");
        assertTrue(Exceptions.isExpected(iterator::next, NoSuchElementException.class));
    }
}