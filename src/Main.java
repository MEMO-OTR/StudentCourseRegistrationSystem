import java.io.*;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static CourseCatalog courseCatalog = new CourseCatalog();
    private static Map<String, Student> students = new HashMap<>();

    private static String inputRequired(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("❌ This field cannot be empty. Please try again.");
        }
    }

    private static void saveStudents() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("students.dat"));
            out.writeObject(students);
            out.close();
        } catch (Exception e) {
            System.out.println("❌ Failed to save students: " + e.getMessage());
        }
    }

    private static void loadStudents() {
        try {
            File file = new File("students.dat");
            if (!file.exists()) return;

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            students = (Map<String, Student>) in.readObject();
            in.close();

        } catch (Exception e) {
            System.out.println("❌ Failed to load students: " + e.getMessage());
        }
    }

    private static void saveCourses() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("courses.dat"));
            out.writeObject(courseCatalog);
            out.close();
        } catch (Exception e) {
            System.out.println("❌ Failed to save courses: " + e.getMessage());
        }
    }

    private static void loadCourses() {
        try {
            File file = new File("courses.dat");
            if (!file.exists()) return;

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            courseCatalog = (CourseCatalog) in.readObject();
            in.close();

        } catch (Exception e) {
            System.out.println("❌ Failed to load courses: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        loadStudents();
        loadCourses();

        while (true) {
            System.out.println("\n--- Welcome to the Student Course Registration System ---");
            System.out.println("[+] Continue");
            System.out.println("[-] Exit");

            String s = inputRequired("Please Make A Selection (+/-) : ");

            if (s.equals("+")) mainMenu();
            else if (s.equals("-")){
                saveStudents();
                saveCourses();
                System.out.println("You logged out Goodbye...");
                break;
            }
            else System.out.println("❌ Invalid input ❌");
        }
    }

    private static void mainMenu() {
        while(true){
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1) Student Operations");
            System.out.println("2) Course Registration");
            System.out.println("3) Add / Remove Course");
            System.out.println("4) Show Course List");
            System.out.println("5) Student Registration Check");
            System.out.println("6) Return to Main Screen\n");

            int sec = Integer.parseInt(inputRequired("Please Select (1-6) : "));

            switch (sec) {
                case 1 -> studentMenu();
                case 2 -> registerCourse();
                case 3 -> courseMenu();
                case 4 -> courseCatalog.showCourses();
                case 5 -> studentCheck();
                case 6 -> { return; }
                default -> System.out.println("❌ Invalid input ❌");
            }

        }
    }

    private static void studentMenu() {
        while (true) {
            System.out.println("\n --- STUDENT MANAGEMENT ---");
            System.out.println("1) Add Student");
            System.out.println("2) Delete Student");
            System.out.println("3) Edit Student");
            System.out.println("4) Return To Main Menu\n");

            int sec = Integer.parseInt(inputRequired("Please Select (1-4) : "));

            switch (sec) {
                case 1 -> loadStudents();
                case 2 -> deleteStudent();
                case 3 -> editStudent();
                case 4 -> {return;}
            }

        }

    }

    private static void addStudent() {

        String type = inputRequired("Is This Student Undergraduate Or Gradute ? (U/H) ").toUpperCase();
        String no = inputRequired("Student Number : ");
        String name = inputRequired("First Name : ");
        String surname = inputRequired("Last Name : ");
        String dept = inputRequired("Department : ");

        Student st = type.equals("G")
                ? new GraduateStudent(no, name, surname, dept)
                : new Student(no, name, surname, dept);

        students.put(no, st);
        saveStudents();
        System.out.println("✔ Student Added Successfully...");
    }

    private static void deleteStudent(){
        String no = inputRequired("Enter The Student Number To Delete : ");
        Student removed = students.remove(no);

        if (removed == null){
            System.out.println("❌ No Student Found With This Number");
        }else {
            saveStudents();
            System.out.println("✔ Student Deleted...");
        }
    }

    private static void editStudent() {
        String no = inputRequired("Enter The Student Number To Edit : ");

        Student s = students.get(no);

        if (s == null) {
            System.out.println("❌ No Student Found With This Number...");
            return;
        }
        s.setName(inputRequired("New First Name : "));
        s.setSurname(inputRequired("New Last Name : "));
        s.setDepartment(inputRequired("New Department : "));

        saveStudents();
        System.out.println("✔ Student Updated...");
    }

    private static void registerCourse() {

        System.out.println("\n--- Course Registration Student Login ---");

        String no = inputRequired("Enter Student Number : ");
        Student st = students.get(no);

        if (st == null) {
            System.out.println("❌ Login Failed !!! No Student Found !!!");
            return;
        }
        System.out.println("Login Successful... Welcome..." + st.getName() + "!");
        courseCatalog.showCourses();

        String code = inputRequired("Enet Course Code To Register : ");
        Course c = courseCatalog.getCourse(code);

        if (c == null) {
            System.out.println("❌ No Such Course Exists...");
            return;
        }
        st.registerCourse(c);
        saveStudents();
    }

    private static void courseMenu(){
        while (true){
            System.out.println("\n--- COURSE OPERATIONS ---");
            System.out.println("1) Add Course");
            System.out.println("2) Delete Course");
            System.out.println("3) Edit Course");
            System.out.println("4) Return To Main Menu\n");

            int sec = Integer.parseInt(inputRequired("Please Select (1-4) : "));

            switch (sec) {
                case 1 -> addCourse();
                case 2 -> deleteCourse();
                case 3 -> editCourse();
                case 4 -> {return;}

            }
        }
    }

    private static void addCourse() {
        String code = inputRequired("Course Code : ");
        String name = inputRequired("Course Name : ");
        int ects = Integer.parseInt(inputRequired("ECTS : "));

        courseCatalog.addCourse(new Course(code, name, ects));
        saveCourses();
        System.out.println("✔ Course Added...");
    }

    private static void deleteCourse() {
        String code = inputRequired("Enter Course Code To Delete : ");

        boolean removed = courseCatalog.removeCourse(code);

        if (removed){
            saveCourses();
            System.out.println("✔ Course Deleted...");
        } else {
            System.out.println("❌ No Course Found With This Code...");
        }
    }

    private static void editCourse(){
        String code = inputRequired("Enter Course Code To Edit : ");

        Course c = courseCatalog.getCourse(code);

        if (c == null) {
            System.out.println("❌ No Course Found With This Code...");
            return;
        }

        c.setName(inputRequired("New Course Name : "));
        c.setEcts(Integer.parseInt(inputRequired("New ECTS : ")));

        saveCourses();
        System.out.println("✔ Course Updated...");
    }

    private static void studentCheck() {
        String no = inputRequired("Student Number : ");
        Student st = students.get(no);

        if (st == null){
            System.out.println("❌ Student Not Found...");
            return;
        }

        System.out.println("\n--- STUDENT INFORMATION ---");
        System.out.println("First Name : " + st.getName());
        System.out.println("Surname : " + st.getSurname());
        System.out.println("Department : " + st.getDepartment());
        System.out.println("Registered Courses : ");
        st.showCourses();
        System.out.println("Total Tuition Fee : " + st.calculateTuition() + "TL");
    }
}
