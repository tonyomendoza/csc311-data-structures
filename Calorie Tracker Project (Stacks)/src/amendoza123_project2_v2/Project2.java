package amendoza123_project2_v2;

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
 * @author Tony Mendoza NOTE: 'Client' and 'User' are used interchangeably.
 *
 */
public class Project2 {

    // This TonyoStack is the backbone of the application. It stores accounts storing other TonoyStacks!
    static TonyoStack<Client> clients;
    // Boring stuff... but essential! They are used for quickly changing the app.
    static String filename; // Where we will load the database
    static boolean databaseLoaded; // Whether or not the database has been loaded
    static Client activeClient; // This is used as a pointer to reference the current session's account

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    // main(...) runs the program.
    // 12/20 lines {Brackets, whitespace, and comments don't count}
    public static void main(String[] args) throws IOException {
        // Try to use the System's input for the interactive feature of this applicaiton.
        try (Scanner input = new Scanner(System.in)) {
            clients = new TonyoStack<>(); // Initialize the TonyoStack to hold the c.
            filename = System.getProperty("user.dir") + "\\database.txt"; // Set filename to where the program is running
            databaseLoaded = false; // The database has not been loaded

            // Welcome the user.
            System.out.println("=> Welcome to the MealCheckerApp!\n=>"
                    + "Our app is better than FitBit!");

            // Loads the database for the user.
            LoadDatabase();

            // Handle what the user inputs. This serves as the loop of the program.
            ProcessInput(input, clients);

            // Close the input.
            input.close();

            // Tell the user goodbye.
            System.out.println("=> Program finished. Have a nice day!");
        } catch (Exception e) {
            // If something happened, print the error message.
            System.out.println("=> Something went wrong: " + e.getMessage());
        }
    }

    // Handles the input of the user. This is where all the action of the program begins.
    static void ProcessInput(Scanner input, TonyoStack<Client> clients) throws IOException {
        // Handle input as long as the user has not input [9] to shut down.
        String command = "";
        do {
            // Give the user their options. They have nine options to choose from.
            System.out.println("\n=> Available Commands:");
            if (activeClient == null) { // If there is no active client.
                System.out.println("\t=> [1] Register"); // Option [1] registers the client.
                if (databaseLoaded) {
                    System.out.println("\t=> [2] Login"); // Option[2] logs in the client
                }
            } else { // If the current session's account is active.
                System.out.println("\t=> [2] Logout"); // Option [2] logs out the client.
                System.out.println("\t=> [3] Log Food"); // Option [3] logs food.
                System.out.println("\t=> [4] Food History"); // Option [4] displays food history.
                System.out.println("\t=> [5] Food Type History"); // Option [5] displays food type history.
                System.out.println("\t=> [6] Total Calories"); // Option [6] displays total calories consumed.
                System.out.println("\t=> [7] Maximum Calories Food"); // Option [7] displays maximum calories in a food type.
                System.out.println("\t=> [8] Favorite Food of the Day"); // Option [8] displays the food type with the largest quantity.
            }
            System.out.println("\t=> [9] Shut Down"); // Option [9] shuts the program down.

            // The following reads what the user has input for a command.
            // They will proceed to the next state to process their next input.
            command = input.nextLine().trim();
            if (activeClient == null) {
                if (command.equals("1")) {
                    RegistrationProcess(input);
                } else if (command.equals("2") && databaseLoaded) {
                    LoginProcess(input);
                } else if (command.equals("9")) {
                    ShutDownProcess();
                } else {
                    // Let the user know the application could not interperet the input.
                    System.out.println("=> Incorrect input.");
                }
            } else if (command.equals("2")) {
                LogoutProcess();
            } else if (command.equals("3")) {
                LogFoodProcess(input);
            } else if (command.equals("4")) {
                FoodHistoryProcess();
            } else if (command.equals("5")) {
                FoodTypeHistoryProcess();
            } else if (command.equals("6")) {
                TotalCaloriesProcess();
            } else if (command.equals("7")) {
                MaximumCaloriesProcess();
            } else if (command.equals("8")) {
                FavoriteFoodProcess();
            } else if (command.equals("9")) {
                ShutDownProcess();
            } else {
                // Let the user know the applicatiron could not interperet the input.
                System.out.println("=> Incorrect input.");
            }
            if (!command.equals("9")) {
                System.out.print("=> Press [Enter] to continue... ");
                input.nextLine();
            }
        } while (!command.equals("9"));
    }

    // Process the client's registration.
    static void RegistrationProcess(Scanner input) throws IOException {
        // Prompt the user for the client's first name
        System.out.println("=> Type the first name of the client you wish to add: ");
        String firstName = input.nextLine();

        // Prompt the user for the client's last name
        System.out.println("=> Type the last name of the client you wish to add: ");
        String lastName = input.nextLine();

        // Add the account based on the interpretations, then let the user know it has been added.
        Client client = new Client(firstName, lastName, clients.getSize());
        clients.push(client);
        System.out.println("=> Added: " + client.toString());

        // Begin processing the userID for the account
        String userID = "";
        int tries = 0;
        System.out.println("=> Create a unique userID for your account: ");
        // Loop the process until the userID is not empty.
        do {
            // The user has two tries to enter a unique userID
            if (tries < 2) {
                if (tries == 1) { // If they already used one try, tell them they have one more try and we will generate another if they fail.
                    System.out.println("=> Try one more time to create a unique userID for your account. "
                            + "If you cannot, then we will generate one for you: ");
                }
                userID = input.nextLine(); // Set userID from the user's input.
            } else {
                // Generate one for the user.
                System.out.println("=> Failed to create a unique userID. Generating a unique userID for your account... ");
                userID = client.GenerateUserID();
            }

            // Regardless if it's user input or generated, check if it is unique.
            for (int i = 0; i < clients.getSize() - 1; i++) {
                if (clients.get(i).getUserID().equals(userID)) {
                    userID = ""; // Set userID to nothing to start the loop over.
                    break; // No need to search the entire database if this userID has already been found to be not unique.
                }
            }
            // Add to the tries counter to determine when to generate a unique userID.
            // Could also be used for statistical purposes for the random generator.
            tries++;
        } while (userID.equals(""));

        // Process the user's input for the password.
        System.out.println("=> userID created. Create an password at least 8 characters in length: ");
        String password;
        // Loop for an appropriate password as long as the password is less than 8 characters.
        do {
            password = input.nextLine();
        } while (password.length() < 8);

        // Set the userID and password to the account.
        client.SetCredentials("", userID, password);

        // More than likely, the database has not been loaded because it does not exist.
        // This is the user's first time using the program.
        // Try using the BufferedWriter to write to the database.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, databaseLoaded))) {
            bw.write(client.toDatabaseEntry()); // Add the account to the database.
            bw.newLine(); // Create a new entry to the database.
            databaseLoaded = true; // If a database has been created, the database is technically loaded.
        } catch (Exception e) {
            // Most likely something happened with the BufferedWriter, let the user know that.
            System.out.println("=> An error while saving the database occured: " + e.getMessage());
        }

        // Let the user know an account has successfully been created.
        System.out.println("=> Login credentials successfully created for " + client.getUserID());
    }

    // Process the user's input for logging in.
    static void LoginProcess(Scanner input) {
        // Prompt the user for their userID
        System.out.println("=> Type the userID of the client you wish to see: ");
        String userID = input.nextLine();

        // Set the variable 'account' to the account with the matching userID
        Client client = null;
        for (int i = 0; i < clients.getSize(); i++) { // loop
            if (clients.get(i).getUserID().equals(userID)) { // if the userID matches
                client = clients.get(i);
                break; // No need to search through the entire database
            }
        }

        // If the account could not be found, let the user know the login has failed...
        if (client == null) {
            System.out.println("=> No account found. Login failed.");
        } else { // else, prompt the user for a password.
            System.out.println("=> Account found. Please input the password: ");
            if (client.comparePassword(input.nextLine())) { // If the password matches, login the user
                System.out.println("=> Password correct. Login sucessful.");
                activeClient = client; // Set the active account for this session.
            } else { // else, let the user know the login has failed. For security reasons (laziness), let the user redo the login process.
                // I'm not being paid enough to have the user repeatedly try passwords. Make them work for incorrect passwords.
                System.out.println("=> Password incorrect. Login failed.");
            }
        }
    }

    // Just logout the user.
    static void LogoutProcess() throws IOException {
        activeClient = null; // Unset activeAccount
        System.out.println("=> Logout sucessful."); // Let the user know they are logged out.
    }

    // simplest method for recieving input
    static String receiveInput(Scanner input) {
        return receiveInput(input, true);
    }
    // simple method for recieving input, allow empty strings
    static String receiveInput(Scanner input, boolean allowEmpty) {
        return receiveInput(input, "", allowEmpty);
    }
    // simple method for recieving input, give a message for the user to read
    static String receiveInput(Scanner input, String prompt, boolean allowEmpty) {
        return receiveInput(input, prompt, null, allowEmpty);
    }
    // method for recieving input
    static String receiveInput(Scanner input, String prompt, String[] allowedInput, boolean allowEmpty) {
        String response = "";
        while (true) { // loop until the loop breaks (a valid response is inputted)
            // If the prompt is not empty
            if (!prompt.isEmpty()) {
                // Print out what the user should read
                System.out.println(prompt);
            }
            response = input.nextLine().trim(); // remove the whitespace, set the response
            // do not allow the response to go through if empty stirngs aren't allowed
            if (!allowEmpty && response.isEmpty()) {
                System.out.println("=> Invalid input.");
                continue;
            }
            // If there is no keys to match with allowed input, then let the response pass through
            if (allowedInput == null || allowedInput.length == 0) {
                break;
            }
            // Otherwise, check to match the users response with the allowed responses
            for (int i = 0; i < allowedInput.length; i++) {
                if (!response.equals(allowedInput[i])) {
                    System.out.println("=> Invalid input.");
                    response = "";
                }
            }
            break;
        }
        return response;
    }

    // Simple method for handling inputted integers
    static int receiveInputInt(Scanner input, String prompt, boolean allowEmpty) {
        return receiveInputInt(input, prompt, allowEmpty, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    // Handle inputted integers
    static int receiveInputInt(Scanner input, String prompt, boolean allowEmpty, int min, int max) {
        int response = -1;
        // Loop until the loop breaks
        do {
            // If the prompt is not empty
            if (!prompt.isEmpty()) {
                // Print out what the user should read
                System.out.println(prompt);
            }
            try { // try to interpret the response
                response = input.nextInt(); // read what the user inputted
                if (response >= min && response <= max) { // break if the number is between the min and max
                    break;
                }
                // otherwise, the input was not in range
                System.out.println("=> Input not within range. Try again.");
            } catch (Exception e) {
                // Catch if there is data could not be interpreted
                System.out.println("=> Invalid input: " + e.toString());
            }
        } while (true);

        return response; // return the user's integer response
    }

    // Process user's input to log food.
    static void LogFoodProcess(Scanner input) {
        // Begin processing what the client types to create a food object.
        String name = receiveInput(input, "=> Enter a valid name for the food.", false).toLowerCase();
        int calories = receiveInputInt(input, "=> Enter a number for a single serving of this food's calories.", false, 0, Integer.MAX_VALUE);
        Food food = new Food(name, calories); // Used to create entry for database
        food.quantity = receiveInputInt(input, "=> Enter a number quantity of the food (or servings).", false, 0, Integer.MAX_VALUE);
        int mealType = receiveInputInt(input, "=> Select a valid number for the food's mealtype.\n"
                + "\t=> [1] Breakfast\n\t=> [2] Second-Breakfast\n\t=> [3] Brunch\n\t=> [4] Lunch\n"
                + "\t=> [5] Lunner\n\t=> [6] Dinner\n\t=> [7] Supper\n\t=> [8] Dessert", false, 0, 8);
        food.setMealType(mealType);

        // Add the created food object to the client's eaten food
        activeClient.addFood(food);

        // Let the user know the client's food is sucessfully logged.
        System.out.println("=> Food sucessfully logged: " + food);

        // Try using the BufferedWriter to save a database entry.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            // Save the food based on the userID (for reference) and the active account's arrayID (for data retrieval efficiency)
            bw.write(activeClient.getUserID() + ":: " + food.toDatabaseEntry() + ":: " + activeClient.GetArrayID());
            bw.newLine(); // Create a new database entry
        } catch (Exception e) {
            // Most likely something happened with the BufferWriter, let the user know that.
            System.out.println("=> An error while saving the database occured: " + e.getMessage());
        }
    }

    // Print the client's food history.
    static void FoodHistoryProcess() {
        activeClient.sortFood();
        // Let the user know we begun to print the food history.
        System.out.println("=> Printing food history (from latest to earliest)");
        // Loop through the active account's food history, printing each food in the process.
        for (int f = activeClient.getFood().getSize() - 1; f >= 0; f--) {
            System.out.println("\t=> " + activeClient.getFood().get(f));
        }
        // Let the user know we finisehd printing food history.
        System.out.println("=> Finished printing food history.");
    }

    // Print the client's food type history.
    static void FoodTypeHistoryProcess() {
        // Let the user know we begun to print the food type history.
        System.out.println("=> Printing food type history (from most to least calories)");
        // Loop through the active account's food type history, printing each food type in the process.
        for (int f = activeClient.getFoodTypes().getSize() - 1; f >= 0; f--) {
            System.out.println("\t=> " + activeClient.getFoodTypes().get(f).toStringNoMealType());
        }
        // Let the user know we finisehd printing food type history.
        System.out.println("=> Finished printing food type history.");
    }

    // Print the total calories consumed by the client
    static void TotalCaloriesProcess() {
        System.out.println("=> " + activeClient.firstName + "'s total calories: " + activeClient.getTotalCalories());
    }
    
    // Print the food that has the most calories
    static void MaximumCaloriesProcess() {
         // since the food types stack is sorted by calories, return the one from the top
        Food f = activeClient.getFoodTypes().peek();
        System.out.println("=> " + activeClient.getfirstName() + " ate " + f.getQuantity() + " " 
                + f.getName() + ", reaching the maximum calories of " + f.getCalories() * f.getQuantity() + ".");
    }

    // Print the food with the most quantity
    static void FavoriteFoodProcess() {
        System.out.println("=> " + activeClient.getfirstName() + "'s favorite food is " + activeClient.getFavorite().getName() 
                + ", in which she ate of " + activeClient.favorite.getQuantity() + ".");
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
                if (data.length == 5) { // A data entry must be at least of length 7...
                    clients.push(Client.fromDatabaseEntry(data)); // Push an account onto the TonyoStack using the data. 
                } else if (data.length == 6) { // or of lenght 5 to be processed.
                    // If the call was incoming, recieve the call.
                    clients.get(Integer.parseInt(data[5])).addFood(Food.fromDatabaseEntry(data));
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
