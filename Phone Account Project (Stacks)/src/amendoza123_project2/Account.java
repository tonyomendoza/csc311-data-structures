package amendoza123_project2;

/**
 *
 * @author Tony Mendoza
 */
// This class holds data for client's account.
public class Account {

    public static enum AccountType {
        Individual, Business
    }
    protected AccountType accountType; // Its AccountType tells us whether it is for individuals or a business.
    protected String phoneNumber; // Self-explanatory
    protected TonyoStack<Call> outgoingCalls; // Used to categorize outgoing calls. Helpful, but may be unecessary for this project.
    protected TonyoStack<Call> incomingCalls; // Used to categorize incoming calls. Helpful, but may be unecessary for this project.
    protected TonyoStack<Call> calls; // Used to store calls (the real backbone of this project)
    protected String userID; // Used to manage login.
    protected String password; // Used to manage login.
    protected String firstName; // Self-explanatory.
    protected String lastName; // Self-explanatory.

    // My favorite variable for this assignment.
    // The ArrayID is used for retrieving calls in a database and adding it to the account.
    // Instead of searching for where to add the call to, we simply point to its location in the database.
    protected int ArrayID;

    // Default constructor for an account, initializes all variables.
    public Account(String firstName, String lastName, String phoneNumber, AccountType accountType, int arrayID) {
        this.userID = "";
        this.password = "";
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
        outgoingCalls = new TonyoStack<>();
        incomingCalls = new TonyoStack<>();
        calls = new TonyoStack<>();
        ArrayID = arrayID;
    }

    // Load an account from a database entry.
    public static Account fromDatabaseEntry(String databaseEntry) {
        String[] data = databaseEntry.split(":: "); // Split the database entry string into a data array
        return fromDatabaseEntry(data); // Proceed to the next state.
    }

    // Load an account from a database entry using a given data array.
    public static Account fromDatabaseEntry(String[] data) {
        Account.AccountType accountType = Account.AccountType.Individual; // Default account type.
        if (data[2].equals("Business")) { // If he account type is a business, make it so
            accountType = Account.AccountType.Business;
        }
        // Create an account based on the interpreted data
        Account account = new Account(data[4], data[3], data[5], accountType, Integer.parseInt(data[6]));
        account.SetCredentials("", data[0], data[1]); // Set the userID and password from the data as well
        return account; // Return the created account
    }

    // Set a (new) password
    public void SetPassword(String oldPassword, String newPassword) {
        // User must have input the old passwrod to change to a new password
        // If the password matches an empty string, then you can create a new password because the account is new.
        if (comparePassword(oldPassword)) {
            this.password = newPassword;
        }
    }

    // Set a (new) userID and a (new) password
    public void SetCredentials(String oldPassword, String newUserID, String newPassword) {
        // User must have input the old password to change to a new password and set the userID 
        // If the password matches an empty string, then you can create a new password and userID because the account is new.
        if (comparePassword(oldPassword)) {
            this.userID = newUserID;
            this.password = newPassword;
        }
    }

    // Generate a semi-random userID
    public String GenerateUserID() {
        // Take the first 5 letters of the last name or business name after removing the white space.
        String userID = this.lastName.replace(" ", "").substring(0, 5);
        // The next three random digits are attached to the end of the userID
        for (int i = 0; i < 3; i++) {
            userID += (int) (Math.random() * 9);
        }
        return userID; // return the created userID
    }

    // Compares the passwords to see if they match. Used for logging in.
    public boolean comparePassword(String password) {
        return this.password.equals(password);
    }

    // Creates a database entry string based on the given account data.
    // This does pose security risk. Consider encryption in the future.
    public String toDatabaseEntry() {
        return userID + ":: "
                + password + ":: "
                + accountType + ":: "
                + lastName + ":: "
                + firstName + ":: "
                + phoneNumber + ":: "
                + ArrayID;
    }

    // Prints accoutn data, specifically: last name, first name, and phone number.
    @Override
    public String toString() {
        return lastName + ", " + firstName + ": " + phoneNumber;
    }

    // Return the first name
    public String getfirstName() {
        return firstName;
    }

    // Return the last name or business name
    public String getLastName() {
        return lastName;
    }

    // Return the AccountType
    public AccountType getAccountType() {
        return accountType;
    }

    // Return userID
    public String getUserID() {
        return userID;
    }

    // return the phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Send a call (outgoing)
    public void sendCall(String number, float duration) {
        Call call = new Call(number, duration, Call.CallType.Outgoing); // Create the call
        calls.push(call); // Add the call
        outgoingCalls.push(call); // Ad teh call to outgoingCalls
    }

    // Recieve a call (incoming)
    public void recieveCall(String number, float duration) {
        Call call = new Call(number, duration, Call.CallType.Incoming); // Create the call
        calls.push(call); // Add the call
        incomingCalls.push(call); // Add the call to incomingCalls
    }

    // return incomingCalls TonyoStack
    public TonyoStack<Call> getIncomingCalls() {
        return incomingCalls;
    }

    // Return outgoingCalls TonoyStack
    public TonyoStack<Call> getOutgoingCalls() {
        return outgoingCalls;
    }

    // Return calls TonyoStack
    public TonyoStack<Call> getCalls() {
        return calls;
    }

    // Return ArrayID
    public int GetArrayID() {
        return ArrayID;
    }
}