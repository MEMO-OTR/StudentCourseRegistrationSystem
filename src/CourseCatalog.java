import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CourseCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public void showCourses() {
        if (courses.isEmpty()) {
            System.out.println("Sistemde kayıtlı ders yok.");
            return;
        }

        for (Course c : courses.values()) {
            System.out.println(c.getCode() + " - " + c.getName() + " (AKTS: " + c.getEcts() + ")");
        }
    }
}
