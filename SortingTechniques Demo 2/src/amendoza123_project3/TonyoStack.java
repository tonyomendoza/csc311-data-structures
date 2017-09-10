package amendoza123_project3;

/**
 *
 * @author Tony Mendoza
 */
// This is MY stack
public class TonyoSingleList<E> {
    private int size; /// How many elements in the TonyoStack currently present
    private int capacity; // Total space of the array
    private E[] myStack; // Used to hold the data
    private static final int INITIAL_CAPACITY = 10; // The default space allocated for the stack
    private int top; // Used to navigate through the stack.

    public TonyoSingleList() {
        this.capacity = INITIAL_CAPACITY; // defualt capacity
        this.size = 0; // No elements at the beginning
        this.top = -1; // default navigation index
        myStack = (E[]) new Object[this.capacity]; // initialize the stack with set capacity
    }

    public TonyoStack(int capacity) {
        this.capacity = capacity; // set ccapacticy
        this.size = 0; // No elements at the beginning
        this.top = -1; // default navigation index
        myStack = (E[]) new Object[this.capacity]; // initialize the stack with set capacity
    }

    // Push an element onto the top of the stack
    public void push(E object) {
        if (top == capacity - 1) {
            System.out.println("=> Stack Overflow. Reallocating for the sake of this project.");
            reallocate();
            push(object);
        }
        else{
            size++;
            top++;
            myStack[top] = object;
        }
    }
    
    // Pop an element from the top of the stack.
    // It removes it from the stack afterwards
    public E pop() {
        if (isEmpty()) {
            System.out.println("=> Stack underflow... woops.");
            return null;
        }
        else{
            top--;
            size--;
            return myStack[top + 1];
        }
    }

    // Return whether or not it is empty
    public boolean isEmpty(){
        return top == -1;
    }
    
    // Return the top object without removing it from the stack.
    public E peek(){
        if(isEmpty()){
            System.out.println("=> Nothing found on this stack, returning 'null'");
            return null;
        }
        else{
            return myStack[top];
        }
    }
    
    // Return the object at a given index
    public E get(int i){
        // Returns the data at the given index
        // Check if the index is valid or not
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("=> Invalid Index: " + i);
        } else {
            return myStack[i];
        }
    }

    /// this method displays the content of the stack
    public void display() {
        System.out.print("=> The contents of the list: ");
        for (int i = 0; i < size; i++) {
            System.out.print(myStack[i]);
            if (i < size - 1) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }
    
    // Search through the stack to see if it exists inside
    public int search(E object){
        for(int i = 0; i < size; i++)
            if(myStack[i] == object)
                return i; // Return its index in the stack
        return - 1; // Return -1 if it doesn't exist inside the stack
    }
    
    // Saved from my BareBones/DHArrayList class...
    // Allocating allows us to increase the stack's capacity.
    private void reallocate() {
        this.capacity *= 2; // Double the capacity for the new array
        E[] temp = (E[]) new Object[this.capacity];
        // Need to copy the old data into the new array
        for (int i = 0; i < myStack.length; i++) {
            temp[i] = myStack[i];
        }
        // Need to point the reference of the array to the correct data
        this.myStack = temp;
    }
    
    // Return the "physical" size of the array
    public int getSize() {
        return size;
    }
}