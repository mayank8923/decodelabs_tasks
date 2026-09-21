import java.util.Scanner;

public class DecodeLabs_Java_P2 {
    public static void main(String[] args) {
        GradeCalculator calculator = new GradeCalculator();
        calculator.calculateResult();
    }
}

class GradeCalculator {
    private final Scanner scanner;

    public GradeCalculator() {
        this.scanner = new Scanner(System.in);
    }

    public void calculateResult() {
        int numSubjects = getValidSubjectCount();
        double totalMarks = 0;

        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Enter Marks for Subject " + i + " (out of 100): ");
            double marks = getValidMarks();
            totalMarks += marks;
        }

        double averagePercentage = totalMarks / numSubjects;
        char grade = determineGrade(averagePercentage);

        StudentResult result = new StudentResult(numSubjects, totalMarks, averagePercentage, grade);
        result.printReport();
        scanner.close();
    }

    private int getValidSubjectCount() {
        System.out.print("Enter the Number of Subjects: ");
        int numSubjects = scanner.nextInt();

        while (numSubjects <= 0) {
            System.out.print("Invalid input: Enter a positive number of Subjects: ");
            numSubjects = scanner.nextInt();
        }

        return numSubjects;
    }

    private double getValidMarks() {
        double marks = scanner.nextDouble();

        while (marks < 0 || marks > 100) {
            System.out.print("Invalid Marks. Please enter marks between 0 and 100: ");
            marks = scanner.nextDouble();
        }

        return marks;
    }

    private char determineGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return 'A';
        } else if (averagePercentage >= 80) {
            return 'B';
        } else if (averagePercentage >= 70) {
            return 'C';
        } else if (averagePercentage >= 60) {
            return 'D';
        } else if (averagePercentage >= 50) {
            return 'E';
        } else {
            return 'F';
        }
    }
}

class StudentResult {
    private final int numSubjects;
    private final double totalMarks;
    private final double averagePercentage;
    private final char grade;

    public StudentResult(int numSubjects, double totalMarks, double averagePercentage, char grade) {
        this.numSubjects = numSubjects;
        this.totalMarks = totalMarks;
        this.averagePercentage = averagePercentage;
        this.grade = grade;
    }

    public void printReport() {
        System.out.println("\n----RESULTS----");
        System.out.println("Total Marks Obtained: " + totalMarks + " / " + (numSubjects * 100));
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Grade Assigned: " + grade);
        System.out.println("--------");
    }
}
