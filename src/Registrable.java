/**
 * Represents an interface for any class that supports course registration.
 * Classes implementing this interface must provide their own logic for
 * registering a student to a specific course.
 */
public interface Registrable {

    /**
     * Registers the given course.
     *
     * @param course the course to be registered
     */
    void registerCourse(Course course);
}
