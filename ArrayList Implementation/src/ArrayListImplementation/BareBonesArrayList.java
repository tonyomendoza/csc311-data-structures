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
public interface BareBonesArrayList<E> {
    public void add(E o);
    public void add(E o, int index);
    public void remove(int index);
    public Object get(int index);
    public void set(int index, E o);
    public int getSize();
    public int indexOf(E o);
    public void display();
}