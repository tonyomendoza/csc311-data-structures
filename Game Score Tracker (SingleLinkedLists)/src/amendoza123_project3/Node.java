package amendoza123_project3;

/**
 *
 * @author Tony Mendoza
 */
// Used for nodes'y stuff. Or for the linked list...
public class Node<E> {
    public E data; // Data for this node
    public Node<E> next; // the next node for this node (connecting)
    
    // Constructor to assign an object
    public Node(E data){
         this.data = data;
         this.next = null;
                 }
    
    // Constructor to assign an object and a connecting node
    public Node(E data, Node<E> next){
        this.data = data;
        this.next = next;
    }
}