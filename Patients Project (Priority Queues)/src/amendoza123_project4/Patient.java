package amendoza123_project4;

/**
 *
 * @author Tony Mendoza
 * A class used to store data of a patient.
 */
public class Patient implements Comparable {
    // A bunch of variables that are mostly self-explanatory
    protected String firstName;
    protected String lastName;
    protected String dateOfBirth;
    protected String arrivalTime;
    protected float arrivalTimeF; // 0-59, used for calculating attended time
    protected String attendedTime;
    protected boolean attended;
    protected float attendingMax; // the maximum value of attending to
    protected float attendingCounter; // the counter used for attending to

    // MedicalCondition has an urgency value (used to help in sorting) and a description (used to laugh my butt off)
    public static enum MedicalCondition { // Source: thehappyhospitalist.blogspot.com/2008/03/heartastroke.html
        Unemployable(20, "if you can't get a job, come to the ER instead."),
        AbdominalPainOfAbsolutelyNoSignificance(19, "As in \"why come to the ER?\" type of pain."),
        Cyberchondria(18, "worrying about all the worst possibilities after reading the internet."),
        InsurancePains(17, "pain that presents itself when insurance settlements are involved."),
        Whinorrhea(16, "patient whines a lot."),
        Chacne(15, "chest acne."),
        JustAHotMess(14, "self-explanatory."),
        IneffectiveCoping(13, "self-explanatory."),
        JiffyFeet(12, "has gross feet."),
        Microdekia(11, "not playing with a full deck."),
        ArrestogenicShock(10, "sudden life-threatening illness caused by getting arrested."),
        Drunkacidal(9, "doing life-threatening stuff while drinking."),
        GlobusStupidicus(8, "self-explanatory."),
        Ridiculitis(7, "because every symptom needs respect."),
        CraniorectalInversion(6, "self-explanatory."),
        Heartastroke(5, "a patient confusing heart symptoms with stroke symptoms and have an impossible combination of both."),
        DeadShovel(4, "a patient who has a heart attack shoveling snow."),
        AcuteLeadPoisoning(3, "gunshot wound."),
        FicticiousIllness(2, "illness that is obviously staged."),
        Supratentorial(1, "when the causes of unexplained symptoms originates from above the tentorium; indicating a psychatric origin.")
        ;
        private final int urgency;
        private final String description;

        // Constructor for MedicalCondition
        private MedicalCondition(int urgency, String description){
            this.urgency = urgency;
            this.description = description;
        }
    
        // Returns the urgency from within MedicalCondition
        public int getUrgency() {
            return urgency;
        }
        
        // Returns the description from within MedicalCondition
        public String getDescription(){
            return description;
        }
    };
    protected MedicalCondition medicalCondition;

    // Constructor for Patient, bunch of stuff being intialized in here
    public Patient(String firstName, String lastName, String dateOfBirth, MedicalCondition medicalCondition, String arrivalTime, float arrivalTimeF, float attendingMax) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.medicalCondition = medicalCondition;
        this.arrivalTime = arrivalTime;
        this.arrivalTimeF = arrivalTimeF;
        this.attended = false;
        this.attendingMax = attendingMax;
    }

    // This is like a counter. When it is hit, then the patient has been attended for a second.
    // Once it reaches its max, then the patient is finished being attended to.
    public void Attend(float attendingIncrement, String currentTime) {
        attendingCounter += attendingIncrement;
        if (attendingCounter >= attendingMax) {
            attended = true;
            attendedTime = currentTime;
        }
    }
    
    // Return whether or not the patient has been attended to.
    public boolean Attended(){
        return attended;
    }

    // First compare the urgency of the medical condition then compare the time they arrived.
    @Override
    public int compareTo(Object o) {
        Patient p = (Patient) o;
        if (p.medicalCondition.getUrgency() < this.medicalCondition.getUrgency()) {
            return 1;
        } else if (p.medicalCondition.getUrgency() > this.medicalCondition.getUrgency()) {
            return -1;
        } else if (p.arrivalTimeF < this.arrivalTimeF) {
            return 1;
        } else if (p.arrivalTimeF > this.arrivalTimeF) {
            return -1;
        } else {
            return 0;
        }
    }
    
    // Return the name of the patient
    public String getName(){
        return firstName + " " + lastName;
    }
    // Return the first name of the patient
    public String getFirstName(){
        return firstName;
    }
    // Return the last name of the patient
    public String getLastName(){
        return lastName;
    }
    // Return medical condition
    public MedicalCondition getMedicalCondition(){
        return medicalCondition;
    }

    // Return the string value of the patient (for reports)
    @Override
    public String toString() {
        return lastName + ", " + firstName + ". D.O.B.: " + dateOfBirth
                + "\n\tArrived at " + arrivalTime
                + "\n\tTreated for " + medicalCondition + ": " + medicalCondition.getDescription() + " Urgency: " + medicalCondition.getUrgency() + "."
                + "\n\tAttended at " + attendedTime;
    }
}