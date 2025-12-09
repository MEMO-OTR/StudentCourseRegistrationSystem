public class GraduateStudent extends Student implements java.io.Serializable {

    public GraduateStudent(String number, String name, String surname, String department){
        super(number, name, surname, department);
    }

    @Override
    public double calculateTuition() {
        return registrations.size() * 5.350;
    }
}
