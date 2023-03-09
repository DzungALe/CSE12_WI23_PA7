/*
 * Name: Dung Le
 * PID: A16873166
 * Sources used: Oracle documentation, 
 * course lecture slides
 * 
 * This file is my implementation of MyMinHeap,
 * implementing the MinHeapInterface
 * It stores data using theQueue using an ArrayList
 * It has functions to swap, percolate up and down, 
 * and get parents/children of elements
 */

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is my implementation of MyMinHeap
 * It includes methods on how to sort a minHeap,
 * percolate an element up and down to adhere to 
 * a minHeap's requirements
 * it can also add and remove elements from the Heap as well
 * 
 * Instance variables 
 * data - ArrayList type that stores data
 */
public class MyMinHeap<E extends Comparable<E>> implements MinHeapInterface<E>
{
    /* 
     * Instance variables 
     * 
     * Arraylist data - uses 0-based indexing
     */
    protected ArrayList<E> data;

    /**
     * Constructor
     * Initializes data to new empty arraylist
     */
    public MyMinHeap()
    {
        data = new ArrayList<>();
    }

    /**
     * Constructor
     * 
     * @param collection - collection of data
     * 
     * Initializes data to collection
     * Throws NullPointerException if collection is null
     * OR if any element in collection is null
     * Percolates down 
     */
    public MyMinHeap(Collection<? extends E> collection)
    {
        if(collection == null){
            throw new NullPointerException();
        }

        this.data = new ArrayList<>(collection);

        //Traverses backward to percolate elements down
        //Throws exception if data point null
        for(int i = this.data.size() - 1; i >= 0; i--){
            if(data.get(i) == null){
                throw new NullPointerException();
            }
            percolateDown(i);
        }
    }

    /* ---------- Helper Methods ---------- */

    /**
     * Swap method 
     * 
     * @param from - index of first element to
     * @param to - index of second element to 
     */
    protected void swap(int from, int to)
    {
        E element = data.get(from);
        data.set(from, data.get(to));   //Set from element to to element
        data.set(to, element);          //Set to element to from element
    }

    /**
     * Get parent index 
     * 0-based indexing
     * 
     * @param index - index of child
     * 
     * @return index of parent
     */
    protected static int getParentIdx(int index)
    {
        final int DOUBLE = 2;
        return (index - 1) / DOUBLE;
    }

    /**
     * Get left child index
     * 0-based indexing
     * 
     * @param index - index of parent
     * 
     * @return index of left child
     */
    protected static int getLeftChildIdx(int index)
    {
        final int DOUBLE = 2;
        return (index + 1) * DOUBLE - 1;
    }

    /**
     * Get right child of index
     * 0-based indexing
     * 
     * @param index - index of parent
     * 
     * @return index of right child
     */
    protected static int getRightChildIdx(int index)
    {
        final int DOUBLE = 2;
        return(index  + 1) * DOUBLE;
    }

    /**
     * Get the lesser child's index
     * 0-based indexing 
     * 
     * @param index - index of parent
     * 
     * Instance variables:
     * LEFTCHILD - final index of left child
     * RIGHTCHILD - final index of right child
     * FINALIDX - final index of arrayList
     * 
     * @return index of lesser child, if any
     */
    protected int getMinChildIdx(int index)
    {
        /* Instance variabbles */
        final int LEFTCHILD = getLeftChildIdx(index);
        final int RIGHTCHILD = getRightChildIdx(index);
        final int FINALIDX = size() - 1;

        /*
         * Check to see if element is leaf
         * Leaf is when there is no left child
         * Therefore no right child since it's a heap
         * 
         * THEN,
         * Check if left is null. If left is null
         * Then right should also be null
         */
        if(LEFTCHILD > FINALIDX){
            return -1;
        }
        if(this.data.get(LEFTCHILD) == null){
            return -1;
        }

        /*
         * IF left is not null,
         * Check to see if right element exists
         * THEN check if right child is null
         * If:
         * rightchild's "index" is higher than last
         * index of arrayList OR
         * LEFTCHILD is not null and RIGHTCHILD is null
         */
        if(this.data.get(LEFTCHILD) != null){
            if(RIGHTCHILD > FINALIDX){
                return LEFTCHILD;
            }
            if(this.data.get(RIGHTCHILD) == null){
                return LEFTCHILD;
            }
        }
 
        /*
         * Return left child if left child bigger or equal
         * Return right child if right child bigger 
         */
        if(this.data.get(LEFTCHILD).compareTo(this.data.get(RIGHTCHILD)) <= 0){
            return LEFTCHILD;
        } else{
            return RIGHTCHILD;
        }   
    }

    /**
     * Percolate up method
     * Percolates in array to maintain minHeap property
     * Makes recursive call if conditions are not satisifed 
     * 
     * @param index - index of element
     * 
     * Instance variables:
     * PARENTINDEX - int value that stores the index of parent
     */
    protected void percolateUp(int index)
    {
        final int PARENTINDEX = getParentIdx(index);
        if(PARENTINDEX < 0){
            return;
        }
        if(this.data.get(index).compareTo(this.data.get(PARENTINDEX)) < 0){
            swap(index, PARENTINDEX);   //Swap parent index with child
            percolateUp(PARENTINDEX);   //Recursive call with PARENTINDEX
        }
    }

    /**
     * Percolate down method
     * Percolates in array to maintain minHeap property
     * Makes recursive call if condtions are not satisifed
     * 
     * @param index - index of element
     * 
     * Instance variables:
     * CHILDINDEX - index of smaller child
     */
    protected void percolateDown(int index)
    {
        final int CHILDINDEX = getMinChildIdx(index);
        if(CHILDINDEX == -1){
            return;
        }
        //If data is bigger than CHILDINDEX, then swap
        //Else, return
        if(this.data.get(index).compareTo(this.data.get(CHILDINDEX)) > 0){
            swap(index, CHILDINDEX);
            percolateDown(CHILDINDEX);
        } 
    }

    /**
     * Delete index method
     * Remove element at index and return it
     * 
     * @param index - index of element
     * 
     * Instance variables:
     * LASTINDEX - last index of element
     * deleteElem - value to be removed and returned
     * 
     * @return removed element
     */
    protected E deleteIndex(int index)
    {

        final int LASTINDEX = this.data.size() - 1;

        /*
         * Store current index
         * Swap index with final index
         * Remove value at final index
         * Percolate down until min heap is reached
         */
        E returnElem = this.data.get(index);
        swap(index, LASTINDEX);             
        this.data.remove(LASTINDEX);
        percolateDown(index);

        return returnElem;
    }

    /* ---------- Core Methods ---------- */

    /**
     * Insert method
     * 
     * @param element - element to be inserted
     * 
     * Throw NullPointerException if element is null
     * Add element to end of heap 
     * Percolate up if necessary
     * 
     * Instance variables:
     * FINALINDEX - index of final elem
     */
    public void insert(E element)
    {
        if(element == null){
            throw new NullPointerException();
        }

        this.data.add(element);

        //Add element to end of heap 
        //Percolate up 
        final int FINALINDEX = this.data.size() - 1;
        percolateUp(FINALINDEX);
    }

    /**
     * Get min method
     * 0-based indexing
     * 
     * @return root element, or null 
     */
    public E getMin()
    {
        if(this.data.size() == 0){
            return null;
        }
        return this.data.get(0);
    }

    /**
     * Remove method
     * 0-based indexing
     * 
     * Instance variables:
     * returnElem, stores root, if valid
     * 
     * @return removed element
     */
    public E remove()
    {
        if(this.data.size() == 0){
            return null;
        }
        E returnElem = this.data.get(0);
        deleteIndex(0);

        return returnElem;
    }

    /**
     * Get size method
     * 
     * @return number of elements in arrayList
     */
    public int size()
    {
        return this.data.size();
    }

    /**
     * Clear method
     * 
     * Clear entire list
     */
    public void clear()
    {
        while(this.size() > 0){
            remove();
        }
    }
}
