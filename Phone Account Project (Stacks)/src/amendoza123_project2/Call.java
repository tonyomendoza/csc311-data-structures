package amendoza123_project2;

/**
 *
 * @author Tony Mendoza
 */
// A class used to store data about a call.
public class Call {
    public static enum CallType { Incoming, Outgoing }
    protected CallType callType; // Its CallType tells whether it was sent or recieved.
    protected float duration; // How long the call lasted.
    protected String phoneNumber; // Self-explanatory.
    
    // Default constructor for phone calls. Assigns the number, duration, and call type
    public Call(String phoneNumber, float duration, CallType callType){
        this.phoneNumber = phoneNumber;
        this.duration = duration;
        this.callType = callType;
    }
    
    // Create a Call from a database entry.
    public static Call fromDatabaseEntry(String databaseEntry) {
        String[] data = databaseEntry.split(":: "); // Create the data array by splitting the database entry.
        return fromDatabaseEntry(data); // Proceed to the next state.
    }
    // Create a Call from a data array
    public static Call fromDatabaseEntry(String[] data) {
        CallType callType = CallType.Incoming; // Set the default CallType
        // If it was sent then assign it a new CallType
        if(data[1].equals("Outgoing"))
            callType = CallType.Outgoing;
        // Return the call based on the given data by using the defaul constructor.
        return new Call(data[2], Float.parseFloat(data[3]), callType);
    }
    
    // Return the callType
    public CallType getCallType(){
        return callType;
    }
    // Return the duration.
    public float getDuration(){
        return duration;
    }
    // Return the phoneNumber
    protected String getCallNumber(){
        return phoneNumber;
    }
    
    // Return a string based on the call's data
    @Override
    public String toString(){
        // Configure the string for the call's duration
        String durationMinutes = "" + ((int)duration) + " minute"; // Minutes
        if(!durationMinutes.equals("1")) // If the minutes does not equal to 1, then attach the 's' for 'minutes'.
            durationMinutes += "s";
        String durationSeconds = "" + (int)((duration - (int)duration) * 60) + " second"; // Seconds
        if(!durationSeconds.equals("1")) // If the seconds does not equal to 1, then attach the 's' for 'seconds'.
            durationSeconds += "s";
        // Return the complete string: call type. phone number. duration minutes, duration seconds.
        return callType + ". " + phoneNumber + ". " + durationMinutes + ", " + durationSeconds + ".";
    }
    
    // Use this to create a database entry. Returns all given data.
    public String toDatabaseEntry(){
        return callType + ":: " + phoneNumber + ":: " + duration;
    }
}