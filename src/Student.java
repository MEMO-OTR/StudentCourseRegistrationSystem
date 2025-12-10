import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student in the course registration system.
 * A student can register for courses, view registered courses,
 * and calculate tuition fees based on total ECTS credits.
 */
public class Student implements Registrable {

    protected String number;
    protected String name;
    protected String surname;
    protected String department;
    protected String type;

    protected List<Registration> registrations = new ArrayList<>();

    /**
     * Creates a new Student object with the given information.
     *
     * @param number     student ID number
     * @param name       student's first name
     * @param surname    student's last name
     * @param department student's academic department
     * @param type       student type ("U" or "G")
     */
    public Student(String number, String name, String surname, String department, String type) {
        this.number = number;
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.type = type;
    }

    /**
     * Registers the student to the specified course.
     * Prevents duplicate registration attempts.
     *
     * @param course the course to register
     */
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

    /**
     * Displays all courses registered by the student.
     * Prints a message if no courses exist.
     */
    public void showCourses() {
        if (registrations.isEmpty()) {
            System.out.println("You have no registered courses.");
            return;
        }

        for (Registration r : registrations) {
            System.out.println(r.getCourse().getCode() + " - " + r.getCourse().getName());
        }
    }

    /**
     * Calculates the tuition fee based on total ECTS credits.
     * Undergraduate: 1.0 × ECTS
     * Graduate: overridden in GraduateStudent
     *
     * @return total tuition fee as a double
     */
    public double calculateTuition() {
        double total = 0;

        for (Registration r : registrations) {
            total += r.getCourse().getEcts() * 1.000;
        }

        return total;
    }

    /**
     * @return student number
     */
    public String getNumber() { return number; }

    /**
     * @return student first name
     */
    public String getName() { return name; }

    /**
     * @return student last name
     */
    public String getSurname() { return surname; }

    /**
     * @return student department
     */
    public String getDepartment() { return department; }

    /**
     * @return student type ("U" or "G")
     */
    public String getType() { return type; }

    /**
     * Updates the student's first name.
     *
     * @param name new first name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Updates the student's last name.
     *
     * @param surname new last name
     */
    public void setSurname(String surname) { this.surname = surname; }

    /**
     * Updates the student's department.
     *
     * @param department new department name
     */
    public void setDepartment(String department) { this.department = department; }
}
