public class GraduateStudent extends Student {

    public GraduateStudent(String number, String name, String surname, String department) {
        super(number, name, surname, department, "G");
    }

    @Override
    public double calculateTuition() {
        return super.calculateTuition() * 2;
    }
}
