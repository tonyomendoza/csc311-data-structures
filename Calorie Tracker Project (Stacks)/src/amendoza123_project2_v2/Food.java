package amendoza123_project2_v2;

/**
 *
 * @author Tony Mendoza
 */
public class Food implements Comparable {
    String name; // stores the name of the food
    int quantity; // stores the quantity of the food
    int calories; // stores the calories of the food

    // MealType enum (used for sorting as well), stores when the food was eaten
    public static enum MealType {
        Unspecified(0), Breakfast(1), SecondBreakfast(2), Brunch(3), Lunch(4), Lunner(5), Dinner(6), Supper(7), Dessert(8);
        
        // the integer value of the mealtype is used for sorting
        private final int value;
        // Constructor for MealType
        private MealType(int value){
            this.value = value;
        }
    
        // Returns the integer value of the MealType
        public int getValue() {
            return value;
        }
    };
    MealType mealType = MealType.Unspecified;

    // Simple constructor that takes in name and calories
    public Food(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }
    // Constructor that "clones" another food
    public Food(Food food) {
        this.name = food.getName();
        this.quantity = food.getQuantity();
        this.mealType = food.getMealType();
        this.calories = food.getCalories();
    }
    
    // sets the quantity
    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            System.out.println("Invalid quantity.");
        }
    }
    // sets the mealType on the integer value
    public void setMealType(int value) {
        mealType = MealType.values()[value];
    }
    // sets the mealType using a string value
    public void setMealType(String value) {
        mealType = MealType.valueOf(name);
    }
    // Sets the mealType using the enum value
    public void setMealType(MealType value) {
        mealType = value;
    }
    
    // returns name
    public String getName() {
        return name;
    }
    // returns calories
    public int getCalories() {
        return calories;
    }
    // returns quantity
    public int getQuantity() {
        return quantity;
    }
    // returns mealType
    public MealType getMealType() {
        return mealType;
    }

    // returns a string value for this object
    @Override
    public String toString() {
        return toStringNoMealType() + " Consumed during " + mealType + ".";
    }
    // returns a string value for this object, without meal type specified.
    public String toStringNoMealType() {
        return quantity + " x " + name + ". " + (quantity * calories) + " calories (" + calories + " each).";
    }
    // returns a string with only the name and its calories  
    public String toStringSimple() {
        return name + ", " + (calories * quantity) + " calories.";
    }
    // prints the object's string value to the console
    public void print() {
        System.out.print(toString());
    }
    // prints the object's string value to the console, uses \n
    public void println() {
        System.out.println(toString());
    }

    // Use this to create a database entry. Returns all given data.
    public String toDatabaseEntry() {
        return name + ":: " + calories + ":: " + quantity + ":: " + mealType;
    }

    // Create food from a database entry.
    public static Food fromDatabaseEntry(String databaseEntry) {
        String[] data = databaseEntry.split(":: "); // Create the data array by splitting the database entry.
        return fromDatabaseEntry(data); // Proceed to the next state.
    }

    // Create food from a data array
    public static Food fromDatabaseEntry(String[] data) {
        Food food = new Food(data[1], Integer.parseInt(data[2]));
        //food.mealType = MealType.Unspecified; // Set the default MelType
        food.mealType = MealType.valueOf(data[4]);
        food.quantity = Integer.parseInt(data[3]);
        //Return the food based on the given data.
        return food;
    }
    
    // Compare the mealType, then compare calories
    @Override
    public int compareTo(Object t) {
        Food f = (Food)t;
        if (this.mealType.value < f.getMealType().value){
            return -1;
        }
        else if (this.mealType.value > f.getMealType().value){
            return 1;
        }
        else if ((this.calories * this.quantity) < (f.getCalories() * f.quantity))
            return -1;
        else if ((this.calories * this.quantity) > (f.getCalories() * f.quantity))
            return 1;
        return 0;
    }
    // Compares quantity
    public int compareQuantity(Object t) {
        Food f = (Food)t;
        if (this.quantity < f.getQuantity())
            return -1;
        else if (this.quantity > f.getQuantity())
            return 1;
        return 0;
    }
    // Compares Calories
    public int compareCalories(Object t) {
        Food f = (Food)t;
        if ((this.calories * this.quantity) < (f.getCalories() * f.quantity))
            return -1;
        else if ((this.calories * this.quantity) > (f.getCalories() * f.quantity))
            return 1;
        return 0; 
    }
}