package amendoza123_project4_v2;

/**
 *
 * @author Tony Mendoza
 */
// Student class stores a student's name and id
public class Student {
    int id; // unique to every student
    String name; // can be similar

    // default constructor, id and name parameters
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // return id
    public int getId() {
        return id;
    }

    // return name
    public String getName() {
        return name;
    }
    
    // return string value of student
    public String toString(){
        return "Student: " + id + ": " + name;
    }
}
