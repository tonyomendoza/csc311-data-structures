package amendoza123_project1_v2;

/**
 *
 * @author Tony Mendoza
 */
public class Project1 {
    // The three arrays used to store the data for this program
    // I admit, it could be done in less... but I'd prefer to use the other data structures next time
    private static Object[][] teams; // [y][] holds team names. [][x] holds winning percentage.
    private static String[] players; // holds player names
    private static String[][] rounds; // [y][] holds the round number, [x] holds player names

    // The main program
    public static void main(String[] args) {
        teams = createTeams(); // initialize the teams 2D array with preset data.
        players = createPlayers(); // initalize the players array with preset data.
        rounds = new String[4][8]; // initialize the rounds 2D array with preset data.
        
        //Welcome the Football fan.
        System.out.println("Welcome to the unofficial NFL Draft!\nAlthough I know little about football, I tried my best.\nBut not my best to be funny, because this is serious. I think.\n");
        
        // Step 1: "Assign a random winning percentage to each of the teams for the previous season."
        assignRandomWinningPercentages();
        // Step 2: "Based on the winning percentage, rank the teams in order starting with the one that had the worst performance."
        stupidSort(teams);
        // Step 3: "Each team can now select players in the order they were ranked."
        System.out.println("The teams have been sorted by their winning percentages.");
        for (Object[] team : teams) { // For every team
            // Print their winning percentages.
            System.out.println("\t" + (String) team[0] + " won " + ((float) team[1] * 100) + "% of their games last season.");
        }
        System.out.println("Too bad for " + teams[0][0] + ". LOSERS! But #Winners for the draft!\n");
        // Step 4: "Assign numbers 1 to 4 to the players randomly indicating the round in which they can be picked."
        assignPlayersToRounds();
        // Step 5: "In each round, the teams in order of their numbers will choose randomly from the players eligible for that round and select them."
        draftPick();
        // Step 6: "At the end of 4 rounds, each of the 8 teams will have 4 players they picked."
        System.out.println("The draft has now concluded.\nEight NFL teams have picked 32 (minus 2) NFL players!\nThank you and good night.");
    }
    
    // Step 1: Assign random winning Percentages
    static void assignRandomWinningPercentages(){
        // Print that a random number will be be assigned.
        System.out.println("Assigning random winning percentages to the 8 teams competing to pick \"great\" players.");
        for (Object[] team : teams) { // For every team
            team[1] = generateWinningPercentage(); // Assign the winning percentage.
            // Print their winning percentages.
            System.out.println("\t" + (String) team[0] + " won " + ((float) team[1] * 100) + "% of their games last season.");
        }
        System.out.println();
    }
    
    // Step 4: Assing players to the 4 rounds they will be picked from
    static void assignPlayersToRounds(){
        System.out.println("Now assigning 32 players randomly to 4 rounds evenly (8 each round).");
        int length = players.length; // Save the length of the actual players (which is 32...)
        for (int i = 0; i < length; i++) { // for every 32 players (length)
            int index = (int)(Math.random() * players.length); // create an index to choose a player from 'players' randomly
            // Assign a player to a round linearly and distributed evenly. Keep in mind, the player chosen for that round is still random.
            rounds[(int)(i / 8)][i % 8] = players[index];
            // Print that a player has been placed in a round.
            System.out.println("\t" + (String) rounds[(int)(i / 8)][i % 8] + " is placed in round " + ((int)(i / 8) + 1) + ".");
            players = (String[])removeFromPlayers(index); // Remove a player from the 'players' array.
        }
        System.out.println();
    }
    
    // Step 5: Have the teams pick the players for the draft.
    static void draftPick(){
        System.out.println("Now picking for the draft. *Fingers Crossed*");
        for (int r = 0; r < rounds.length; r++) { // For every round
            for (int p = 0; p < rounds[r].length; p++){ // For every player in that round
                // Pick and print the players picked in the order of the team's winning percentage
                System.out.println("\t" + teams[p][0] + " picks " + rounds[r][p] + " in round " + (r + 1));
            }
        }
        System.out.println();
    }

    // Create the teams 2D array eligible for the draft.
    static Object[][] createTeams() {
        return new Object[][]{
            {"Dallas Cowboys", 0}, {"San Francisco 49ers", 0},
            {"Baltimore Ravens", 0}, {"Philadelphia Eagles", 0},
            {"Seattle Seahawks", 0}, {"Atlanta Falcons", 0},
            {"Green Bay Packers", 0}, {"Pittsburgh Steelers", 0},};
    }

    // Create the players array for the draft.
    static String[] createPlayers() {
        return new String[]{
            "Erik Williams", "Karl Wilson", "Cary Brabham", "Gordon Laro",
            "Garisson Hearst", "Barry Sanders", "Eddie George", "Daunte Culpepper",
            "Marshall Faulk",  "Michael Vick",  "Ray Lewis",  "Donovan McNabb",
            "Shaun Alexander", "Vince Young", "Brett Favre",  "Troy Polamalu",
            "Drew Brees", "Peyton Hillis", "Calvin Johnson", "Barry Sanders",
            "Adrian Peterson", "Richard Sherman", "Odell Beckham Jr.", "Rob Gronkowski",
            "Von Miller", "Antonio Brown", "Larry Fitzgerald", "Roberto Garza",
            "Luis Castillo", "Dorsey Levens",
            "John Madden", // Hurt is ankle at training camp. Never made it pro.
            "Tony \"Broken Face\" Mendoza",}; // Is worse that the guy before him.
    }

    // Generate a random winning percentage
    static float generateWinningPercentage() {
        return (float)Math.random(); // return a random decimal number using the Math.random() function
    }

    // A generic but potentially slow sorting algorithm
    // I know it's not "stupid", but I'm bummed I couldn't figure out quicksort in time.
    static void stupidSort(Object[][] team) {
        for (int i = 0; i < team.length; i++) { // for every element in the 2d array
            for (int j = 0; j < team.length; j++) { // and for every element in that same 2d array
                if (i != j && (float) team[i][1] <= (float) team[j][1]) {
                    // If not comparing the same position and if the winning percentage at 'i' is less than the winning percentage at 'j'
                    swap(team, i, j); // Swap at indexes 'i' and 'j'
                }
            }
        }
    }

    // Swap elements in a 2D array.
    // 'a' is an index, 'b' is an index
    static void swap(Object[][] array, int a, int b) {
        // Temporarily store the data at 'a'
        Object name = array[a][0];
        Object percentage = array[a][1];
        // Set data at 'a' to data at 'b'
        array[a][0] = array[b][0];
        array[a][1] = array[b][1];
        // Set data at 'b' to the temp data
        array[b][0] = name;
        array[b][1] = percentage;
    }

    // Remove a player from the players array
    static String[] removeFromPlayers(int index) {
        // Create a new array
        String[] newArray = new String[players.length - 1];
        // Iterate through the old players array
        for (int i = 0, j = 0; i < players.length; i++, j++) {
            if (i != index) { // As long as the index is not at the current position...
                newArray[j] = players[i]; // set element at 'j' to player at 'i'
            } else {
                j--; // increment 'j' by one (1)
            }
        }
        return newArray; // Return the new array
    }
}