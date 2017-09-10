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
public class QueueDriver {
    public static void main(String[] args){
        CircularArrayQueue Q = new CircularArrayQueue();
        Q.offer("A");
        Q.offer("B");
        Q.display();
        Q.offer("C");
        Q.display();
        Q.pull();
        Q.display();
        Q.offer("D");
        Q.offer("E");
        Q.display();
        Q.offer("F");
        Q.display();
        Q.pull();
        Q.display();
        Q.offer("G");
        Q.offer("H");
        Q.display();
        Q.offer("I");
        Q.display();
        Q.pull();
        Q.display();
        
    }
}