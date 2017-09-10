/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayListImplementation;

/**
 *
 * @author Tony Mendoza
 */
public class Driver {
    public static void main(String[] args){
        System.out.println("Welcome to DH Array List...");
        // Create the ArrayList object
        DHArrayList<Integer> myList = new DHArrayList<Integer>(3);
        // Insert and show the data
        myList.add(3);
        myList.display();
        myList.add(15);
        myList.display();
        myList.add(25);
        myList.display();
        myList.add(-5);
        myList.display();
        System.out.println(myList.get(2));
        System.out.println(myList.get(1));
        
        System.out.println();
        System.out.println("Testing added functions.");
        
        // Testing added functions
        myList.remove(3);
        myList.display();
        System.out.println("\nIndex of 25: " + myList.indexOf(25));
        myList.set(2, 1000);
        myList.display();
        System.out.println("\nSize of myList: " + myList.getSize());
        myList.add(69, 2);
        myList.display();
        System.out.println("\nSize of myList: " + myList.getSize());
        
    }
}
