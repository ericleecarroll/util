package com.mrsnottypants.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * To iterate the characters within a character sequence.
 *
 * Created by Eric on 7/27/2016.
 */
public class CharSequenceIterator implements Iterator<Character> {

    private final CharSequence source;
    private int at;

    /**
     * Construct character sequence iterator
     * @param source character sequence to iterate
     */
    public CharSequenceIterator(final CharSequence source) {
        this.source = (source != null) ? source : "";
        this.at = 0;
    }

    /**
     * Return true if we are not at the end of the character sequence
     * @return true if not at end
     */
    @Override
    public boolean hasNext() {
        return (at < source.length());
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Character next() {

        // confirm there is a next
        if (!hasNext()) { throw new NoSuchElementException("Past end of input"); }

        // return and increment
        return source.charAt(at++);
    }
}
