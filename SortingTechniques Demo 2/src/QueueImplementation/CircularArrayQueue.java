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
public class CircularArrayQueue implements Queue {

    private Object[] Q;
    private int front;
    private int rear;
    private int count;
    private int capacity = 5;

    // Constructor
    public CircularArrayQueue() {
        Q = new Object[capacity];
    }

    @Override
    public void offer(Object obj) {
        if (isFull()) {
            System.out.println("Queue is full. Reallocating...");
            reallocate();
            offer(obj);
            return;
        }
        Q[rear] = obj; // Add the element to the rear
        rear = (rear + 1) % capacity; // Updte the value of rear
        count++; // Increase the total number of elements
    }

    @Override
    public Object pull() {
        // this method removes the first element from queue if there is any
        // Check if there are leemtns to be removed\
        if (isEmpty()) {
            System.out.println("The queue is empty");
            return null;
        }
        Object result = Q[front];
        front = (front + 1) % capacity;
        count--;
        return result;
    }
    
    protected void reallocate(){
        Object[] temp = new Object[this.capacity * 2];
        // Need to copy the old data into the new array
        for (int i = front, j =0; i < front + count; i++, j++)  {
            temp[j] = Q[i % this.capacity];
        }
        this.capacity *= 2; // Double the capacity for the new array
        this.Q = temp;
        front = 0;
        rear = count;
    }

    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    @Override
    public boolean isFull() {
        // or is it (front + count == capacity); ???
        return (count == capacity);
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
        return this.count;
    }

    @Override
    public void display() {
        if(isEmpty()){
            System.out.println("Queue is Empty");
            return;
        }
        for (int i = front; i < front + count; i++) {
            System.out.print(Q[i % capacity]);
            if (i < front + count - 1) {
                System.out.print(", ");
            };
        }
        System.out.println();
    }

}
