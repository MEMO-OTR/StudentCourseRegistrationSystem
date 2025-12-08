import java.util.ArrayList;
import java.util.List;

public class Student implements Registrable, java.io.Serializable {

    protected String number;
    protected String name;
    protected String surname;
    protected String department;

    protected List<Registration> registrations = new ArrayList<>();

    public Student(String number, String name, String surname, String department) {
        this.number = number;
        this.name = name;
        this.surname = surname;
        this.department = department;
    }
    @Override
    public void registerCourse(Course course) {
        for (Registration r : registrations) {
            if (r.getCourse().getCode().equalsIgnoreCase(course.getCode())) {
                System.out.println("⚠ You are already registered for this course!");
                return;
            }
        }

        registrations.add(new Registration(this, course));
        System.out.println("✔ Course successfully registered.");
    }
    public void showCourses() {
        if (registrations.isEmpty()) {
            System.out.println("You have no registered courses.");
            return;
        }

        for (Registration r : registrations) {
            System.out.println(r.getCourse().getCode() + " - " + r.getCourse().getName() +
                    " | Registration Date: " + r.getDate());
        }
    }
    public double calculateTuition() {
        return registrations.size() * 1.000;
    }

    public String getNumber() {return number;}
    public String getName() {return name;}
    public String getSurname() {return surname;}
    public String getDepartment() {return department;}

    public void setName(String name) {this.name = name;}
    public void setSurname(String surname) {this.surname = surname;}
    public void setDepartment(String department) {this.department = department;}
}
