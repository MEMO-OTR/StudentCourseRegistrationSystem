import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    void testRegisterCourseSuccess() {
        Student s = new Student("1", "Ali", "Kaya", "CS", "U");
        Course c = new Course("CS101", "Intro", 5);

        s.registerCourse(c);

        assertEquals(1, s.registrations.size());
        assertEquals("CS101", s.registrations.get(0).getCourse().getCode());
    }

    @Test
    void testRegisterDuplicateCourse() {
        Student s = new Student("1", "Ali", "Kaya", "CS", "U");
        Course c = new Course("CS101", "Intro", 5);

        s.registerCourse(c);
        s.registerCourse(c);  // same course again

        assertEquals(1, s.registrations.size(), "Duplicate course should NOT be added");
    }

    @Test
    void testCalculateTuition() {
        Student s = new Student("1", "Ali", "Kaya", "CS", "U");
        s.registerCourse(new Course("CS101", "Intro", 5));
        s.registerCourse(new Course("MATH200", "Math", 4));

        assertEquals(9.0, s.calculateTuition());
    }
}
