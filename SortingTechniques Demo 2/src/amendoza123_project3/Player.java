package amendoza123_project3;

/**
 * 
 * @author Tony Mendoza
 */
// This class holds data for client's account.
public class Player {
    protected String name; // Self-explanatory.
    protected int score; // Self-explanatory.

    // Default constructor for an account, initializes all variables.
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Load an account from a database entry.
    public static Player fromDatabaseEntry(String databaseEntry) {
        String[] data = databaseEntry.split(":: "); // Split the database entry string into a data array
        return fromDatabaseEntry(data); // Proceed to the next state.
    }

    // Load an account from a database entry using a given data array.
    public static Player fromDatabaseEntry(String[] data) {
        // Create an account based on the interpreted data
        return new Account(data[0], Integer.parseInt(data[3])); // Return the created account
    }
}