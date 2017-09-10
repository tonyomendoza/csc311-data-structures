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
public abstract interface Queue {
    public void offer(Object obj);
    public Object pull();
    public boolean isEmpty();
    public boolean isFull();
    public int size();
    public Object peek();
    public void display();
}