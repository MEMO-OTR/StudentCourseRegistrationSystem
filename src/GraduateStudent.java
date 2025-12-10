/**
 * Represents a graduate-level student.
 * Graduate students pay a higher tuition rate compared to undergraduate students.
 */
public class GraduateStudent extends Student {

    /**
     * Creates a new GraduateStudent object.
     *
     * @param number     student ID number
     * @param name       student's first name
     * @param surname    student's last name
     * @param department student's department
     */
    public GraduateStudent(String number, String name, String surname, String department) {
        super(number, name, surname, department, "G");
    }

    /**
     * Calculates the tuition fee for a graduate student.
     * Graduate tuition is twice the undergraduate rate.
     *
     * @return the calculated tuition fee for this graduate student
     */
    @Override
    public double calculateTuition() {
        return super.calculateTuition() * 2;
    }
}
