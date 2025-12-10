/**
 * Represents a university course that contains a code, name, and ECTS credit value.
 * This class stores basic course information used in the registration system.
 */
public class Course {

    private String code;
    private String name;
    private int ects;

    /**
     * Creates a new Course object with the given code, name, and ECTS value.
     *
     * @param code the unique course code
     * @param name the full descriptive name of the course
     * @param ects the ECTS credit value of the course
     */
    public Course(String code, String name, int ects) {
        this.code = code.toUpperCase();
        this.name = name;
        this.ects = ects;
    }

    /**
     * Returns the course code.
     *
     * @return the course code in uppercase format
     */
    public String getCode() { return code; }

    /**
     * Returns the course name.
     *
     * @return the course name
     */
    public String getName() { return name; }

    /**
     * Returns the ECTS credit value of the course.
     *
     * @return ECTS value
     */
    public int getEcts() { return ects; }

    /**
     * Updates the course name.
     *
     * @param name the new course name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Updates the ECTS credit value.
     *
     * @param ects the new ECTS value
     */
    public void setEcts(int ects) { this.ects = ects; }
}
