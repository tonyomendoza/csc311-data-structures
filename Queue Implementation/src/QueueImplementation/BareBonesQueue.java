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
public interface BareBonesQueue {
    public void offer(Object obj); // adds elements to the Q
    public Object poll(); // remove eleents from the Q
    public boolean isEmpty(); // checks if there is any data
    public boolean isFull(); // checks if there is more space
    public int size(); // how many lements in Q
    public Object peek(); // returns the first element without removing it
    public void display(); // display the contents of the Q
    
}