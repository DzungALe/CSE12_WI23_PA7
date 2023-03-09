/*
 * Name: Dung Le
 * PID: A16873166
 * Sources used: Oracle documentation, 
 * course lecture slides, StackOverflow
 * 
 * This file is my implementation of MyPriorityQueue,
 * It stores a heap with a MyMinHeap type
 * It has functions to push, pop, peek
 * get length, and clear the queue
 */

import java.util.Collection;

/**
 * This class is my implementation of PriorityQueue
 * It implements a Min Heap 
 * It can add, remove, peek elements 
 */
public class MyPriorityQueue<E extends Comparable<E>>
{
    /* Instance variables */
    protected MyMinHeap<E> heap;

    /**
     * Constructor
     * Initializes priority queue to a minHeap
     */
    public MyPriorityQueue() { this.heap = new MyMinHeap<>(); }

    /**
     * Constructor
     * @param collection - collection of data
     * 
     * Initializes heap to a minHeap with collection as data
     */
    public MyPriorityQueue(Collection<? extends E> collection) { 
        this.heap = new MyMinHeap<>(collection); 
    }

    /**
     * Push
     * 
     * @param element
     * 
     * Inserts element with proper Priority Queue implementation
     * Uses percolation with the insert() method
     */
    public void push(E element) { this.heap.insert(element); }

    public E peek() { return this.heap.getMin(); }

    public E pop() { return this.heap.remove(); }

    public int getLength() { return this.heap.size(); }

    public void clear() { this.heap.clear(); }
}
