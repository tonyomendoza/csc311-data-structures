package amendoza123_project3_v2;

/**
 *
 * @author Tony Mendoza
 */
// This class holds data for a saved score.
public class SavedScore {
    public static String[] PlaceSuffix = new String[]{ "st", "nd", "rd", "th" };
    protected String name; // The player's name
    protected int score; // The player's score
    protected int rounds; // the rounds the game lasted
    protected int place; // the place the player scored

    // Default constructor for an account, initializes all variables.
    public SavedScore(String name, int score, int rounds, int place) {
        this.name = name;
        this.score = score;
        this.rounds = rounds;
        this.place = place;
    }

    // Returns a string based on a certain format
    @Override
    public String toString() {
        return "Name: " + name + ", Score: " + score;
    }

    // Returns a string suitable for displaying scores
    public String toString(int spaces) {
        String zeroes = "";
        String trail = "";
        if (score < 10)
            zeroes = "00";
        else if (score < 100)
            zeroes = "0";
        if (spaces != name.length()) {
            String space = "";
            for (int i = 0; i < spaces - name.length(); i++) {
                space += ".";
            }
            return name + space + "... " + zeroes + score+ ", " + trail + rounds + " rounds " + place + "" + PlaceSuffix[place - 1] + " place.";
        }
        return name + "... " + zeroes + score + ", " + trail + rounds + " rounds " + place + "" + PlaceSuffix[place - 1] + " place.";
    }

    // Returns the data in a formatted database entry
    public String toDatabaseEntry() {
        return name + ":: " + score + ":: " + rounds + ":: " + place;
    }

    // Load an saved score from a database entry.
    public static SavedScore fromDatabaseEntry(String databaseEntry) {
        String[] data = databaseEntry.split(":: "); // Split the database entry string into a data array
        return fromDatabaseEntry(data); // Proceed to the next state.
    }

    // Load a saved score from a database entry using a given data array.
    public static SavedScore fromDatabaseEntry(String[] data) {
        // Create a saved score based on the interpreted data
        return new SavedScore(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])); // Return the created account
    }
    
    // Return the score
    public int getScore(){
        return score;
    }
    // Return the where the player placed in a game
    public int getPlace(){
        return place;
    }
    
    // Return the rounds the player took
    public int getRounds(){
        return rounds;
    }
    
    
    //@Override
    // Compare by score, then by place, then by rounds
    public int compareTo(SavedScore s) {
        if (this.getScore() < s.getScore()) {
            return -1;
        }
        if (this.getScore() > s.getScore()) {
            return 1;
        }
        if (this.getPlace()< s.getPlace()) {
            return -1;
        }
        if (this.getPlace() > s.getPlace()) {
            return 1;
        }
        if (this.getRounds()< s.getRounds()) {
            return -1;
        }
        if (this.getRounds() > s.getRounds()) {
            return 1;
        }
        return 0;
    }
}