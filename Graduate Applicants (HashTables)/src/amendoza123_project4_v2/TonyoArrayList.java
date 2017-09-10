package amendoza123_project4_v2;

/**
 *
 * @author Tony Mendoza
 */
public class TonyoArrayList<E> {

    private int size; /// How many elements in the ArrayList currently present
    private int capacity; // Total space of the array
    private E[] myArray;
    private static final int INITIAL_CAPACITY = 10;

    // Overloaded constructor to create ArrayList of variable size
    public TonyoArrayList() {
        this.capacity = INITIAL_CAPACITY; // defualt capacity
        this.size = 0; // No elements at the beginning
        myArray = (E[]) new Object[this.capacity];
    }

    // Constructor with defined capacity
    public TonyoArrayList(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        myArray = (E[]) new Object[this.capacity];
    }

    // Add an object to an arraylist
    public void add(E o) {
        if (size >= capacity) {
            System.out.println("\nNot enough space. Reallocate Called.");
            this.reallocate();
        }
        myArray[size] = o;
        size++;
    }

    public void reallocate() {
        this.capacity *= 2; // Double the capacity for the new array
        E[] temp = (E[]) new Object[this.capacity];
        // Need to copy the old data into the new array
        for (int i = 0; i < myArray.length; i++) {
            temp[i] = myArray[i];
        }
        // Need to point the reference of the array to the correct data
        this.myArray = temp;
    }

    public void add(E o, int index) {
        /// This method adds the data at the given point of the ArrayList
        if (size >= capacity) {
            System.out.println("\nNot enough space. Reallocate Called.");
            this.reallocate();
        }

        E[] temp = (E[]) new Object[this.capacity];
        // Need to copy the old data into the new array
        for (int i = 0; i < myArray.length; i++) {
            if (i < index) {
                temp[i] = myArray[i];
            } else if (i > index && i - 1 < size) {
                temp[i] = myArray[i - 1];
            } else {
                temp[i] = o;
            }
        }
        this.myArray = temp;
        size++;
    }

    public E remove(int index) {
        // This method removes data from the array
        // Check if the index is valid or not
        if (index < 0 || index >= size) {
            System.out.println("Invalid Index: " + index);
            return null;
        } else {
            E temp = myArray[index];
            // Need to copy the old data into the new array
            for (int i = index; i < size - 1; i++) {
                myArray[i - 1] = myArray[i];
            }
            size--;
            return temp;
        }
    }

    public E get(int index) {
        // Returns the data at the given index
        // Check if the index is valid or not
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index: " + index);
        } else {
            return myArray[index];
        }
    }

    public void set(int index, E o) {
        // Sets the data at the given index
        // Check if the index is valid or not
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index: " + index);
        } else {
            myArray[index] = o;
        }
    }

    public int getSize() {
        // Return the "physical" size of the array
        return size;
    }

    public int indexOf(E o) {
        // Loop through the array until the object is found, then return its index. 
        for (int i = 0; i < size; i++) {
            if (o.equals(myArray[i])) {
                return i;
            }
        }
        return -1; // return -1 if not found
    }

    public void display() {
        /// this method displays the content of hte ArrayList
        System.out.print("Displaying list: ");
        for (int i = 0; i < size; i++) {
            System.out.print(myArray[i]);
            if (i < size - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("\n");
    }
}
