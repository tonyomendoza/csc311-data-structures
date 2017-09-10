package amendoza123_project4;

/**
 *
 * @author Tony Mendoza
 * This is the main class.
 */
public class project4 {

    // FirstName row of a database
    static String[] firstNameBank = {
        "Gray", "Blue", "Red", "Yellow", "Green", "Orange", "Violet",
        "Dog", "Eri", "Bear", "Dark", "Princess Floral", "Dr. Jet", "Seagreen",
        "Mr."
    };
    // LastName row of a database
    static String[] lastNameBank = {
        "Allround", "Speedy", "SparkPlug", "SharkTank", "Samurai", "Marmalade", "Neutral",
        "Woof-Woof", "Sandwiches", "Roar", "Orchid", "White", "Black", "de la Marina",
        "Black-Hat"
    };
    // birthday row of a database
    static String[] birthdayBank = {
        "November 04, 1995", "April 18, 1993", "July 27, 1993", "November 15, 1992", "September 12, 1993", "September 23, 1995", "August 12, 1992",
        "November 15, 1995", "November 14, 1991", "Nopetember 03, 2025", "October 30, 1992", "Juanuary 27, 199", "March 21, 1990", "June 20, 1996",
        "February 09, 1993"
    };
    // Variables used to help in programming the app
    static int hour = RandomInt(24); // the hour of the visits
    static Patient[] attendedPatients; // the patients that have already been attended to
    static Patient attendingPatient; // the patient that is being attended to
    static TonyoPriorityQueue pQueue; // the prioirty queue used to sort the patients

    // the main thing that runs the program
    public static void main(String[] args) {
        // Initialize variables
        Initialize();

        // Loop for 60 "minutes" (iterations)
        for (int m = 0, p = 0, a = 0; m < 60; m++) { // 60 minutes
            System.out.println(FormatTime(hour, m)); // Print the time

            p = AddPatient(p, m); // Add the patients

            // As long as there still patients to attend to
            if (a < attendedPatients.length) {
                if (attendingPatient == null) { // If no patients have yet been attended to, then start attending.
                    BeginAttending();
                } else if (attendingPatient.Attended()) { // If the patient has been attended to, then get a new patient.
                    a = SwapPatients(a);
                } else { // Else then we need to attend to our current patient
                    AttendPatient(m);
                }
            } else { // When there are no more patients, then let my professor know. Don't keep him waiting.
                System.out.println("\tNo more patients are visiting.");
            }
        }

        PrintReport(); // Print the report.
    }

    // Begin Attending
    static void BeginAttending() {
        // If there is a patient waiting..
        if (pQueue.peek() != null) {
            System.out.println("\t=> " + ((Patient) pQueue.peek()).getName() + " is being called in to be attended to.");
        }
        // Then allow the patient to be attended to
        attendingPatient = (Patient) pQueue.poll();
    }

    // Initialize the Variables
    static void Initialize() {
        pQueue = new TonyoPriorityQueue();
        attendingPatient = null;
        attendedPatients = new Patient[15];
    }
    
    // Print the report for the professor
    static void PrintReport() {
        System.out.println();
        System.out.println("Reports:");

        // Iterate through the patients that were attended to.
        for (int i = 0; i < attendedPatients.length; i++) {
            if (attendedPatients[i] != null) {
                System.out.println((i + 1) + ") " + attendedPatients[i].toString());
            }
        }
    }

    // Format a time by using a given hour and minutes
    static String FormatTime(int h, int m) {
        String hour = "0" + h;
        if (h > 9) {
            hour = "" + h;
        }
        String minutes = "0" + m;
        if (m > 9) {
            minutes = "" + m;
        }
        return hour + ":" + minutes;
    }

    // Add the patient based on given patient count and minutes
    static int AddPatient(int p, int m) {
        // As long as there is a quota of patients that need to be attended to
        if (p < attendedPatients.length) {
            // Then try to fill it. The patient has about 1.5 chances out of 4 of arriving
            // Case 3, 2, 1: No one came in
            // Case 0, someone came in.
            int incomingPatient = RandomInt(4);
            switch (incomingPatient) {
                case 3:
                    if (RandomInt(2) == 1) {
                        System.out.println("\tNobody came in.");
                        break;
                    }
                case 0:
                    // Patient is random (sort of)
                    Patient patient = RandomPatient(m);
                    System.out.println("\t" + patient.getName() + " is visiting for " + patient.getMedicalCondition() + ".");
                    // add the patient to the waitlist
                    pQueue.offer(patient);
                    p++;
                    break;
                case 1:
                    System.out.println("\t== Nobody came in.");
                    break;
                case 2:
                    System.out.println("\t== Nobody came in.");
                    break;
            }
        }
        return p;
    }

    // Swap patients if one has already been seen
    static int SwapPatients(int a) {
        // Attendeding patinets 
        attendedPatients[a] = attendingPatient;
        a++;
        System.out.println("\t<= " + attendingPatient.getName() + " has been attended to and is leaving now.");
        if (pQueue.peek() != null) {
            System.out.println("\t=> " + ((Patient) pQueue.peek()).getName() + " is being called in to be attended to.");
        }
        attendingPatient = (Patient) pQueue.poll();
        return a;
    }

    // Attend the Patient
    static void AttendPatient(int m) {
        System.out.println("\t" + attendingPatient.getName() + " is being attended to.");
        attendingPatient.Attend(1, FormatTime(hour, m)); // Attend to the patient.
        if (!attendingPatient.Attended()) { // At this point, the patient is given a diagnosis 
            System.out.println("\t\tPatient showing symptoms of " + attendingPatient.getMedicalCondition().toString() + ".");
            System.out.println("\t\tSymptoms: \"" + attendingPatient.getMedicalCondition().getDescription() + "\"");
        } else { // At this point the patietn is being treated.
            System.out.println("\t\tRubbing patient with anti-" + attendingPatient.getMedicalCondition().toString() + " cream."); 
        }
    }

    // Create a random patient
    static Patient RandomPatient(float arrivalTime) {
        // Save the necessary variables for creating our random patient
        int nameIndex = RandomInt(firstNameBank.length);
        String firstName = firstNameBank[nameIndex];
        String lastName = lastNameBank[nameIndex];
        String birthday = birthdayBank[nameIndex];
        
        // Remove entry from the database, because it is not needed.
        String[] tempFirst = new String[firstNameBank.length - 1]; 
        String[] tempLast = new String[lastNameBank.length - 1];
        for (int i = 0, t = 0; i < firstNameBank.length; i++) {
            if (i != nameIndex) {
                tempFirst[t] = firstNameBank[i];
                tempLast[t] = lastNameBank[i];
                t++;
            }
        }
        firstNameBank = tempFirst;
        lastNameBank = tempLast;

        // Return the new patient
        return new Patient(
                firstName,
                lastName,
                birthday, // birthdate
                Patient.MedicalCondition.values()[RandomInt(Patient.MedicalCondition.values().length)], // condition
                FormatTime(hour, (int) arrivalTime),
                arrivalTime,
                2 // attended time set to zero
        );
    }

    // Create a random number
    static int RandomInt(int length) {
        return (int) (Math.random() * length);
    }
}
