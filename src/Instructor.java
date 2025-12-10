/**
 * Represents an instructor working in a specific department.
 * Stores basic identity information such as name and department.
 */
public class Instructor {

    private String name;
    private String department;

    /**
     * Creates a new Instructor object with the given name and department.
     *
     * @param name       instructor's full name
     * @param department instructor's academic department
     */
    public Instructor(String name, String department) {
        this.name = name;
        this.department = department;
    }

    /**
     * Returns the instructor's name.
     *
     * @return instructor name
     */
    public String getName() { return name; }

    /**
     * Returns the instructor's department.
     *
     * @return department name
     */
    public String getDepartment() { return department; }

    /**
     * Updates the instructor's name.
     *
     * @param name new instructor name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Updates the instructor's department.
     *
     * @param department new department name
     */
    public void setDepartment(String department) { this.department = department; }

    /**
     * Returns a readable string representation of the instructor.
     *
     * @return formatted instructor information
     */
    @Override
    public String toString() {
        return name + " (" + department + ")";
    }
}
