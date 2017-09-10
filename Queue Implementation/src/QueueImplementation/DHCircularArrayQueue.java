/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QueueImplementation;

/**
 *
 * @author Tony Mendoza
 */
public class DHCircularArrayQueue implements BareBonesQueue {
    private Object[] Q; // the reference to the actual Q
    private int front; // front of the Q
    private int rear; // back of the Q
    private int size; // # of elements in Q
    private int capacity = 5; // Default capacity
    private static int DEFAULT_CAPACITY = 5; // Default capacity

    // default Constructor
    public DHCircularArrayQueue() {
        this(DEFAULT_CAPACITY);
    }
    // one argument Constructor
    public DHCircularArrayQueue(int capacity) {
        this.capacity = capacity;
        Q = new Object[capacity]; // create the actual queue
        this.front = 0;
        this.rear = capacity - 1;
    }

    @Override
    public void offer(Object obj) {
        // this method adds an element to the queue
        // First check if there is space to add
        if (isFull()) {
            System.out.println("Queue is full. Reallocating...");
            reallocate();
        }
        size++; // Increase the total number of elements
        rear = (rear + 1) % capacity; // Update the value of rear
        Q[rear] = obj; // Add the element to the rear
    }

    @Override
    public Object poll() {
        // this method removes the first element from queue if there is any
        // Check if there are leemtns to be removed\
        if (isEmpty()) {
            System.out.println("The queue is empty");
            return null;
        }
        Object result = Q[front]; // take eleemnt from front
        front = (front + 1) % capacity;
        size--;
        return result;
    }
    
    protected void reallocate(){
        Object[] temp = new Object[this.capacity * 2];
        // Need to copy the old data into the new array
        for (int i = front, j =0; i < front + size; i++, j++)  {
            temp[j] = Q[i % this.capacity];
        }
        this.capacity *= 2; // Double the capacity for the new array
        this.Q = temp;
        this.front = 0;
        this.rear = size - 1;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean isFull() {
        // or is it (front + count == capacity); ???
        return (size == capacity);
    }

    @Override
    public Object peek() {
        // this method removes the first element from quue if there is any
        // Check if there are items to be removed.
        if (isEmpty()) {
            System.out.println("The queue is empty");
            return null;
        }
        return Q[front];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void display() {
        if(isEmpty()){
            System.out.println("Queue is Empty");
            return;
        }
        for (int i = front; i < front + size; i++) { // i < front + size
            System.out.print(Q[i % capacity]);
            if (i < front + size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
