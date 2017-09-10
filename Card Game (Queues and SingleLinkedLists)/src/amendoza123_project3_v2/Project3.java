// The package, not delievered by FedEx or UPS or USPS
package amendoza123_project3_v2;

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
 *
 * Project 3 borrows from last semester's data structure's assignment. So, the
 * scoreboard is greatly improved and included.
 */
public class Project3 {

    // This TonyoSingleLinkedList is the backbone of the application. It stores accounts storing other TonoyStacks!
    static TonyoSingleLinkedList scores;
    static int longestName = 0;
    // Boring stuff... but essential! They are used for quickly changing the app.
    // Do I have to comment further (or at all) on the variables? They are self-explanatory.
    static String filename; // Where we will load the database
    static boolean databaseLoaded; // Whether or not the database has been loaded
    static boolean sorted; // I know how an alternative to improve the efficency of this code, but I'd rather do this.

    static Card[] deck; // stores the cards used for the game
    static TonyoSingleLinkedList players; // stores the players 
    static int startingPlayer = 0; // the index of the player that is starting the round first
    static int currentRound = 0; // the current round of the game
    static int maxRounds = 15; // the maximum rounds of the game
    static TonyoQueue tableCards; // the cards players put on the table to compare
    static String[][] Names // the names of the CPU players
            = {
                {"Arwen Lovelace", "Mario Jumpan", "Optimus Robotron"},
                {"Marybelle Hopper", "Sanic Hedgehoglet", "Rosaleena La Fey"},
                {"Shellsee Granville", "Mister Pakkuman", "Totoro"}
            };

    /**
     * @param args the command line arguments
     */
    // main(...) runs the program.
    // ???/20 lines {Brackets, whitespace, and comments don't count}
    public static void main(String[] args) throws IOException {
        // Try to use the System's input for the interactive feature of this applicaiton.
        //try (Scanner input = new Scanner(System.in)) {
        Scanner input = new Scanner(System.in);
        scores = new TonyoSingleLinkedList(); // Initialize the TonoySingleLinkedList to hold the scores.
        filename = System.getProperty("user.dir") + "\\database.txt"; // Set filename to where the program is running
        databaseLoaded = sorted = false; // The database has not been loaded, nor the list sorted

        // Welcome the user.
        System.out.println("=> Welcome to the bestest Game App Ever!\n=>"
                + " A simple card game inspired by the WAR game!");

        // Loads the database for the user.
        LoadDatabase();

        // Sorts the player's scores
        SortScores(scores);

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
    static void ProcessInput(Scanner input, TonyoSingleLinkedList accounts) throws IOException {
        // Handle input as long as the user has not input [6] to shut down.
        String command = "";
        do {
            // Give the user their options. They have six options to choose from.
            System.out.println("=> Available Commands:");
            System.out.println("\t=> [1] Play Game"); // Option [1] plays the game.
            System.out.println("\t=> [2] Display Leaderboard"); // Option [2] displays the leaderboard.
            System.out.println("\t=> [3] Search Scores"); // Option [3] searches through the scores for a specific player.
            System.out.println("\t=> [4] Delete Scores"); // Option [4] deletes scores for a specific player.
            System.out.println("\t=> [5] Help"); // Option [5] opens help
            System.out.println("\t=> [6] Quit Game"); // Option [5] shuts the program down.

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
                HelpProcess();
            } else if (command.equals("6")) {
                ShutDownProcess();
            } else {
                // Let the user know the application could not interperet the input.
                System.out.println("=> Incorrect input.");
            }
            if (!command.equals("6")) {
                System.out.println();
                System.out.print("=> Enter anything to continue.");
                input.nextLine();
            }
        } while (!command.equals("6"));
    }

    // Process the gameplay.
    static void PlayGameProcess(Scanner input) throws IOException {
        // Assign the name based on user input

        // Get the player's name
        String name = "";
        while (name.isEmpty()) {
            System.out.println("=> What's your name?");
            // Assign the name based on user input
            name = GetName(input);
        }

        currentRound = 0; // Set the current round

        // Add the players to the game
        players = new TonyoSingleLinkedList();
        players.addFirst(new Player(name)); // add the human player
        System.out.println("=> Hello, " + ((Player) players.getNode(0).data).getName() + ". We are setting up the game now, using a generic 52 card playing deck.");
        for (int p = 0; p < 3; p++) { // Then add three CPU players.
            players.addLast(new Player(Names[p][(int) (Math.random() * 3)]));
            System.out.println("\t=> You are playing with " + ((Player) players.getNode(p + 1).data).getName());
        }

        // Create the deck of cards based on a generic playing card set
        Card.SetGenericDeck();
        deck = new Card[52];
        for (int s = 0; s < Card.Suits.length; s++) {
            for (int d = 0; d < Card.Denominations.length; d++) {
                deck[(s * Card.Denominations.length) + d] = new Card(s, d);
            }
        }

        // Shuffle the deck three times
        System.out.println("\t=> Now shuffling the deck.");
        Card.Shuffle(deck);
        Card.Shuffle(deck);
        Card.Shuffle(deck);
        System.out.println("\t=> Deck has been shuffled.");

        // Deal the cards out to the players. Everyone gets 13
        DealCards();

        // Player who is starting first
        startingPlayer = (int) (Math.random() * players.getSize());

        // Start the game
        System.out.print("=> Game ready. Press [Enter] to play.");
        input.nextLine();
        System.out.println();

        // Loop for 15 rounds
        for (int r = 0; r < 15; r++) {
            currentRound++; // Keep track of the current round
            System.out.println("=> Playing round " + currentRound + ". " + ((Player) players.getNode(startingPlayer).data).getName() + " will go first.");
            if (PlayRound()) { // If there is a player who has all the cards, then end the game
                break;
            }
            System.out.println();
            System.out.print("=> Press [Enter] to continue.");
            input.nextLine();

            System.out.println();
        }

        // Determine the ranking of the players and what each of them wound up with in the game.
        int place = 1; // Keep track of what place the human player placed
        boolean[] highestPlayers = new boolean[4];
        highestPlayers[0] = true;
        int h = 0;
        boolean oneWinner = true;
        for (int p = 1; p < 4; p++) {
            Player player = (Player) players.getNode(p).data;
            System.out.println(player.getName() + " has " + player.getScore());
            if (((Player) players.getNode(h).data).getScore() < player.getScore()) {
                highestPlayers = new boolean[4];
                h = p;
                highestPlayers[h] = true;
                place++;
            } else if (((Player) players.getNode(h).data).getScore() == player.getScore()) {
                highestPlayers[h] = true;
                oneWinner = false;
                place++;
            }
        }

        // Declare the winner
        if (oneWinner) {
            Player player = (Player) players.getNode(h).data;
            System.out.println("=> The winner is " + player.getName() + " with a score of " + player.getScore());
        } else {
            System.out.println("=> There is a tie!");
            for (int p = 0; p < highestPlayers.length; p++) {
            Player player = (Player) players.getNode(p).data;
                System.out.println("\t=> " + player.getName() + " is a tie!");
            }
        }

        System.out.println("=> Saving your score the leaderboard.");

        // Add the score and let the player know the score has been added
        SavedScore score = new SavedScore(name, ((Player) players.getNode(0).data).getScore(), currentRound, place);

        scores.addLast(score);

        System.out.println(
                "=> Added: " + score.toString());

        // Used for formatting the name on the displayboard
        if (longestName < name.length()) {
            longestName = name.length();
        }

        // The list becomes unsorted after a new element is added to the list
        sorted = false;

        // Try using the BufferedWriter to write to the database.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(score.toDatabaseEntry()); // Add the score to the database.
            bw.newLine();
            databaseLoaded = true; // If a database has been created, the database is technically loaded.
        } catch (Exception e) {
            // Most likely something happened with the BufferedWriter, let the user know that.
            System.out.println("=> An error while saving the database occured: " + e.getMessage());
        }
    }

// Deals the cards to the players evenly. Each player gets 13 cards.
    static void DealCards() {
        for (int c = 0; c < 52; c++) {
            ((Player) players.getNode(c % 4).data).addCard(deck[c]);
        }
    }

    // Handles playing a round of the card game
    static boolean PlayRound() {
        tableCards = new TonyoQueue(); // holds the cards the players will give up
        Player highestPlayer = (Player) players.getNode((int) (startingPlayer)).data; // stores the player with the highest card
        Card highestCard = (Card) highestPlayer.getHand().peek(); // stores the highest card of the player
        int startingNext = startingPlayer + 0; // stores the player index that will potentially be starting next round
        int t = -1; // index for comparing cards on the table
        for (int p = 0; p < 4; p++) { // for every player
            Player player = (Player) players.getNode((int) ((p + startingPlayer) % 4)).data;
            if (player.hand.getSize() > 0) { // if the player has cards to put on the table
                System.out.println("\t=> " + player.getName() + " put down a " + player.getHand().peek().toString());
                tableCards.offer(player.playCard()); // then put it on the table
                t++;
                if (highestCard.compareTo((Card) tableCards.get(t)) == -1) { // if the highest card's value is less than the one just put on the table
                    highestCard = ((Card) tableCards.get(t)); // then set a new highest card
                    highestPlayer = player; // and a new highest player
                    startingNext = (p + startingPlayer) % 4; // and who will potentially be starting first next round
                }
            }
        }
        // Give all the cards to the winner
        System.out.println("=> " + highestPlayer.getName() + " won the round and will take all " + (t + 1) + " cards.");
        for (int c = 0; c < t + 1; c++) {
            highestPlayer.addCard((Card) tableCards.poll());
        }

        // Print the hand of every player
        System.out.println("The hand of every player at the end of round " + currentRound + ":");
        for (int p = 0; p < players.getSize(); p++) {
            System.out.println("\t=> " + ((Player) players.getNode(p).data).getName() + "'s hand: ");
            for (int c = 0; c < ((Player) players.getNode(p).data).getHand().getSize(); c++) {
                System.out.println("\t\t=> " + ((Player) players.getNode(p).data).getHand().get(c));
            }
        }

        // Return whether or not the winning player has all the cards.
        return highestPlayer.hand.getSize() == 52;
    }

    // Return the name of the Player based on user input
    static String GetName(Scanner input) {
        String name = input.nextLine();
        return name;
    }

    // Process searching for scores
    static void SearchScoresProcess(Scanner input) {
        // Prompt the user for the name of the player then assign it.
        System.out.println("=> Type the name of the player whose scores you want to see: ");
        String name = input.nextLine();

        // First, sort the score if it has been unsorted.
        //if (sorted == false) {
        SortScores(scores);
        //    sorted = true;
        //}

        // Create a new SingleLinkedList (Do not add or remove elements)
        TonyoSingleLinkedList searchedScores = SearchScores(name);

        // If there are no matches, then let the player know there are none.
        if (searchedScores.getSize() == 0) {
            System.out.println("=> No players found by that name.");
        } else // Let the user know we begun to print the call history.
        { // Otherwise, display all the scores for the player.
            System.out.println("=> Player: " + name);
            // Loop through to display all the player's score
            for (int s = 0; s < searchedScores.getSize(); s++) {
                System.out.println("\t=> " + ((SavedScore) (searchedScores.getNode(s).data)).toString(longestName));
            }
        }
    }

    // Sort the scores of the player using "BubbleSort"
    static void SortScores(TonyoSingleLinkedList targetScores) {
        // Loop for every element
        for (int i = 0; i < scores.getSize() - 1; i++) {
            // Loop for almost element again. It gets smaller because the higher are going to the top.
            for (int j = 0; j < scores.getSize() - 1 - i; j++) {
                Node node = targetScores.getNode(j);
                Node nextNode = targetScores.getNode(j + 1);
                if (((SavedScore) node.data).compareTo((SavedScore) nextNode.data) == -1) {
                    Object temp = node.data;
                    node.data = nextNode.data;
                    nextNode.data = temp;
                }
            }
        }
    }
    // search for a score based on the player's name.

    static TonyoSingleLinkedList SearchScores(String name) {
        // Create the results list and get its head
        TonyoSingleLinkedList result = new TonyoSingleLinkedList();
        Node nodeRef = scores.getNode(0);
        // Loop through the list to find matching scores, as long as there is a next node.
        while (nodeRef != null) {
            // When a match is found, add it to the results list.
            if (((SavedScore) nodeRef.data).name.equals(name)) {
                result.addLast(nodeRef.data);
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
        Node nodeRef = scores.getNode(0);
        boolean deleted = false;
        // Loop to delete nodes, as long as there is a next node.
        while (nodeRef != null) {
            // If the matching node is first, then simply delete the first one in the list.
            if (((SavedScore) nodeRef.data).name.equals(name) && index == 0) {
                scores.removeFirst();
                deleted = true;
            } else if (nodeRef.next != null && ((SavedScore) nodeRef.next.data).name.equals(name)) {
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
                    bw.write(((SavedScore) (scores.getNode(i).data)).toDatabaseEntry()); // Add the account to the database.
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
            System.out.println("\t=> " + ((SavedScore) (scores.getNode(s).data)).toString(longestName));
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
                if (data.length == 4) { // A data entry must be at least of length 2...
                    scores.addLast(SavedScore.fromDatabaseEntry(data)); // Add a score onto the list using the data. 

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

    // Print instructions for the user
    static void HelpProcess() {
        System.out.println("=> This simple card game is essentially the card game \"War\", except you win by getting points based on your hand's point values.");
        System.out.println("=> Starting the game!");
        System.out.println("\t=> The game first shuffles a deck of 52 card, then it is evenly distributed amongst 4 players.");
        System.out.println("\t=> A player is randomly chosen, then the turns will proceed clockwise.");
        System.out.println("=> Playing a round!");
        System.out.println("\t=> There is minimal effort to play the game. All is needed is for every player to each put a card down. (Press [Enter] )");
        System.out.println("\t=> The cards placed on the table are compared by denomination, then suit. Whomever holds the highest card takes all the cards placed on the table.");
        System.out.println("\t=> As cards are removed or added to a player hand, so are individual scores decremented or incremented.");
        System.out.println("=> Winning the game!");
        System.out.println("\t=> The game will last for a maximum of 15 rounds.");
        System.out.println("\t=> Typically if the game was not limited by that, then the winner would be whomever holds all card in hand.");
        System.out.println("\t=> Otherwise, whomever has the highest score wins.");
        System.out.println("\t=> Your record will be saved to the leaderboard, regardles if you didn't place first.");
        System.out.println("=> Scoring!");
        System.out.println("\t=> Denominations - Point Values");
        System.out.println("\t\t=> 2 = 2 points");
        System.out.println("\t\t=> 3 = 3 points");
        System.out.println("\t\t=> 4 = 4 points");
        System.out.println("\t\t=> 5 = 5 points");
        System.out.println("\t\t=> 6 = 6 points");
        System.out.println("\t\t=> 7 = 7 points");
        System.out.println("\t\t=> 8 = 8 points");
        System.out.println("\t\t=> 9 = 9 points");
        System.out.println("\t\t=> 10 = 10 points");
        System.out.println("\t\t=> Jack = 11 points");
        System.out.println("\t\t=> Queen = 12 points");
        System.out.println("\t\t=> King = 13 points");
        System.out.println("\t\t=> Ace = 14 points");
        System.out.println("\t=> Suits - Order Rankings");
        System.out.println("\t\t=> Spades < Hearts < Diamonds < Clubs");
        System.out.println("Remember to have fun! It's just a game. And seek professional help if you have a gambling addiction.");
    }
}
