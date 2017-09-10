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
public class Driver {
    public static void main(String[] args){
        DHCircularArrayQueue Q = new DHCircularArrayQueue();
        Q.offer("A");
        Q.offer("B");
        Q.display();
        Q.offer("C");
        Q.display();
        Q.poll();
        Q.display();
        Q.offer("D");
        Q.offer("E");
        Q.display();
        Q.offer("F");
        Q.display();
        Q.poll();
        Q.display();
        Q.offer("G");
        Q.offer("H");
        Q.display();
        Q.offer("I");
        Q.display();
        Q.poll();
        Q.display();
    }
}