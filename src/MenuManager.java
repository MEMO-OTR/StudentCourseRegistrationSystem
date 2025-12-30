import java.util.Map;
import java.util.Scanner;

/**
 * Displays and handles the Main Menu navigation.
 */
public class MenuManager {

    private final Scanner scanner;
    private final Map<String, Student> students;
    private final CourseCatalog courseCatalog;

    public MenuManager(Scanner scanner,
                       Map<String, Student> students,
                       CourseCatalog courseCatalog) {
        this.scanner = scanner;
        this.students = students;
        this.courseCatalog = courseCatalog;
    }

    /**
     * Forces user to enter a non-empty text input.
     *
     * @param message prompt message shown to user
     * @return non-empty user input
     */
    private String inputRequired(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("❌ This field cannot be empty. Please try again.");
        }
    }

    /**
     * Program entry menu (old Main loop).
     */
    public void start() {
        while (true) {
            System.out.println("\n--- Welcome To The Student Course Registration System ---");
            System.out.println("[+] Continue");
            System.out.println("[-] Exit");

            String s = inputRequired("Please Make A Selection (+/-) : ");

            if (s.equals("+")) mainMenu();
            else if (s.equals("-")) {
                System.out.println("...You Logged Out Goodbye...");
                return;
            } else System.out.println("❌ Invalid input ❌");
        }
    }

    /**
     * Displays and handles the Main Menu navigation.
     */
    private void mainMenu() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1) Student Operations");
            System.out.println("2) Course Registration");
            System.out.println("3) Add / Remove Course");
            System.out.println("4) Show Course List");
            System.out.println("5) Student Registration Check");
            System.out.println("6) Return To Main Screen\n");

            int sec;
            try {
                sec = Integer.parseInt(inputRequired("Please Select (1-6) : "));
            } catch (Exception e) {
                System.out.println("❌ Invalid Input...");
                continue;
            }

            switch (sec) {
                case 1 -> studentMenu();
                case 2 -> registerCourse();
                case 3 -> courseMenu();
                case 4 -> courseCatalog.showCourses();
                case 5 -> studentCheck();
                case 6 -> { return; }
                default -> System.out.println("❌ Invalid Input...");
            }
        }
    }

    /**
     * Student management submenu.
     * Allows adding, deleting, and editing students.
     */
    private void studentMenu() {
        while (true) {
            System.out.println("\n --- STUDENT MANAGEMENT ---");
            System.out.println("1) Add Student");
            System.out.println("2) Delete Student");
            System.out.println("3) Edit Student");
            System.out.println("4) Return To Main Menu\n");

            int sec;
            try {
                sec = Integer.parseInt(inputRequired("Please Select (1-4) : "));
            } catch (Exception e) {
                System.out.println("❌ Invalid Input...");
                continue;
            }

            switch (sec) {
                case 1 -> addStudent();
                case 2 -> deleteStudent();
                case 3 -> editStudent();
                case 4 -> { return; }
            }
        }
    }

    /**
     * Adds a new student to the system.
     */
    private void addStudent() {

        String type = inputRequired("Is This Student Undergraduate Or Gradute ? (U/G) :  ").toUpperCase();
        String no = inputRequired("Student Number : ");
        String name = inputRequired("Student Name : ");
        String surname = inputRequired("Student Surname : ");
        String dept = inputRequired("Student Department : ");

        Student st = type.equals("G")
                ? new GraduateStudent(no, name, surname, dept)
                : new Student(no, name, surname, dept, "U");

        students.put(no, st);
        FileManager.saveAll(students, courseCatalog);
        System.out.println("✔ Student Added Successfully...");
    }

    /**
     * Deletes a student using their student number.
     */
    private void deleteStudent() {
        String no = inputRequired("Enter The Student Number To Delete : ");
        Student removed = students.remove(no);

        if (removed == null) {
            System.out.println("❌ No Student Found With This Number");
        } else {
            FileManager.saveAll(students, courseCatalog);
            System.out.println("✔ Student Deleted...");
        }
    }

    /**
     * Edits the name, surname, and department of an existing student.
     */
    private void editStudent() {
        String no = inputRequired("Enter The Student Number To Edit : ");

        Student s = students.get(no);

        if (s == null) {
            System.out.println("❌ No Student Found With This Number...");
            return;
        }

        s.setName(inputRequired("New Student Name : "));
        s.setSurname(inputRequired("New Student Surname : "));
        s.setDepartment(inputRequired("New Student Department : "));

        FileManager.saveAll(students, courseCatalog);
        System.out.println("✔ Student Updated...");
    }

    /**
     * Handles course registration for a logged-in student.
     */
    private void registerCourse() {

        System.out.println("\n--- Course Registration Student Login ---");

        String no = inputRequired("Enter Student Number : ");
        Student st = students.get(no);

        if (st == null) {
            System.out.println("❌ Login Failed !!! No Student Found !!!");
            return;
        }

        System.out.println("Login Successful... Welcome..." + st.getName() + "!");
        courseCatalog.showCourses();

        String code = inputRequired("Enter Course Code To Register : ");
        Course c = courseCatalog.getCourse(code);

        if (c == null) {
            System.out.println("❌ No Such Course Exists...");
            return;
        }

        st.registerCourse(c);
        FileManager.saveAll(students, courseCatalog);
        System.out.println("✔ Course Registered");
    }

    /**
     * Displays the course operations menu (Add, Delete, Edit).
     */
    private void courseMenu() {
        while (true) {
            System.out.println("\n--- COURSE OPERATIONS ---");
            System.out.println("1) Add Course");
            System.out.println("2) Delete Course");
            System.out.println("3) Edit Course");
            System.out.println("4) Return To Main Menu\n");

            int sec;
            try {
                sec = Integer.parseInt(inputRequired("Please Select (1-4) : "));
            } catch (Exception e) {
                System.out.println("❌ Invalid Input...");
                continue;
            }

            switch (sec) {
                case 1 -> addCourse();
                case 2 -> deleteCourse();
                case 3 -> editCourse();
                case 4 -> { return; }
            }
        }
    }

    /**
     * Adds a new course into the course catalog.
     */
    private void addCourse() {
        String code = inputRequired("Course Code : ");
        String name = inputRequired("Course Name : ");
        int ects = Integer.parseInt(inputRequired("ECTS : "));

        courseCatalog.addCourse(new Course(code, name, ects));
        FileManager.saveAll(students, courseCatalog);
        System.out.println("✔ Course Added...");
    }

    /**
     * Deletes a course using its course code.
     */
    private void deleteCourse() {
        String code = inputRequired("Enter Course Code To Delete : ");

        boolean removed = courseCatalog.removeCourse(code);

        if (removed) {
            FileManager.saveAll(students, courseCatalog);
            System.out.println("✔ Course Deleted...");
        } else {
            System.out.println("❌ No Course Found With This Code...");
        }
    }

    /**
     * Edits the name and ECTS value of an existing course.
     */
    private void editCourse() {
        String code = inputRequired("Enter Course Code To Edit : ");

        Course c = courseCatalog.getCourse(code);

        if (c == null) {
            System.out.println("❌ No Course Found With This Code...");
            return;
        }

        c.setName(inputRequired("New Course Name : "));
        c.setEcts(Integer.parseInt(inputRequired("New ECTS : ")));

        FileManager.saveAll(students, courseCatalog);
        System.out.println("✔ Course Updated...");
    }

    /**
     * Displays a student's information, registered courses,
     * and calculated tuition fee.
     */
    private void studentCheck() {
        String no = inputRequired("Student Number : ");
        Student st = students.get(no);

        if (st == null) {
            System.out.println("❌ Student Not Found...");
            return;
        }

        System.out.println("\n--- STUDENT INFORMATION ---");
        System.out.println("Student Name : " + st.getName());
        System.out.println("Student Surname : " + st.getSurname());
        System.out.println("Student Department : " + st.getDepartment());
        System.out.println("Registered Courses : ");
        st.showCourses();
        System.out.println("Total Tuition Fee : " + st.calculateTuition() + "TL");
    }
}
