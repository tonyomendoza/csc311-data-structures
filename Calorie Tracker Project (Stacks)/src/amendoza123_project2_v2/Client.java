package amendoza123_project2_v2;

/**
 *
 * @author Tony Mendoza
 */
// This class holds data for client's account.
public class Client {

    protected TonyoStack<Food> foods; // Used to categorize outgoing foods.
    protected TonyoStack<Food> foodTypes; // Used to categorize foods.
    protected String userID; // Used to manage login.
    protected String password; // Used to manage login.
    protected String firstName; // Self-explanatory.
    protected String lastName; // Self-explanatory.
    protected Food favorite; // holds the food with the most quantity
    protected int totalCalories; // 
    boolean sorted;

    // My favorite variable for this assignment.
    // The ArrayID is used for retrieving calls in a database and adding it to the account.
    // Instead of searching for where to add the call to, we simply point to its location in the database.
    protected int ArrayID;

    // Default constructor for an account, initializes all variables.
    public Client(String firstName, String lastName, int arrayID) {
        this.userID = "";
        this.password = "";
        this.firstName = firstName;
        this.lastName = lastName;
        foods = new TonyoStack<>();
        foodTypes = new TonyoStack<>();
        ArrayID = arrayID;
        this.sorted = false;
    }

    // Load an account from a database entry.
    public static Client fromDatabaseEntry(String databaseEntry) {
        String[] data = databaseEntry.split(":: "); // Split the database entry string into a data array
        return fromDatabaseEntry(data); // Proceed to the next state.
    }

    // Load an account from a database entry using a given data array.
    public static Client fromDatabaseEntry(String[] data) {
        // Create an account based on the interpreted data
        Client account = new Client(data[2], data[3], Integer.parseInt(data[4]));
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
                + firstName + ":: "
                + lastName + ":: "
                + ArrayID;
    }

    // Prints accoutn data, specifically: last name, first name, and phone number.
    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }

    // Return the first name
    public String getfirstName() {
        return firstName;
    }

    // Return the last name or business name
    public String getLastName() {
        return lastName;
    }

    // Return userID
    public String getUserID() {
        return userID;
    }

    // Return totalCalories
    public int getTotalCalories() {
        return totalCalories;
    }
    // Return favorite food (the food with the most quantity
    public Food getFavorite(){
        return favorite;
    }

    // Return ArrayID
    public int GetArrayID() {
        return ArrayID;
    }

    // Return calls TonyoStack
    public TonyoStack<Food> getFood() {
        return foods;
    }
    
    // Return calls TonyoStack
    public TonyoStack<Food> getFoodTypes() {
        return foodTypes;
    }

    // addFood, the heart of this project.
    public void addFood(Food food) {
        foods.push(food); // Add the food
        totalCalories += food.calories * food.quantity; // increment totalCalories with specified values
        boolean inList = false; // a flag. if a similar food type is in list
        Food f = null;
        // Check if a smiilar food types is in the list
        for(int i = 0; i < foodTypes.getSize(); i++){
            if(foodTypes.get(i).name.equals(food.name)){ // is it does exist
                f = foodTypes.get(i);
                f.setQuantity(f.getQuantity() + food.getQuantity()); // then change the food type's quantity
                inList = true; // set the flag to true
                break; // stop checking
            }
        }
        // If the food type is not in the list, then add it to the food types list
        if(!inList){
            foodTypes.push(new Food(food));
            f = foodTypes.peek();
        }
        
        // If the favorite is null or the recently added food type's quantity is greater than the favorite's quantity
        if (favorite == null || f.quantity > favorite.quantity)
            favorite = f;
        sorted = false; // the stack is not sorted.
    }

    // addFood to the stack based on specific parameters
    public void addFood(String name, int calories, int quantity, Food.MealType mealType) {
        Food food = new Food(name, calories); // Create the call
        food.setMealType(mealType);
        food.setQuantity(quantity);
        addFood(food);
    }

    // Sort the food in the stack
    public void sortFood() {
        if (!sorted) { // if it is unsorted
            for (int t = 0; t < foods.getSize() - 1; t++) { // interate for every element minus 1
                for (int i = 0; i < foods.getSize() - t - 1; i++) { // iterate again for every element minus 1 minus the iterations passed
                    if (foods.get(i).compareTo(foods.get(i + 1)) == 1) { // compare the elements by meal type than calories
                        foods.swapElements(i, i + 1); // swap this element with the next
                    }
                }
            }
            
            // same as above, except swap by comparing calories
            for (int t = 0; t < foodTypes.getSize() - 1; t++) {
                for (int i = 0; i < foodTypes.getSize() - t - 1; i++) {
                    if (foodTypes.get(i).compareCalories(foodTypes.get(i + 1)) == 1) {
                        foodTypes.swapElements(i, i + 1);
                    }
                }
            }
            sorted = true;
        }
    }
}