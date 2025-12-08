import java.time.LocalDate;

public class Registration implements java.io.Serializable{

    private Student student;
    private Course course;
    private LocalDate date;

    public Registration(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.date = LocalDate.now();
    }

    public Student getStudent() {return student;}
    public Course getCourse() {return course;}
    public LocalDate getDate() {return date;}
}
