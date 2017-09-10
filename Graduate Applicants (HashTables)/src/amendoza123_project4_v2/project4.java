package amendoza123_project4_v2;

/**
 *
 * @author Tony Mendoza
 */
public class project4 {	// Driver method to test HashTable class
    static TonyoHashTable<Integer, Student> graduates; // HashTable to store graduates
    static TonyoArrayList<Student> students; // datastructure to store students
    // datastructures to store students from 2011, 2012, 2013
    static TonyoArrayList<Student> admitted2011;
    static TonyoArrayList<Student> admitted2012;
    static TonyoArrayList<Student> admitted2013;

    // FirstName row of a database
    static String[] firstNameBank = {
        "Gray", "Blue", "Red", "Yellow", "Green", "Orange", "Violet", "Dog", "Eri", "Bear",
        "Orchid", "Floral", "Jet", "Seagreen", "Black-Hat", "Alice", "Rain", "Danny", "Butler", "Rabbit",
        // Went through my contacts list for the rest
        "Abdul", "Adone", "Agustin", "Alex", "Andrew", "Ari", "Arleen", "Bee", "Ben", "Brenda",
        "Caitlin", "Carolina", "Cecilia", "Cheyenne", "Chris", "Cindy", "Crystal", "Daisy", "Deandre", "Diana",
        "Elvis", "Eric", "Fernando", "Gil", "Gio", "Gloria", "Hadeel", "Hector", "Herb", "Jacquelyn",
        "Jasmine", "Jax", "Karina", "Maribel", "Pam", "Robyn", "Rosie", "Ricardo", "Uzi", "Yuna"
    };

    // Runs the program 5/20 Lines
    public static void main(String[] args) {
        System.out.println("=> Welcome to this grad app that tests a hashtable."
                + "\n\t=>Running low on creativity towards the end of the semester. Sorry...");
        admitStudents(); // admit students
        applyForGraduation(); // have them apply for graduation by 2017
        Graduate(); // let them walk... and let me pass so I can walk next year :D
    }
    
    // Determine who is admitted in which years
    static void admitStudents(){
        System.out.println("=> Admitting Students");
        students = new TonyoArrayList<>(60); // Create structure for 60 students
        admitted2011 = new TonyoArrayList<>(20); // store them in here too
        admitted2012 = new TonyoArrayList<>(20); // and some in here
        admitted2013 = new TonyoArrayList<>(20); // and the rest in here
        for (int s = 0; s < 60; s++) { // for every student
            students.add(RandomStudent(s + 1)); // add a random student
            switch (s % 3) { // add them into an admittance year by using modulo 
                case 0:
                    admitted2011.add(students.get(s));
                    break;
                case 1:
                    admitted2012.add(students.get(s));
                    break;
                default:
                    admitted2013.add(students.get(s));
                    break;
            }
            // Print the student
            System.out.println("\t=> " + students.get(s) + ", " + (s % 3));
        }
    }
    
    // Set who will be graduating
    static void applyForGraduation(){
        System.out.println("=> Applying for graduation...");
        graduates = new TonyoHashTable<>(29); // create a hashtable to store the graduates
        // Calculate how many are graduating.
        int extra = RandomInt(11); // set a random number to be added to the minimum (make sure it is less than maximum
        for (int i = 0; i < 15 + extra; i++) { // iterate through all the students to determine who will graduate.
            Student s;
            switch (RandomInt(3)) { // pick from a admittance class randomly, then pick a student randomly
                case 0:
                    s = admitted2011.get(RandomInt(20));
                    break;
                case 1:
                    s = admitted2012.get(RandomInt(20));
                    break;
                default:
                    s = admitted2013.get(RandomInt(20));
                    break;
            }
            if (!graduates.put(s.id, s)) { // if that student is already graduating, then restart
                i--;
            }
        }
        System.out.println("=> Recieved applications.");
    }
    
    // Graduate already!!! And print the graduation rate
    // This method tests the hashtable using ineffective means of comparision :D Sorry.
    static void Graduate(){
        System.out.println("=> Congratulations! Class of 2017!");
        int grad2011 = 0, grad2012 = 0, grad2013 = 0; // used to store who is graduating from which admittance class
        for (int i = 0; i < admitted2011.getSize(); i++) { // iterate through every student from 2011
            Student graduate = graduates.get(admitted2011.get(i).getId()); // pick a student
            if (graduate != null) { // if  the student is graduating, then print student name
                System.out.println("\t=> 2011 graduate: " + graduate.name);
                grad2011++; // graduating from 2011
            }
        }
        for (int i = 0; i < admitted2012.getSize(); i++) { // iterate through every student from 2012
            Student graduate = graduates.get(admitted2012.get(i).getId()); // pick a student
            if (graduate != null) { // if the student is graduating, then print student name
                System.out.println("\t=> 2012 graduate: " + graduate.name);
                grad2012++; // graduating from 2012
            }
        }
        for (int i = 0; i < admitted2013.getSize(); i++) { // iterate through every student from 2013
            Student graduate = graduates.get(admitted2013.get(i).getId()); // pick a student
            if (graduate != null) { // is the student is graduating, then print student name
                System.out.println("\t=> 2013 graduate: " + graduate.name);
                grad2013++; // graduating from 2013
            }
        }
        
        double g2011 = (double)grad2011 / (admitted2011.getSize() + admitted2012.getSize() + admitted2013.getSize()); // calculate grad rate of 2011 students
        double g2012 = (double)grad2011 / (admitted2012.getSize() + admitted2013.getSize()); // calculate grad rate of 2012 students
        double g2013 = (double)grad2011 / (admitted2013.getSize()); // calculate rate of 2013 students
        
        // Print grad rates
        System.out.println("=> 2011 Graduation rate: " + g2011);
        System.out.println("=> 2012 Graduation rate: " + g2012);
        System.out.println("=> 2013 Graduation rate: " + g2013);
    }

    // Create a random student
    static Student RandomStudent(int id) {
        int index = RandomInt(firstNameBank.length);
        Student student = new Student(id, firstNameBank[index]);
        // Remove entry from the database, because it is not needed.
        String[] tempFirst = new String[firstNameBank.length - 1];
        for (int i = 0, t = 0; i < firstNameBank.length; i++) {
            if (i != index) {
                tempFirst[t] = firstNameBank[i];
                t++;
            }
        }
        firstNameBank = tempFirst;
        // Return the new student
        return student;
    }

    // Create a random number
    static int RandomInt(int length) {
        return (int) (Math.random() * length);
    }
}
