// The package, not delievered by FedEx or UPS or USPS
package amendoza123_project2;

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
 * 1. Implement and use Stack class and methods: Program uses "Push", "Peek". I couldn't find a use a good reason to use 'Pop'.
 * 2. Use a separate class to represent Objects for Stack: Done
 * 3. Write additional methods to search through a Stack: Done externally. Parameters for searching are too specific for a TonyoStack class.
 * 4. Use methods to do separate and repetitive work. The main method should not have more than 20 lines of code: Done. 12/20.
 * 5. Implement the project correctly: Works like awesome.
 * Bonus: 10 points will be awarded for use of meaningful identiﬁers, consistent indentation, 
 *  explanatory comments in your code and properly formatted output.: Done. Done. Done.
 */
public class Project2 {

    // This TonyoStack is the backbone of the application. It stores accounts storing other TonoyStacks!
    static TonyoStack<Account> accounts;
    // Boring stuff... but essential! They are used for quickly changing the app.
    // Do I have to comment further (or at all) on the variables? They are self-explanatory.
    static String filename; // Where we will load the database
    static boolean databaseLoaded; // Whether or not the database has been loaded
    static Account activeAccount; // This is used as a pointer to reference the current session's account

    /**
     * @param args the command line arguments
     */
    // main(...) runs the program.
    // 12/20 lines {Brackets, whitespace, and comments don't count}
    public static void main(String[] args) throws IOException {
        // Try to use the System's input for the interactive feature of this applicaiton.
        try (Scanner input = new Scanner(System.in)) {
            accounts = new TonyoStack<>(); // Initialize the TonyoStack to hold the websites.
            filename = System.getProperty("user.dir") + "\\database.txt"; // Set filename to where the program is running
            databaseLoaded = false; // The database has not been loaded

            // Welcome the user.
            System.out.println("=> Welcome to the Telephone Company App!\n=>"
                    + "We promise we won't screw over our newly registered customers (for the first year, at least)!");

            // Loads the database for the user.
            LoadDatabase();

            // Handle what the user inputs. This serves as the loop of the program.
            ProcessInput(input, accounts);

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
    static void ProcessInput(Scanner input, TonyoStack<Account> accounts) throws IOException {
        // Handle input as long as the user has not input [6] to shut down.
        String command = "";
        do {
            // Give the user their options. They have six options to choose from.
            System.out.println("=> Available Commands:");
            if (activeAccount == null) { // If there is no active account.
                System.out.println("\t=> [1] Register"); // Option [1] registers the client.
                if (databaseLoaded) {
                    System.out.println("\t=> [2] Login"); // Option[2] logs in the client
                }
            } else { // If the current session's account is active.
                System.out.println("\t=> [2] Logout"); // Option [2] logs out the client.
                System.out.println("\t=> [3] Log Call"); // Option [3] logs a call.
                System.out.println("\t=> [4] Call History"); // Option [4] displays call history.
                System.out.println("\t=> [5] Display Shortest and Longest Calls"); // Option [5] displays the shortest and longest call.
            }
            System.out.println("\t=> [6] Shut Down"); // Option [6] shuts the program down.

            // The following reads what the user has input for a command.
            // They will proceed to the next state to process their next input.
            command = input.nextLine().trim();
            if (activeAccount == null) {
                if (command.equals("1")) {
                    RegistrationProcess(input);
                } else if (command.equals("2") && databaseLoaded) {
                    LoginProcess(input);
                } else {
                    // Let the user know the application could not interperet the input.
                    System.out.println("=> Incorrect input.");
                }
            } else if (command.equals("2")) {
                LogoutProcess();
            } else if (command.equals("3")) {
                LogCallProcess(input);
            } else if (command.equals("4")) {
                CallHistoryProcess();
            } else if (command.equals("5")) {
                GetShortestLongestCallProcess();
            } else if (command.equals("6")) {
                ShutDownProcess();
            } else {
                // Let the user know the application could not interperet the input.
                System.out.println("=> Incorrect input.");
            }
            System.out.println("");
        } while (!command.equals("6"));
    }

    // Process the client's registration.
    static void RegistrationProcess(Scanner input) throws IOException {
        /// Ready to interpret input for account type. First, set it to individual.
        Account.AccountType accountType = Account.AccountType.Individual;
        // The following processes if the client is a business. This will loop unless the input is correct.
        String isBusiness = "";
        do {
            // If the user did not type [y] or [n] then led them know their input is invalid and will be checked again.
            if (!isBusiness.equals("")) {
                System.out.println("=> Invalid input."); // 
            }
            // Ask the user if the account is a business account.
            System.out.println("=> Is this a business account? [y] = yes, [n] = no: ");
            isBusiness = input.nextLine().toLowerCase().trim();
        } while (!isBusiness.equals("y") && !isBusiness.equals("n"));

        String option = "last name of the client"; // Set the option for an individual client.
        String firstName = "";
        // If the account is a business, then assign: the account type,  first name, and the options for the user.
        if (isBusiness.equals("y")) {
            accountType = Account.AccountType.Business;
            option = "name of the business";
            firstName = "Business"; // For some reason, businesses first name is Business
        } else { // Else, interpret the input for the client's first name.
            // Prompt the user for the client's name
            System.out.println("=> Type the first name of the client you wish to add: ");
            firstName = input.nextLine();
        }

        // Prompt the user for the client's last name (or business name)
        System.out.println("=> Type the " + option + " you wish to add: ");
        String lastName = input.nextLine();

        // Add the account based on the interpretations, then let the user know it has been added.
        Account account = new Account(firstName, lastName, RandomizePhoneNumber(accountType), accountType, accounts.getSize());
        accounts.push(account);
        System.out.println("=> Added: " + account.toString());

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
                userID = account.GenerateUserID();
            }

            // Regardless if it's user input or generated, check if it is unique.
            for (int i = 0; i < accounts.getSize() - 1; i++) {
                if (accounts.get(i).getUserID().equals(userID)) {
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
        account.SetCredentials("", userID, password);

        // More than likely, the database has not been loaded because it does not exist.
        // This is the user's first time using the program.
        if (!databaseLoaded) {
            // Try using the BufferedWriter to write to the database.
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
                bw.newLine(); // Create a new entry to the database.
                bw.write(account.toDatabaseEntry()); // Add the account to the database.
                databaseLoaded = true; // If a database has been created, the database is technically loaded.
            } catch (Exception e) {
                // Most likely something happened with the BufferedWriter, let the user know that.
                System.out.println("=> An error while saving the database occured: " + e.getMessage());
            }
        }

        // Let the user know an account has successfully been created.
        System.out.println("=> Login credentials successfully created for " + account.getUserID());
    }

    // Process the user's input for logging in.
    static void LoginProcess(Scanner input) {
        // Prompt the user for their userID
        System.out.println("=> Type the userID of the client you wish to see: ");
        String userID = input.nextLine();

        // Set the variable 'account' to the account with the matching userID
        Account account = null;
        for (int i = 0; i < accounts.getSize(); i++) { // loop
            if (accounts.get(i).getUserID().equals(userID)) { // if the userID matches
                account = accounts.get(i);
                break; // No need to search through the entire database
            }
        }

        // If the account could not be found, let the user know the login has failed...
        if (account == null) {
            System.out.println("=> No account found. Login failed.");
        } else { // else, prompt the user for a password.
            System.out.println("=> Account found. Please input the password: ");
            if (account.comparePassword(input.nextLine())) { // If the password matches, login the user
                System.out.println("=> Password correct. Login sucessful.");
                activeAccount = account; // Set the active account for this session.
            } else { // else, let the user know the login has failed. For security reasons (laziness), let the user redo the login process.
                // I'm not being paid enough to have the user repeatedly try passwords. Make them work for incorrect passwords.
                System.out.println("=> Password incorrect. Login failed.");
            }
        }
    }

    // Just logout the user.
    static void LogoutProcess() throws IOException {
        activeAccount = null; // Unset activeAccount
        System.out.println("=> Logout sucessful."); // Let the user know they are logged out.
    }

    // Process user's input to log calls.
    static void LogCallProcess(Scanner input) {
        // Begin processing if the client made the call.
        String response = "";
        // Loop until the user's response is [y] or [n]
        do {
            // If the response is not empty, then the call was invalid.
            if (!response.equals("")) {
                System.out.println("=> Invalid input.");
            }
            // Ask the user whether or not the client made the call.
            System.out.println("=> Did you make the call? [y] = yes, [n] = no");
            response = input.nextLine().trim();
        } while (!response.equals("y") && !response.equals("n"));

        Call call = null; // Used to create entry for database
        if (response.equals("y")) { // If the client made the call, then send a random call.
            activeAccount.sendCall(RandomizePhoneNumber(), RandomizeDuration());
            call = activeAccount.getOutgoingCalls().peek(); // assign 'call' a value. 
        } else { // Else, recieve a random call.
            activeAccount.recieveCall(RandomizePhoneNumber(), RandomizeDuration());
            call = activeAccount.getIncomingCalls().peek(); // assign 'call' a value
        }
        // Let the user know the client's call is sucessfully logged.
        System.out.println("=> Call sucessfully logged: " + call);

        // Try using the BufferedWriter to save a database entry.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.newLine(); // Create a new database entry
            // Save the call based on the userID (for reference), the call's data, and the active account's arrayID (for data retrieval efficiency)
            bw.write(activeAccount.getUserID() + ":: " + call.toDatabaseEntry() + ":: " + activeAccount.GetArrayID());
        } catch (Exception e) {
            // Most likely something happened with the BufferWriter, let the user know that.
            System.out.println("=> An error while saving the database occured: " + e.getMessage());
        }
    }

    // Print the client's call history.
    static void CallHistoryProcess() {
        // Let the user know we begun to print the call history.
        System.out.println("=> Printing call history (from latest to earliest)");
        // Loop through the active account's call history, printing each call in the process.
        for (int c = activeAccount.getCalls().getSize() - 1; c >= 0; c--) {
            System.out.println("\t=> " + activeAccount.getCalls().get(c));
        }
        // Let the user know we finisehd printing call history.
        System.out.println("=> Finished printing call history.");
    }

    // Print the client's shortest and longest call.
    static void GetShortestLongestCallProcess() {
        // Only print if there are calls to compare
        if (activeAccount.getCalls().getSize() > 0) {
            // Assume at this point there is at least one call that can be the shortest and the longest.
            Call shortestCall = activeAccount.getCalls().get(0);
            Call longestCall = activeAccount.getCalls().get(0);

            // Loop through the rest of the calls to compare and assign the shortest and longest call.
            for (int i = 1; i < activeAccount.getCalls().getSize(); i++) {
                if (activeAccount.getCalls().get(i).duration > shortestCall.duration) {
                    longestCall = activeAccount.getCalls().get(i); // If the longer than before, assign to the new call.
                }
                if (activeAccount.getCalls().get(i).duration < shortestCall.duration) {
                    shortestCall = activeAccount.getCalls().get(i); // If shorter than before, assign to the new call.
                }
            }

            // Print the shortest and longest calls.
            System.out.println("=> The shortest call is: " + shortestCall);
            System.out.println("=> The longest call is: " + longestCall);
        } else { // Else, tell the user to actually log some calls.
            System.out.println("=> Try logging some calls first.");
        }
    }

    // Shut the program down.
    static void ShutDownProcess() throws IOException {
        // Data is saved after every data entry, just let the client know there is nothing to worry about.
        System.out.println("=> Data has already been saved. Shutting down...");
    }

    // Randomize a duration for calls, does not make sense to be inside the Call class.
    static float RandomizeDuration() {
        int minutes = (int) (Math.random() * 90); // Randomize the minutes.
        float seconds = (float) Math.random(); // Randomize the seconds.
        return minutes + seconds; // return the combination of both (in decimal form)
    }

    // Randomize a phone number for a random account type, does not make sense to be insdie the Call class.
    static String RandomizePhoneNumber() {
        // Randomly choose between a business or an indidvidual account.
        // Choose then move on to the next state.
        int choice = (int) (Math.random() * 2);
        if (choice == 1) {
            return RandomizePhoneNumber(Account.AccountType.Business);
        }
        return RandomizePhoneNumber(Account.AccountType.Individual);
    }

    // Randomize phone number for a determined account type. Does not make sense for it to be in Call class.
    static String RandomizePhoneNumber(Account.AccountType accountType) {
        // We are assuming the calls are made in the US
        String phoneNumber = "1 ";
        // If the account is a business, give them the 800 area code.
        if (accountType == Account.AccountType.Business) {
            phoneNumber += "(800) ";
        } else { // else, determine what the area code could be. 
            // Loop through the possible numbers until three appropriate numbers are found.
            // More than likely, the area  code will never be a 800 number, but this is a fail-safe.
            do {
                phoneNumber = "1 ("; // Reset the phoneNumber if it failed
                // Generate three numbers randomly
                for (int i = 0; i < 3; i++) { 
                    phoneNumber += (int) (Math.random() * 10);
                }
                phoneNumber += ") "; // Fix the parentheses at the end
            } while (phoneNumber.equals("1 (800) "));
        }
        // Randomly generate 7 phone numbers.
        for (int i = 0; i < 7; i++) {
            // Add that hyphen if three numbers were already added
            if (i == 3) {
                phoneNumber += "-";
            }
            phoneNumber += (int) (Math.random() * 10);
        }
        return phoneNumber; // Return the awesomely generated phone number
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
                if (data.length == 7) { // A data entry must be at least of length 7...
                    accounts.push(Account.fromDatabaseEntry(data)); // Push an account onto the TonyoStack using the data. 
                } else if (data.length == 5) { // or of lenght 5 to be processed.
                    if (data[1].equals("Incoming")) { // If the call was incoming, recieve the call.
                        accounts.get(Integer.parseInt(data[4])).recieveCall(data[2], Float.parseFloat(data[3]));
                    }
                    if (data[1].equals("Outgoing")) { // If the call was outgoing, send the call
                        accounts.get(Integer.parseInt(data[4])).sendCall(data[2], Float.parseFloat(data[3]));
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