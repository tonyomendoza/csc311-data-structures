 package amendoza123_project3;

/**
 *
 * @author Tony Mendoza
 */
// This is MY SingleList
public class TonyoSingleLinkedList<E> {
    private Node<E> head = null; // The beginning of the SingleList
    private int size; /// How many elements in the TonyoSingleLinkedList currently present

    public TonyoSingleLinkedList() {
    }

    // Add element to the front of the list
    public void addFirst(E item) {
        head = new Node<>(item, head);
        size++;
    }
    
    // Remove element from the front of the list
    public E removeFirst(){
        // Assign the head to a temporary variable
        Node<E> temp = head;
        if(head != null){
            head = head.next;
        }
        // If there is an element left, then decrease the size and return the head. Otherwise, return null.
        if(temp != null){
            size--;
            return temp.data;
        }else{
            return null;
        }
    }

    // Add element after the referenced node
    public void addAfter(Node<E> node, E item) {
        node.next = new Node<>(item, node.next); // null ????
        size++;
    }

    // Remove the element after the referenced node
    public E removeAfter(Node<E> node) {
        // assign the next to the temporary node
        Node<E> temp = node.next;
        // If there is a node left, then add the rest of the nodes to the referenced node.
        // Return the temporary node
        if (temp != null) {
            node.next = temp.next;
            size--;
            return temp.data;
        } else {
            return null;
        }
    }
    
    // A string that returns the elements in a format suitable for linked lists
    @Override
    public String toString(){
        Node<String> nodeRef = (Node<String>) head;
        StringBuilder result = new StringBuilder();
        while (nodeRef != null){
            result.append(nodeRef.data);
            if (nodeRef.next != null){
                result.append(" ==> ");
            }
            nodeRef = nodeRef.next;
        }
        return result.toString();
    }
    
    // Return the node at the given index
    public Node<E> getNode(int index){
        Node<E> node = head;
        for(int i = 0; i < index && node != null; i++){
            node = node.next;
        }
        return node;
    }
    
    // Return the object at the given index
    public E get (int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        return node.data;
    }
    
    // Set the object at the given index.
    public E set(int index, E anEntry){
        if (index < 0 || index >= size){
            throw new
                IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        E result = node.data;
        node.data = anEntry;
        return result;
    }
    
    // Add the object at a given index
    public void add(int index, E item){
        if(index < 0 || index > size){
            throw new
                IndexOutOfBoundsException(Integer.toString(index));
        }
        if(index == 0){
            addFirst(item);
        }
        else{
            Node<E> node = getNode(index - 1);
            addAfter (node, item);
        }
    }
    
    // Add the object at the end
    public boolean add(E item){
        add(size, item);
        return true;
    }

    // Search through the  list to see if it exists inside
    public int indexOf(E object) {
        Node<E> nodeRef = head;
        int index = 0;
        while (nodeRef != null){
            if(nodeRef.data == object)
                return index;
            else
                index++;
            nodeRef = nodeRef.next; 
        }
        return - 1; // Return -1 if it doesn't exist inside the stack
    }

    // Return the "physical" size of the array
    public int getSize() {
        return size; 
    }
}