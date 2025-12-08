import java.io.*;
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






















}
