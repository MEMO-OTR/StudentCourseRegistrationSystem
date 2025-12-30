import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map<String, Student> students = new HashMap<>();
        CourseCatalog courseCatalog = new CourseCatalog();

        FileManager.loadAll(students, courseCatalog);

        MenuManager menu = new MenuManager(scanner, students, courseCatalog);
        menu.start();

        FileManager.saveAll(students, courseCatalog);

        System.out.println("System closed. Goodbye.");
    }
}
