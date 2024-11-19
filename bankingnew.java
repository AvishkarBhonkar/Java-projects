import java.util.*;

class User {
    String userId;
    String name;
    String password;
    String accountNumber;
    double balance;

    public User(String userId, String name, String password, String accountNumber) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public void displayAccountStatement() {
        System.out.println("Account Statement for User: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: " + balance);
    }
}

class BankSystem {
    private Map<String, User> users = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    // User Registration
    public void registerUser() {
        System.out.print("Enter Adhar card Number ");
        String userId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        String accountNumber = generateAccountNumber();
        User newUser = new User(userId, name, password, accountNumber);
        users.put(userId, newUser);
        System.out.println("User registered successfully. Account Number: " + accountNumber);
    }

    // Login System
    public User login() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        User user = users.get(userId);
        if (user != null && user.password.equals(password)) {
            System.out.println("Login successful.");
            return user;
        } else {
            System.out.println("Invalid credentials.");
            return null;
        }
    }

    //  Deposit and Withdrawal
    public void deposit(User user) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        user.deposit(amount);
        System.out.println("Deposit successful. New Balance: " + user.getBalance());
    }

    public void withdraw(User user) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (user.withdraw(amount)) {
            System.out.println("Withdrawal successful. New Balance: " + user.getBalance());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    //  Fund Transfer
    public void fundTransfer(User user) {
        System.out.print("Enter recipient's account number: ");
        String recipientAccount = scanner.next();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        User recipient = null;
        for (User u : users.values()) {
            if (u.getAccountNumber().equals(recipientAccount)) {
                recipient = u;
                break;
            }
        }

        if (recipient != null && user.withdraw(amount)) {
            recipient.deposit(amount);
            System.out.println("Transfer successful. New Balance: " + user.getBalance());
        } else {
            System.out.println("Transfer failed. Check account details or balance.");
        }
    }

    // Account Statements
    public void displayAccountStatement(User user) {
        user.displayAccountStatement();
    }

    // Generate Unique Account Number
    private String generateAccountNumber() {
        return "AC" + new Random().nextInt(100000000);
    }

    // Main Menu
    public void showMenu(User user) {
        while (true) {
            System.out.println("\n--- Banking System Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Fund Transfer");
            System.out.println("4. Account Statement");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    deposit(user);
                    break;
                case 2:
                    withdraw(user);
                    break;
                case 3:
                    fundTransfer(user);
                    break;
                case 4:
                    displayAccountStatement(user);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

public class bankingnew {
    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Welcome to the Banking System ---");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    bankSystem.registerUser();
                    break;
                case 2:
                    User user = bankSystem.login();
                    if (user != null) {
                        bankSystem.showMenu(user);
                    }
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
