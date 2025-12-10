import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * Manages all the courses in the system.
 * Provides operations such as adding, removing, retrieving and listing courses.
 */
public class CourseCatalog {

    private Map<String, Course> courses = new HashMap<>();

    /**
     * Adds a new course to the catalog.
     *
     * @param course the Course object to add
     */
    public void addCourse(Course course) {
        courses.put(course.getCode().toUpperCase(), course);
    }

    /**
     * Removes a course from the catalog using its code.
     *
     * @param code the course code to remove
     * @return true if the course was successfully removed, false otherwise
     */
    public boolean removeCourse(String code) {
        return courses.remove(code.toUpperCase()) != null;
    }

    /**
     * Retrieves a course by its course code.
     *
     * @param code the course code
     * @return the corresponding Course object, or null if no match is found
     */
    public Course getCourse(String code) {
        return courses.get(code.toUpperCase());
    }

    /**
     * Checks if there are no courses stored in the catalog.
     *
     * @return true if the catalog is empty, false otherwise
     */
    public boolean isEmpty() {
        return courses.isEmpty();
    }

    /**
     * Returns all courses stored in the catalog.
     *
     * @return a Collection of Course objects
     */
    public Collection<Course> getAllCourses() {
        return courses.values();
    }

    /**
     * Prints the list of all available courses.
     * If no course exists, it displays a message to the user.
     */
    public void showCourses() {
        if (courses.isEmpty()) {
            System.out.println("Sistemde kayıtlı ders yok.");
            return;
        }

        for (Course c : courses.values()) {
            System.out.println(
                    c.getCode() + " - " + c.getName() +
                            " (AKTS: " + c.getEcts() + ")"
            );
        }
    }
}
