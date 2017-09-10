package amendoza123_project3;

/**
 *
 * @author Tony Mendoza
 */
// This class holds data for client's account.
public class SavedScore {

    protected String name; // Self-explanatory.
    protected int score; // Self-explanatory.

    // Default constructor for an account, initializes all variables.
    public SavedScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Returns a string based on a certain format
    @Override
    public String toString() {
        return "Name: " + name + ", Score: " + score;
    }

    // Returns a string suitable for displaying scores
    public String toString(int spaces) {
        String zeroes = "";
        if (score < 10)
            zeroes = "00";
        else if (score < 100)
            zeroes = "0";
        if (spaces != name.length()) {
            String space = "";
            for (int i = 0; i < spaces - name.length(); i++) {
                space += ".";
            }
            return name + space + "... " + zeroes + score;
        }
        return name + "... " + zeroes + score;
    }

    // Returns the data in a formatted database entry
    public String toDatabaseEntry() {
        return name + ":: " + score;
    }

    // Load an account from a database entry.
    public static SavedScore fromDatabaseEntry(String databaseEntry) {
        String[] data = databaseEntry.split(":: "); // Split the database entry string into a data array
        return fromDatabaseEntry(data); // Proceed to the next state.
    }

    // Load an account from a database entry using a given data array.
    public static SavedScore fromDatabaseEntry(String[] data) {
        // Create an account based on the interpreted data
        return new SavedScore(data[0], Integer.parseInt(data[1])); // Return the created account
    }
}
