import java.util.Scanner;

/**
 * BankAccount Class (The Data Vault & Logic)
 * Encapsulates account data and enforces business/security rules.
 */
class BankAccount {
    private int accountNumber;
    private double balance;
    private int pin; // Encapsulated private PIN

    // Constructor
    public BankAccount(int accountNumber, double initialBalance, int pin) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.pin = pin;
    }

    // Security checkpoint: Validate entered PIN against the private state
    public boolean validatePin(int enteredPin) {
        return this.pin == enteredPin;
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Deposit method with validation
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount. Must be greater than zero.");
        }
    }

    // Withdrawal method enforcing overdraft rules
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrew: $" + amount);
            return true;
        } else {
            System.out.println("Transaction Failed: Insufficient funds or invalid amount.");
            return false;
        }
    }
}

/**
 * Main ATM System Controller (The Interface & IPO Model)
 * Handles user interactions, console input validation, and menus.
 */
public class DecodeLabs_Java_P3{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initialize sample bank account with Account No: 101, Initial Balance: $5000.0, PIN: 1234
        BankAccount account = new BankAccount(101, 5000.0, 1234);

        System.out.println("=== WELCOME TO DECODELABS ATM (PROJECT 3) ===");
        
        // PIN Authentication Loop (Security Gate - Max 3 attempts)
        int maxAttempts = 3;
        boolean isAuthenticated = false;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.print("Please enter your 4-digit PIN: ");
            
            // Robust input validation gate (prevents InputMismatchException crash)
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input format. Please enter numbers only: ");
                scanner.next(); // Clear invalid token from buffer
            }
            
            int enteredPin = scanner.nextInt();

            // Check credentials via the BankAccount class method
            if (account.validatePin(enteredPin)) {
                isAuthenticated = true;
                System.out.println("Authentication Successful!\n");
                break;
            } else {
                int remaining = maxAttempts - attempt;
                System.out.println("Incorrect PIN. Remaining attempts: " + remaining);
            }
        }

        // Lock out if attempts run out
        if (!isAuthenticated) {
            System.out.println("Card Blocked. Too many incorrect attempts.");
            scanner.close();
            return;
        }

        // Main Transaction Loop
        int choice;
        do {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            // Validation gate for menu choices
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid choice. Please enter a number between 1 and 4: ");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: $" + account.getBalance());
                    break;
                    
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.print("Invalid amount. Enter a valid numerical value: ");
                        scanner.next();
                    }
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                    
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.print("Invalid amount. Enter a valid numerical value: ");
                        scanner.next();
                    }
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                    
                case 4:
                    System.out.println("Thank you for using DecodeLabs ATM. Please take your card.");
                    break;
                    
                default:
                    System.out.println("Invalid option. Please choose between 1 and 4.");
            }
        } while (choice != 4);

        scanner.close();
    }
}