import java.io.*;
import java.util.Map;

/**
 * Handles all file input/output operations of the system.
 *
 * Responsibilities:
 * - Load students, courses, and registrations from CSV files
 * - Save students, courses, and registrations to CSV files
 *
 * This class is stateless and provides only static utility methods.
 */
public class FileManager {

    /** CSV file for student records */
    private static final String STUDENT_FILE = "students.csv";

    /** CSV file for course records */
    private static final String COURSE_FILE = "courses.csv";

    /** CSV file for registration records */
    private static final String REG_FILE = "registrations.csv";

    /**
     * Loads all system data from CSV files.
     *
     * @param students       map to populate with students
     * @param courseCatalog  catalog to populate with courses
     */
    public static void loadAll(Map<String, Student> students,
                               CourseCatalog courseCatalog) {
        loadStudents(students);
        loadCourses(courseCatalog);
        loadRegistrations(students, courseCatalog);
    }

    /**
     * Saves all system data to CSV files.
     *
     * @param students       student storage map
     * @param courseCatalog  course catalog
     */
    public static void saveAll(Map<String, Student> students,
                               CourseCatalog courseCatalog) {
        saveStudents(students);
        saveCourses(courseCatalog);
        saveRegistrations(students);
    }

    /**
     * Loads students from the students CSV file.
     *
     * @param students map to populate with loaded students
     */
    private static void loadStudents(Map<String, Student> students) {
        File file = new File(STUDENT_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) continue;

                Student student = data[4].equals("G")
                        ? new GraduateStudent(data[0], data[1], data[2], data[3])
                        : new Student(data[0], data[1], data[2], data[3], "U");

                students.put(data[0], student);
            }

        } catch (IOException e) {
            System.out.println("Student load error: " + e.getMessage());
        }
    }

    /**
     * Saves all students into the students CSV file.
     *
     * @param students student storage map
     */
    private static void saveStudents(Map<String, Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_FILE))) {

            writer.println("number,name,surname,department,type");

            for (Student student : students.values()) {
                writer.println(
                        student.getNumber() + "," +
                                student.getName() + "," +
                                student.getSurname() + "," +
                                student.getDepartment() + "," +
                                student.getType()
                );
            }

        } catch (IOException e) {
            System.out.println("Student save error: " + e.getMessage());
        }
    }

    /**
     * Loads courses from the courses CSV file.
     *
     * @param catalog course catalog to populate
     */
    private static void loadCourses(CourseCatalog catalog) {
        File file = new File(COURSE_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 3) continue;

                Course course = new Course(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2])
                );

                catalog.addCourse(course);
            }

        } catch (Exception e) {
            System.out.println("Course load error: " + e.getMessage());
        }
    }

    /**
     * Saves all courses into the courses CSV file.
     *
     * @param catalog course catalog
     */
    private static void saveCourses(CourseCatalog catalog) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COURSE_FILE))) {

            writer.println("code,name,ects");

            for (Course course : catalog.getAllCourses()) {
                writer.println(
                        course.getCode() + "," +
                                course.getName() + "," +
                                course.getEcts()
                );
            }

        } catch (IOException e) {
            System.out.println("Course save error: " + e.getMessage());
        }
    }

    /**
     * Loads course registrations and rebuilds
     * student-course relationships.
     *
     * @param students student storage map
     * @param catalog  course catalog
     */
    private static void loadRegistrations(Map<String, Student> students,
                                          CourseCatalog catalog) {
        File file = new File(REG_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 2) continue;

                Student student = students.get(data[0]);
                Course course = catalog.getCourse(data[1]);

                if (student != null && course != null) {
                    student.registerCourse(course);
                }
            }

        } catch (IOException e) {
            System.out.println("Registration load error: " + e.getMessage());
        }
    }

    /**
     * Saves all student-course registrations into CSV file.
     *
     * @param students student storage map
     */
    private static void saveRegistrations(Map<String, Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(REG_FILE))) {

            writer.println("studentNumber,courseCode");

            for (Student student : students.values()) {
                for (Registration registration : student.registrations) {
                    writer.println(
                            student.getNumber() + "," +
                                    registration.getCourse().getCode()
                    );
                }
            }

        } catch (IOException e) {
            System.out.println("Registration save error: " + e.getMessage());
        }
    }
}
