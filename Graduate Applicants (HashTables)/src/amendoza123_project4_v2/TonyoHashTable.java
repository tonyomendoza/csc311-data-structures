package amendoza123_project4_v2;

/**
 *
 * @author Tony Mendoza
 */
// A hashtable that I tried my best to create. Can definetly be improved.
// I'm not that great a programmer yet. I can name at least few more better aka my professors. ;)
public class TonyoHashTable<K, V> {

    private TonyoArrayList<HashNode> entries; // stores entries
    private int capacity; // stores capacity of array list
    private int size;  // current size of array list

    // default Constrcutor
    public TonyoHashTable() {
        this.capacity = 13; // set the default capacity
        entries = new TonyoArrayList<>(capacity); // create a new list
        size = 0; // set size

        // Create empty elements
        for (int i = 0; i < capacity; i++) {
            entries.add(null);
        }
    }
    
    // Constrcutor, capacity parameter
    public TonyoHashTable(int capacity) {
        this.capacity = capacity; // set capacity
        entries = new TonyoArrayList<>(capacity); // create a new list
        size = 0; // set size

        // Create empty elements
        for (int i = 0; i < capacity; i++) {
            entries.add(null);
        }
    }

    // return whether or not the table is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // return the size of the table
    public int size() {
        return size;
    }

    // Finds an index for a given key
    private int getBucketIndex(K key) {
        int hashCode = key.hashCode(); // get the hashCode of the key
        int index = hashCode % capacity; // create an index by dividing the hashcode by capacity and returning the remainder
        return index;
    }

    // Adds a key value pair to hash
    public boolean put(K key, V value) {
        // Find the index
        int bucketIndex = getBucketIndex(key);

        HashNode entry = entries.get(bucketIndex); // Set a entry to see if a entry exists in the table
        // if the entry exists
        if (entry != null) {
            // If the key exists, then replace the value
            if (entry.getKey() == key) {
                entry.setValue(value);
                return false; // return that the key is not unique
            }
            // otherwise, linear probe by finding the next entry through iteration
            entry = entries.get(bucketIndex++); // Set a entry to see if a entry exists in the table 
            int firstIndex = getBucketIndex(key); // keep track of the original index
            while (entry != null && bucketIndex != firstIndex) { // loop until there is a null entry or we hit the original index
                if (entry.getKey() == key) { // if the key exists, then replace the value.
                entry.setValue(value);
                    return false; // return that the key is not unique
                }
                entry = entries.get(bucketIndex++); // Set a entry to see if a entry exists in the table 
                if(bucketIndex == capacity){ // If we reached the end of the array, then reset to 0
                    bucketIndex = 0;
                }
            }
            
            if(bucketIndex == firstIndex){ // if we looped to the index, then there is no more space.
                return true; // return that the key is "technically" unique
            }
        }

        entries.set(bucketIndex, new HashNode(key, value)); // create a new entry
        size++; // increment size
        return true; // the key is unique
    }

    public V get(K key) {
        // Find the index
        int bucketIndex = getBucketIndex(key);
        
        HashNode entry = entries.get(bucketIndex);  // Set a entry to see if a entry exists in the table
        // if entry does not exist, then return null
        if (entry == null) {
            return null;
        } else if (entry.key != key) { // else if the key is not equal to the entry's key
            int firstIndex = getBucketIndex(key); // keep track of the original index
            do { // linear probe to find the entry
                if(bucketIndex + 1 >= capacity){
                    bucketIndex = -1;
                }
                entry = entries.get(bucketIndex++); // set a entry to see if a entry exists in the table
                if (entry == null) { // if it is null, then it does not exist
                    return null;
                }
            } while (entry.key != key && bucketIndex != firstIndex); // if we looped to the index, then there is no more space.
            
            if(bucketIndex == firstIndex || entry.key != key){ // if there are no matching values, then it does not exists
                return null;
            }
        }
        
        return (V) entry.value; // return the value
    }

    public V remove(K key) {
        // Apply hash function to find index for a given key
        int bucketIndex = getBucketIndex(key);
        
        HashNode entry = entries.get(bucketIndex);  // Set a entry to see if a entry exists in the table
        // if entry does not exist, then return null
        if (entry == null) {
            bucketIndex = -1;
        } else if (entry.key != key) { // else if the key is not equal to the entry's key
            int firstIndex = getBucketIndex(key); // keep track of the original index
            do { // linear probe to find the entry
                entry = entries.get(bucketIndex++); // set a entry to see if a entry exists in the table
                if (entry == null) { // if it is null, then it does not exist
                    bucketIndex = -1;
                    break;
                }
            } while (entry.key != key && bucketIndex != firstIndex); // if we looped to the index, then there is no more space.
            
            if(bucketIndex == firstIndex || entry.key != key){ // if there are no matching values, then it does not exists
                bucketIndex = -1;
            }
        }
        
        if(bucketIndex == -1){ // if there is no index
            return null;
        }
        
        V temp = (V)entries.get(bucketIndex).value; // store the value of the soon to be removed item
        entries.set(bucketIndex, null); // set the returned item to null
        size--; // decrement size
        return temp; // return the value
    }
    
    // Return capacity of the table
    public int getCapacity(){
        return capacity;
    }
}