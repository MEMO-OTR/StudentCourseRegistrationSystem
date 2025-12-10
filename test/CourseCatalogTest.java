import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CourseCatalogTest {

    @Test
    void testAddCourse() {
        CourseCatalog catalog = new CourseCatalog();
        Course c = new Course("CS101", "Intro", 5);

        catalog.addCourse(c);

        assertEquals(c, catalog.getCourse("CS101"));
    }

    @Test
    void testRemoveCourse() {
        CourseCatalog catalog = new CourseCatalog();
        Course c = new Course("CS101", "Intro", 5);

        catalog.addCourse(c);
        boolean removed = catalog.removeCourse("CS101");

        assertTrue(removed);
        assertNull(catalog.getCourse("CS101"));
    }

    @Test
    void testIsEmpty() {
        CourseCatalog catalog = new CourseCatalog();

        // Başlangıçta boş olmalı
        assertTrue(catalog.isEmpty());

        // Ders eklenince boş olmamalı
        catalog.addCourse(new Course("CS101", "Intro", 5));
        assertFalse(catalog.isEmpty());
    }
}
