import java.util.Scanner;

class Student
{
  String name;
  int rollNumber;
  int[] marks = new int[3];

  Student (String name, int rollNumber, int[] marks)
  {
    this.name = name;
    this.rollNumber = rollNumber;
    this.marks = marks;
  }

  int
  getTotal ()
  {
    return marks[0] + marks[1] + marks[2];
  }

  double
  getAverage ()
  {
    return getTotal () / 3.0;
  }
}

public class StudentGradeManagementSystem
{
  static Scanner sc = new Scanner (System.in);
  static Student[] students = new Student[50];
  static int studentCount = 0;

  public static void
  main (String[] args)
  {
    int choice;
    do
      {
        showMenu ();
        choice = sc.nextInt ();
        sc.nextLine (); // consume newline
        switch (choice)
          {
                case 1 -> addStudent();
                case 2 -> updateMarks();
                case 3 -> removeStudent();
                case 4 -> viewAllStudents();
                case 5 -> searchStudent();
                case 6 -> highestScorer();
                case 7 -> classAverage();
                case 8 -> exitProgram();
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 8);
    }

    static void showMenu() {
        System.out.println("\n--- Student Grade Management System ---");
        System.out.println("1. Add Student");
        System.out.println("2. Update Marks");
        System.out.println("3. Remove Student");
        System.out.println("4. View All Students");
        System.out.println("5. Search Student");
        System.out.println("6. Highest Scorer");
        System.out.println("7. Class Average");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    static void addStudent() {
        if (studentCount >= 50) {
            System.out.println("Maximum student limit reached!");
            return;
        }

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter roll number: ");
        int rollNumber = sc.nextInt();
        sc.nextLine(); // consume newline

        if (findStudentIndex(rollNumber) != -1) {
            System.out.println("Roll number already exists! Cannot add student.");
            return;
        }

        int[] marks = new int[3];
        for (int i = 0; i < 3; i++) {
            do {
                System.out.print("Enter marks for subject " + (i + 1) + " (0-100): ");
                marks[i] = sc.nextInt();
                if (marks[i] < 0 || marks[i] > 100) {
                    System.out.println("Invalid marks! Enter again.");
                }
            } while (marks[i] < 0 || marks[i] > 100);
        }
        sc.nextLine(); // consume newline

        students[studentCount++] = new Student(name, rollNumber, marks);
        System.out.println("Student added successfully!");
    }

    static void updateMarks() {
        System.out.print("Enter roll number to update marks: ");
        int roll = sc.nextInt();
        sc.nextLine();
        int index = findStudentIndex(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        int[] marks = new int[3];
        for (int i = 0; i < 3; i++) {
            do {
                System.out.print("Enter new marks for subject " + (i + 1) + " (0-100): ");
                marks[i] = sc.nextInt();
                if (marks[i] < 0 || marks[i] > 100) {
                    System.out.println("Invalid marks! Enter again.");
                }
            } while (marks[i] < 0 || marks[i] > 100);
        }
        sc.nextLine(); // consume newline

        students[index].marks = marks;
        System.out.println("Marks updated successfully!");
    }

    static void removeStudent() {
        System.out.print("Enter roll number to remove: ");
        int roll = sc.nextInt();
        sc.nextLine();
        int index = findStudentIndex(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        // Shift students left to remove
        for (int i = index; i < studentCount - 1; i++) {
            students[i] = students[i + 1];
        }
        students[--studentCount] = null;
        System.out.println("Student removed successfully!");
    }

    static void viewAllStudents() {
        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        System.out.printf("%-10s %-20s %-10s %-10s %-10s %-10s %-10s\n",
                "Roll No", "Name", "Sub1", "Sub2", "Sub3", "Total", "Average");
        for (int i = 0; i < studentCount; i++) {
            Student s = students[i];
            System.out.printf("%-10d %-20s %-10d %-10d %-10d %-10d %-10.2f\n",
                    s.rollNumber, s.name, s.marks[0], s.marks[1], s.marks[2], s.getTotal(), s.getAverage());
        }
    }

    static void searchStudent() {
        System.out.print("Enter roll number to search: ");
        int roll = sc.nextInt();
        sc.nextLine();
        int index = findStudentIndex(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        Student s = students[index];
        System.out.printf("Roll No: %d, Name: %s, Marks: %d, %d, %d, Total: %d, Average: %.2f\n",
                s.rollNumber, s.name, s.marks[0], s.marks[1], s.marks[2], s.getTotal(), s.getAverage());
    }

    static void highestScorer() {
        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        Student top = students[0];
        for (int i = 1; i < studentCount; i++) {
            if (students[i].getTotal() > top.getTotal()) {
                top = students[i];
            }
        }

        System.out.println("Highest Scorer:");
        System.out.printf("Roll No: %d, Name: %s, Total: %d, Average: %.2f\n",
                top.rollNumber, top.name, top.getTotal(), top.getAverage());
    }

    static void classAverage() {
        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        double sum = 0;
        for (int i = 0; i < studentCount; i++) {
            sum += students[i].getAverage();
        }
        System.out.printf("Class Average: %.2f\n", sum / studentCount);
    }

    static void exitProgram() {
        System.out.println("Exiting program...");
        System.out.println("Total students: " + studentCount);
        classAverage();
    }

    static int findStudentIndex(int rollNumber) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].rollNumber == rollNumber) {
                return i;
            }
        }
        return -1;
    }
}
