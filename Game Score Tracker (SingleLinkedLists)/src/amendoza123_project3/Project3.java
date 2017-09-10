// The package, not delievered by FedEx or UPS or USPS
package amendoza123_project3;

// Necessary imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Tony Mendoza NOTE: 'Player' and 'User' are used interchangeably.
 */
public class Project3 {

    // This TonyoSingleLinkedList is the backbone of the application. It stores accounts storing other TonoyStacks!
    static TonyoSingleLinkedList<SavedScore> scores;
    static int longestName = 0;
    // Boring stuff... but essential! They are used for quickly changing the app.
    // Do I have to comment further (or at all) on the variables? They are self-explanatory.
    static String filename; // Where we will load the database
    static boolean databaseLoaded; // Whether or not the database has been loaded
    static boolean sorted; // I know how an alternative to improve the efficency of this code, but I'd rather do this.

    /**
     * @param args the command line arguments
     */
    // main(...) runs the program.
    // ???/20 lines {Brackets, whitespace, and comments don't count}
    public static void main(String[] args) throws IOException {
        // Try to use the System's input for the interactive feature of this applicaiton.
        //try (Scanner input = new Scanner(System.in)) {
        Scanner input = new Scanner(System.in);
        scores = new TonyoSingleLinkedList<>(); // Initialize the TonoySingleLinkedList to hold the scores.
        filename = System.getProperty("user.dir") + "\\database.txt"; // Set filename to where the program is running
        databaseLoaded = sorted = false; // The database has not been loaded, nor the list sorted

        // Welcome the user.
        System.out.println("=> Welcome to the bestest Game App Ever!\n=>"
                + "Pac-Man can eat a lemon for all I care! (See what I did there?)");

        // Loads the database for the user.
        LoadDatabase();

        // Handle what the user inputs. This serves as the loop of the program.
        ProcessInput(input, scores);

        // Close the input.
        input.close();

        // Tell the user goodbye.
        System.out.println("=> Game finished. Have a nice day!");
        /*} catch (Exception e) {
            // If something happened, print the error message.
            System.out.println("=> Something went wrong: " + e.getMessage());
        }*/
    }

    // Handles the input of the user. This is where all the action of the program begins.
    static void ProcessInput(Scanner input, TonyoSingleLinkedList<SavedScore> accounts) throws IOException {
        // Handle input as long as the user has not input [6] to shut down.
        String command = "";
        do {
            // Give the user their options. They have six options to choose from.
            System.out.println("=> Available Commands:");
            System.out.println("\t=> [1] Play Game"); // Option [1] plays the game.
            System.out.println("\t=> [2] Display Leaderboard"); // Option [2] displays the leaderboard.
            System.out.println("\t=> [3] Search Scores"); // Option [3] searches through the scores for a specific player.
            System.out.println("\t=> [4] Delete Scores"); // Option [4] deletes scores for a specific player.
            System.out.println("\t=> [5] Quit Game"); // Option [5] shuts the program down.

            // The following reads what the user has input for a command.
            // They will proceed to the next state to process their next input.
            command = input.nextLine().trim();
            if (command.equals("1")) {
                PlayGameProcess(input);
            } else if (command.equals("2")) {
                DisplayLeaderboardProcess();
            } else if (command.equals("3")) {
                SearchScoresProcess(input);
            } else if (command.equals("4")) {
                DeleteScoresProcess(input);
            } else if (command.equals("5")) {
                ShutDownProcess();
            } else {
                // Let the user know the application could not interperet the input.
                System.out.println("=> Incorrect input.");
            }
            if (!command.equals("5")) {
                System.out.println();
                System.out.print("=> Enter anything to continue.");
                input.nextLine();
            }
        } while (!command.equals("5"));
    }

    // Process the gameplay.
    static void PlayGameProcess(Scanner input) throws IOException {
        // Assign the name based on user input
        String name = GetName(input);

        System.out.println("");

        // Add the score and let hte player know the score has been added
        SavedScore score = new SavedScore(name, RandomizeScore());
        scores.add(score);
        System.out.println("=> Added: " + score.toString());

        // Used for formatting the name on the displayboard
        if (longestName < name.length()) {
            longestName = name.length();
        }

        // The list becomes unsorted after a new element is added to the list
        sorted = false;

        // Try using the BufferedWriter to write to the database.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            if (databaseLoaded) {
                bw.newLine();
            }
            bw.write(score.toDatabaseEntry()); // Add the score to the database.
            databaseLoaded = true; // If a database has been created, the database is technically loaded.
        } catch (Exception e) {
            // Most likely something happened with the BufferedWriter, let the user know that.
            System.out.println("=> An error while saving the database occured: " + e.getMessage());
        }
    }

    // Randomize the score
    static int RandomizeScore() {
        return (int) (Math.random() * 100);
    }

    // Return the name of the Player based on user input
    static String GetName(Scanner input) {
        System.out.println("=> Please input name.");
        String name = input.nextLine();
        return name;
    }

    // Process searching for scores
    static void SearchScoresProcess(Scanner input) {
        // Prompt the user for the name of the player then assign it.
        System.out.println("=> Type the name of the player whose scores you want to see: ");
        String name = input.nextLine();

        // First, sort the score if it has been unsorted.
        if (sorted == false) {
            SortScores(scores);
            sorted = true;
        }

        // Create a new SingleLinkedList (Do not add or remove elements)
        TonyoSingleLinkedList<SavedScore> searchedScores = SearchScores(name);

        // If there are no matches, then let the player know there are none.
        if (searchedScores.getSize() == 0) {
            System.out.println("=> No players found by that name.");
        } else // Let the user know we begun to print the call history.
        { // Otherwise, display all the scores for the player.
            System.out.println("=> Player: " + name);
            // Loop through to display all the player's score
            for (int s = 0; s < searchedScores.getSize(); s++) {
                System.out.println("\t=> " + searchedScores.get(s).toString(longestName));
            }
        }
    }

    // Sort the scores of the player using "BubbleSort"
    static void SortScores(TonyoSingleLinkedList<SavedScore> targetScores) {
        // Loop for every element
        for (int i = 0; i < scores.getSize() - 1; i++) {
            // Loop for almost element again. It gets smaller because the higher are going to the top.
            for (int j = 0; j < scores.getSize() - 1 - i; j++) {
                // Assign the current node
                Node<SavedScore> node = scores.getNode(j);
                // If there is an ext node and the next node's value is greater than the current node's value
                if (node.next != null && node.data.score < node.next.data.score) {
                    // Then remove the next node, keep its value, and put it behind the current node.
                    SavedScore nextScore = scores.removeAfter(node);
                    scores.add(j, nextScore);
                }
            }
        }
    }

    // search for a score based on the player's name.
    static TonyoSingleLinkedList<SavedScore> SearchScores(String name) {
        // Create the results list and get its head
        TonyoSingleLinkedList<SavedScore> result = new TonyoSingleLinkedList<>();
        Node<SavedScore> nodeRef = scores.getNode(0);
        // Loop through the list to find matching scores, as long as there is a next node.
        while (nodeRef != null) {
            // When a match is found, add it to the results list.
            if (nodeRef.data.name.equals(name)) {
                result.add(nodeRef.data);
            }
            nodeRef = nodeRef.next;
        }
        return result;
    }

    // Delete Player's Scores
    static void DeleteScoresProcess(Scanner input) {
        // Prompt the user for the name they want to delete then assign it.
        System.out.println("=> Type the name of the player whose scores you want to delete: ");
        String name = input.nextLine();

        int index = 0;
        Node<SavedScore> nodeRef = scores.getNode(0);
        boolean deleted = false;
        // Loop to delete nodes, as long as there is a next node.
        while (nodeRef != null) {
            // If the matching node is first, then simply delete the first one in the list.
            if (nodeRef.data.name.equals(name) && index == 0) {
                scores.removeFirst();
                deleted = true;
            } else if (nodeRef.next != null && nodeRef.next.data.name.equals(name)) {
                // Otherwise, delete the matching scores when the next node's value is matching
                scores.removeAfter(nodeRef);
                deleted = true;
            } else {
                // iterate
                index++;
            }
            nodeRef = scores.getNode(index);
        }

        if (deleted) {
            // Try using the BufferedWriter to write to the database.
            // Requires rewriting the database to reflect changes
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) {
                for (int i = 0; i < scores.getSize(); i++) {
                    bw.write(scores.get(i).toDatabaseEntry()); // Add the account to the database.
                    if (i < scores.getSize() - 1) {
                        bw.newLine();
                    }
                }
            } catch (Exception e) {
                // Most likely something happened with the BufferedWriter, let the user know that.
                System.out.println("=> An error while saving the database occured: " + e.getMessage());
            }

            System.out.println(
                    "=> Player: \"" + name + "\" deleted from the database.");
        } else {
            System.out.println("=> No players found by that name.");
        }
    }

    // Display the leaderboards.
    static void DisplayLeaderboardProcess() {
        System.out.println("=> Leaderboard: Top 10");

        // First sort if unsorted.
        if (sorted == false) {
            SortScores(scores);
            sorted = true;
        }

        // The length for looping is the size of the scores is less than 10, otherwise, it is just 10.
        int length = scores.getSize();
        if (length > 10) {
            length = 10;
        }
        // Loop through the leaderboard, printing each score in the process.
        for (int s = 0; s < length; s++) {
            System.out.println("\t=> " + scores.get(s).toString(longestName));
        }
    }

    // Shut the program down.
    static void ShutDownProcess() throws IOException {
        // Data is saved after every data entry, just let the client know there is nothing to worry about.
        System.out.println("=> Data has already been saved. Shutting down...");
    }

    // Load the database
    static void LoadDatabase() throws IOException {
        // Try using the BufferedReader to read the file containing the database.
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Let the user know it is reading the file.
            System.out.println("=> Reading the file...");
            // Do the following while the BufferedReader has lines (data entries) to read.
            while ((line = br.readLine()) != null) {
                // First, split the data entry to a data array.
                String[] data = line.split(":: ");
                if (data.length == 2) { // A data entry must be at least of length 2...
                    scores.add(SavedScore.fromDatabaseEntry(data)); // Add a score onto the list using the data. 

                    if (longestName < data[0].length()) {
                        longestName = data[0].length();
                    }
                }
            }
            // Let the user know the database has been loaded.
            System.out.println("=> The database has been loaded at: " + filename);
            databaseLoaded = true; // The database has been loaded, so set boolean value to true.
        } catch (FileNotFoundException e) {
            System.out.println("=> Database not found. To create a new database, please register a new user: " + e.getMessage());
        } catch (Exception e) {
            // Most likely something happened with the bufferereader, let the user know that.
            System.out.println("=> An error while loading the database occured: " + e.getMessage());
        }
    }
}
