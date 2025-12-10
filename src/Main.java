import java.io.*;
import java.util.*;

/**
 * Main class of the Student Course Registration System.
 * Handles menus, file operations, student and course management.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static CourseCatalog courseCatalog = new CourseCatalog();
    private static Map<String, Student> students = new HashMap<>();

    private static final String STUDENT_FILE = "students.csv";
    private static final String COURSE_FILE = "courses.csv";
    private static final String REG_FILE = "registrations.csv";

    /**
     * Forces user to enter a non-empty text input.
     *
     * @param message prompt message shown to user
     * @return non-empty user input
     */
    private static String inputRequired(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("❌ This field cannot be empty. Please try again.");
        }
    }

    /**
     * Saves all students into students.csv file.
     */
    private static void saveStudentsCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(STUDENT_FILE))) {
            pw.println("number,name,surname,department,type");
            for (Student s : students.values()) {
                pw.println(s.getNumber() + "," +
                        s.getName() + "," +
                        s.getSurname() + "," +
                        s.getDepartment() + "," +
                        s.getType());
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to save students CSV: " + e.getMessage());
        }
    }

    /**
     * Loads all students from students.csv file.
     */
    private static void loadStudentsCSV() {
        try {
            File file = new File(STUDENT_FILE);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {

                String[] arr = line.split(",");
                if (arr.length < 5) continue;

                String number = arr[0];
                String name = arr[1];
                String surname = arr[2];
                String dept = arr[3];
                String type = arr[4];

                Student s = type.equals("G")
                        ? new GraduateStudent(number, name, surname, dept)
                        : new Student(number, name, surname, dept, "U");

                students.put(number, s);
            }

            br.close();

        } catch (Exception e) {
            System.out.println("❌ Failed to load students CSV: " + e.getMessage());
        }
    }

    /**
     * Saves all courses into courses.csv.
     */
    private static void saveCoursesCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(COURSE_FILE))) {
            pw.println("code,name,ects");
            for (Course c : courseCatalog.getAllCourses()) {
                pw.println(c.getCode() + "," + c.getName() + "," + c.getEcts());
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to save courses CSV: " + e.getMessage());
        }
    }

    /**
     * Loads all courses from courses.csv.
     */
    private static void loadCoursesCSV() {
        try {
            File file = new File(COURSE_FILE);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {

                String[] arr = line.split(",");
                if (arr.length < 3) continue;

                Course c = new Course(arr[0], arr[1], Integer.parseInt(arr[2]));
                courseCatalog.addCourse(c);
            }
            br.close();

        } catch (Exception e) {
            System.out.println("❌ Failed to load courses CSV: " + e.getMessage());
        }
    }

    /**
     * Saves all student–course registrations into registrations.csv.
     */
    private static void saveRegistrationsCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(REG_FILE))) {
            pw.println("studentNumber,courseCode");

            for (Student s : students.values()) {
                for (Registration r : s.registrations) {
                    pw.println(s.getNumber() + "," + r.getCourse().getCode());
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to save registrations CSV: " + e.getMessage());
        }
    }

    /**
     * Loads all registration records from registrations.csv
     * and recreates student-course relationships.
     */
    private static void loadRegistrationsCSV() {
        try {
            File file = new File(REG_FILE);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); // header skip

            String line;
            while ((line = br.readLine()) != null) {

                String[] arr = line.split(",");
                if (arr.length < 2) continue;

                String number = arr[0];
                String courseCode = arr[1];

                Student s = students.get(number);
                Course c = courseCatalog.getCourse(courseCode);

                if (s != null && c != null) {
                    s.registerCourse(c);
                }
            }

            br.close();

        } catch (Exception e) {
            System.out.println("❌ Failed to load registrations CSV: " + e.getMessage());
        }
    }

    /**
     * Program entry point. Loads all data and opens main system loop.
     *
     * @param args program arguments (unused)
     */
    public static void main(String[] args) {

        loadStudentsCSV();
        loadCoursesCSV();
        loadRegistrationsCSV();

        while (true) {
            System.out.println("\n--- Welcome To The Student Course Registration System ---");
            System.out.println("[+] Continue");
            System.out.println("[-] Exit");

            String s = inputRequired("Please Make A Selection (+/-) : ");

            if (s.equals("+")) mainMenu();
            else if (s.equals("-")) {

                saveStudentsCSV();
                saveCoursesCSV();
                saveRegistrationsCSV();

                System.out.println("...You Logged Out Goodbye...");
                break;
            }
            else System.out.println("❌ Invalid input ❌");
        }
    }

    /**
     * Displays and handles the Main Menu navigation.
     */
    private static void mainMenu() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1) Student Operations");
            System.out.println("2) Course Registration");
            System.out.println("3) Add / Remove Course");
            System.out.println("4) Show Course List");
            System.out.println("5) Student Registration Check");
            System.out.println("6) Return To Main Screen\n");

            int sec = Integer.parseInt(inputRequired("Please Select (1-6) : "));

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
    private static void studentMenu() {
        while (true) {
            System.out.println("\n --- STUDENT MANAGEMENT ---");
            System.out.println("1) Add Student");
            System.out.println("2) Delete Student");
            System.out.println("3) Edit Student");
            System.out.println("4) Return To Main Menu\n");

            int sec = Integer.parseInt(inputRequired("Please Select (1-4) : "));

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
    private static void addStudent() {

        String type = inputRequired("Is This Student Undergraduate Or Gradute ? (U/G) ").toUpperCase();
        String no = inputRequired("Student Number : ");
        String name = inputRequired("Student Name : ");
        String surname = inputRequired("Student Surname : ");
        String dept = inputRequired("Student Department : ");

        Student st = type.equals("G")
                ? new GraduateStudent(no, name, surname, dept)
                : new Student(no, name, surname, dept, "U");

        students.put(no, st);
        saveStudentsCSV();
        System.out.println("✔ Student Added Successfully...");
    }

    /**
     * Deletes a student using their student number.
     */
    private static void deleteStudent() {
        String no = inputRequired("Enter The Student Number To Delete : ");
        Student removed = students.remove(no);

        if (removed == null) {
            System.out.println("❌ No Student Found With This Number");
        } else {
            saveStudentsCSV();
            saveRegistrationsCSV();
            System.out.println("✔ Student Deleted...");
        }
    }

    /**
     * Edits the name, surname, and department of an existing student.
     */
    private static void editStudent() {
        String no = inputRequired("Enter The Student Number To Edit : ");

        Student s = students.get(no);

        if (s == null) {
            System.out.println("❌ No Student Found With This Number...");
            return;
        }

        s.setName(inputRequired("New Student Name : "));
        s.setSurname(inputRequired("New Student Surname : "));
        s.setDepartment(inputRequired("New Student Department : "));

        saveStudentsCSV();
        System.out.println("✔ Student Updated...");
    }

    /**
     * Handles course registration for a logged-in student.
     */
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

        String code = inputRequired("Enter Course Code To Register : ");
        Course c = courseCatalog.getCourse(code);

        if (c == null) {
            System.out.println("❌ No Such Course Exists...");
            return;
        }

        st.registerCourse(c);

        saveRegistrationsCSV();
        System.out.println("✔ Course Registered");
    }

    /**
     * Displays the course operations menu (Add, Delete, Edit).
     */
    private static void courseMenu() {
        while (true) {
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
                case 4 -> { return; }
            }
        }
    }

    /**
     * Adds a new course into the course catalog.
     */
    private static void addCourse() {
        String code = inputRequired("Course Code : ");
        String name = inputRequired("Course Name : ");
        int ects = Integer.parseInt(inputRequired("ECTS : "));

        courseCatalog.addCourse(new Course(code, name, ects));
        saveCoursesCSV();
        System.out.println("✔ Course Added...");
    }

    /**
     * Deletes a course using its course code.
     */
    private static void deleteCourse() {
        String code = inputRequired("Enter Course Code To Delete : ");

        boolean removed = courseCatalog.removeCourse(code);

        if (removed) {
            saveCoursesCSV();
            saveRegistrationsCSV();
            System.out.println("✔ Course Deleted...");
        } else {
            System.out.println("❌ No Course Found With This Code...");
        }
    }

    /**
     * Edits the name and ECTS value of an existing course.
     */
    private static void editCourse() {
        String code = inputRequired("Enter Course Code To Edit : ");

        Course c = courseCatalog.getCourse(code);

        if (c == null) {
            System.out.println("❌ No Course Found With This Code...");
            return;
        }

        c.setName(inputRequired("New Course Name : "));
        c.setEcts(Integer.parseInt(inputRequired("New ECTS : ")));

        saveCoursesCSV();
        System.out.println("✔ Course Updated...");
    }

    /**
     * Displays a student's information, registered courses,
     * and calculated tuition fee.
     */
    private static void studentCheck() {
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
