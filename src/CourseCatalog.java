import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class CourseCatalog {

    private Map<String, Course> courses = new HashMap<>();

    public void addCourse(Course course) {
        courses.put(course.getCode().toUpperCase(), course);
    }

    public boolean removeCourse(String code) {
        return courses.remove(code.toUpperCase()) != null;
    }

    public Course getCourse(String code) {
        return courses.get(code.toUpperCase());
    }

    public boolean isEmpty() {
        return courses.isEmpty();
    }

    public Collection<Course> getAllCourses() {
        return courses.values();
    }

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
