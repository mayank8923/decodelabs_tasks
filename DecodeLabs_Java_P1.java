import java.util.Random;
import java.util.Scanner;

public class DecodeLabs_Java_P1 {
    public static void main(String[] args) {
        GuessingGame game = new GuessingGame();
        game.start();
    }
}

class GuessingGame {
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;
    private static final int MAX_ATTEMPTS = 10;

    private final Scanner scanner;
    private final Random random;

    public GuessingGame() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    public void start() {
        int totalRounds = 0;
        int roundsWon = 0;
        boolean playAgain = true;

        System.out.println("Welcome to The Guessing Game");

        while (playAgain) {
            totalRounds++;
            int targetNumber = generateTargetNumber();
            boolean guessedCorrectly = playRound(targetNumber, totalRounds);

            if (guessedCorrectly) {
                roundsWon++;
            }

            playAgain = askToPlayAgain();
        }

        System.out.println("\n=== Game Over ===");
        System.out.println("Total rounds played: " + totalRounds);
        System.out.println("Total rounds won: " + roundsWon);

        scanner.close();
    }

    private int generateTargetNumber() {
        return random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
    }

    private boolean playRound(int targetNumber, int roundNumber) {
        int attempts = 0;
        boolean guessedCorrectly = false;

        System.out.println("\n--- Round " + roundNumber + " ---");
        System.out.println("I have generated a number between " + MIN_RANGE + " and " + MAX_RANGE + ".");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess it.");

        while (attempts < MAX_ATTEMPTS && !guessedCorrectly) {
            System.out.print("Enter your guess: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
                continue;
            }

            int guess = scanner.nextInt();
            attempts++;

            if (guess == targetNumber) {
                System.out.println("Congratulations! You guessed the correct number in " + attempts + " attempts.");
                guessedCorrectly = true;
            } else if (guess < targetNumber) {
                System.out.println("Too low! Attempts remaining: " + (MAX_ATTEMPTS - attempts));
            } else {
                System.out.println("Too high! Attempts remaining: " + (MAX_ATTEMPTS - attempts));
            }
        }

        if (!guessedCorrectly) {
            System.out.println("You've used all your attempts. The correct number was: " + targetNumber);
        }

        return guessedCorrectly;
    }

    private boolean askToPlayAgain() {
        System.out.print("\nDo you want to play another round? (yes/no): ");
        String response = scanner.next().toLowerCase();
        return response.startsWith("y");
    }
}


