public class Course {

    private String code;
    private String name;
    private int ects;

    public Course(String code, String name, int ects) {
        this.code = code.toUpperCase();
        this.name = name;
        this.ects = ects;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public int getEcts() { return ects; }

    public void setName(String name) { this.name = name; }
    public void setEcts(int ects) { this.ects = ects; }
}
