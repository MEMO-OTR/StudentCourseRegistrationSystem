import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    @Test
    void testRegistrationConnection() {
        Student s = new Student("1", "Ali", "Kaya", "CS", "U");
        Course c = new Course("CS101", "Intro", 5);

        Registration r = new Registration(s, c);

        assertEquals(s, r.getStudent());
        assertEquals(c, r.getCourse());
    }
}
