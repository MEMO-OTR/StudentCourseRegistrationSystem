/**
 * Represents a registration entry connecting a student with a course.
 * Each Registration object indicates that a specific student is enrolled
 * in a specific course.
 */
public class Registration {

    private Student student;
    private Course course;

    /**
     * Creates a new registration record associating the given student with a course.
     *
     * @param student the student who is registering
     * @param course  the course that the student is being registered to
     */
    public Registration(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    /**
     * Returns the student associated with this registration.
     *
     * @return the registered student
     */
    public Student getStudent() { return student; }

    /**
     * Returns the course associated with this registration.
     *
     * @return the registered course
     */
    public Course getCourse() { return course; }
}
