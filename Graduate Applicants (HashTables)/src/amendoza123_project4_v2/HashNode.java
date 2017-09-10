package amendoza123_project4_v2;

/**
 *
 * @author Tony Mendoza
 */
public class HashNode <K, V>{
    K key; // retrieve
    V value; // data accessed
    
    // Constructor
    public HashNode(K key, V value){
        this.key = key;
        this.value = value;
    }
    
    // get key stored in node
    public K getKey(){
        return key;
    }
    
    // set key stored in node 
    public void setKey(K key){
        this.key = key;
    }
    
    // get value stored in node
    public V getValue(){
        return value;
    }
    
    // set value stored in node
    public void setValue(V value){
        this.value = value;
    }
}