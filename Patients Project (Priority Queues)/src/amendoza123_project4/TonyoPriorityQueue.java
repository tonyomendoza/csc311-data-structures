package amendoza123_project4;

import java.util.NoSuchElementException;

/**
 *
 * @author Tony Mendoza
 * This class is actually a container for a bunch of other classes, a super_array, if you will.
 * FYI, it sorts based on priority.
 */
public class TonyoPriorityQueue<E> {
    protected E[] heap; // The array that is used to store data
    protected int size; // Size
    protected int capacity; // capacity of the heap array
    private static final int INITIAL_CAPACITY = 32; // The default space allocated for the stack
    protected int bottom; // the last element

    // The default constructor initalizing some stuff.
    public TonyoPriorityQueue() {
        this.capacity = INITIAL_CAPACITY;
        this.size = 0;
        this.bottom = -1;
        this.heap = (E[]) new Object[this.capacity];
    }

    // The constructor with a defined capacity the array
    public TonyoPriorityQueue(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.bottom = -1;
        this.heap = (E[]) new Object[this.capacity];
    }

    // Adding data to the PriorityQueue by importing an array
    public TonyoPriorityQueue(Object[] dataSet) {
        this.capacity = INITIAL_CAPACITY;
        while (dataSet.length > this.capacity) {
            this.capacity *= 2;
        }

        this.size = 0;
        this.bottom = -1;
        this.heap = (E[]) new Object[this.capacity];
        for (int i = 0; i < dataSet.length; i++) {
            offer((E) dataSet[i]);
        }
    }

    // Offer as in, add
    public boolean offer(E item) {
        // Reallocate if the size is equal to capacity
        if (size == capacity) {
            reallocate();
            return false; // Try again
        }
        // Begin by inserting at the bottom
        bottom++;
        size++;
        heap[bottom] = item;

        // Compare parent's value
        int index = bottom;
        int parent = (index - 1) / 2;
          while (((Comparable) heap[index]).compareTo((Comparable) heap[parent]) < 0 && index > 0) {
            E temp = heap[parent];
            heap[parent] = heap[index];
            heap[index] = temp;
            index = parent;
            parent = (index - 1) / 2;
        }
        return true;
    }

    // Remove, as in remove. Throw an error if there is a problem
    public E remove() {
        if (size > 0) {
            E item = heap[0];
            remove_function();
            return item;
        } else {
            throw new NoSuchElementException();
        }
    }

    // Polling is like remove except no error is thrown
    public E poll() {
        if (size > 0) {
            E item = heap[0];
            remove_function();
            return item;
        } else {
            return null;
        }
    }

    // The function used to handle removing. It's quite impressive
    protected void remove_function() {
        // Assign the element at 0 a value at the bottom
        heap[0] = heap[bottom];
        // decrement size and bottom variables
        bottom--;
        size--;
        
        // Start at the parent
        int parent = 0;
        int leftIndex = (2 * parent) + 1;
        int rightIndex = leftIndex + 1;
        // Keep looping
        while (true) {
            // break the loop if there is no left child
            if (leftIndex >= size) {
                break;
            }
            // find the minimum index
            int minIndex = leftIndex;
            if (rightIndex < size && ((Comparable) heap[leftIndex]).compareTo(heap[rightIndex]) < 0) {
                minIndex = rightIndex;
            }
            // If the parent value is greater than the minium index value, then swap elements
            if (((Comparable) heap[parent]).compareTo(heap[minIndex]) > 0) {
                E temp = heap[parent];
                heap[parent] = heap[minIndex];
                heap[minIndex] = temp;
                parent = minIndex;
            } else { // break the loop otherwise
                break;
            }
        }
    }

    // Peeking is just looking at the first element without removing
    public E peek() {
        if (size > 0) {
            return heap[0];
        } else {
            return null;
        }
    }

    // Use this to return an element
    public E element() {
        if (size > 0) {
            return heap[0];
        } else {
            throw new NoSuchElementException();
        }
    }

    // Return the string value, which is all the elements together.
    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stb.append("[" + heap[i] + "]");
        }
        return stb.toString();
    }
    
    // Saved from my BareBones/DHArrayList class...
    // Allocating allows us to increase the stack's capacity.
    private void reallocate() {
        this.capacity *= 2; // Double the capacity for the new array
        E[] temp = (E[]) new Object[this.capacity];
        // Need to copy the old data into the new array
        for (int i = 0; i < heap.length; i++) {
            temp[i] = heap[i];
        }
        // Need to point the reference of the array to the correct data
        this.heap = temp;
    }

    // Return the "physical" size of the array
    public int getSize() {
        return size;
    }
}