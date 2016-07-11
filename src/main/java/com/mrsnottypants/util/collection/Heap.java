package com.mrsnottypants.util.collection;

/**
 * A heap - maximum value is stored at the root.
 *
 * Created by Eric on 7/3/2016.
 */
public interface Heap<E> {

    /**
     * Pop the maximum value off of the heap
     * @return maximum value
     */
    E pop();

    /**
     * Push a value into the heap
     * @param value value to push
     */
    void push(E value);
}
