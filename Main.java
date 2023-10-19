import java.util.ArrayList;
import java.util.Scanner;

class Account {
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account() {
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void transfer(double amount, Account recipient) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer", amount));
        } else {
            System.out.println("Insufficient funds for transfer!");
        }
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": " + amount;
    }
}

class User {
    private String userId;
    private String pin;
    private Account account;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.account = new Account();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public Account getAccount() {
        return account;
    }
}

class ATM {
    private ArrayList<User> users;
    private User currentUser;

    public ATM() {
        users = new ArrayList<>();
        // Add sample users
        users.add(new User("12345", "1234"));
        users.add(new User("67890", "5678"));
    }

    public boolean login(String userId, String pin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPin().equals(pin)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void displayMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Transaction History");
        System.out.println("6. Quit");
        System.out.print("Enter your choice: ");
    }

    public void performTransaction(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                System.out.println("Balance: $" + currentUser.getAccount().getBalance());
                break;
            case 2:
                System.out.print("Enter deposit amount: $");
                double depositAmount = scanner.nextDouble();
                currentUser.getAccount().deposit(depositAmount);
                System.out.println("Deposited: $" + depositAmount);
                break;
            case 3:
                System.out.print("Enter withdrawal amount: $");
                double withdrawalAmount = scanner.nextDouble();
                currentUser.getAccount().withdraw(withdrawalAmount);
                break;
            case 4:
                System.out.print("Enter recipient's user ID: ");
                String recipientId = scanner.next();
                User recipient = findUser(recipientId);
                if (recipient != null) {
                    System.out.print("Enter transfer amount: $");
                    double transferAmount = scanner.nextDouble();
                    currentUser.getAccount().transfer(transferAmount, recipient.getAccount());
                    System.out.println("Transferred: $" + transferAmount);
                } else {
                    System.out.println("Recipient not found.");
                }
                break;
            case 5:
                ArrayList<Transaction> transactions = currentUser.getAccount().getTransactionHistory();
                System.out.println("Transaction History:");
                for (Transaction transaction : transactions) {
                    System.out.println(transaction);
                }
                break;
            case 6:
                System.out.println("Thank you for using the ATM. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private User findUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        System.out.println("Welcome to the ATM!");
        while (true) {
            System.out.print("Enter User ID: ");
            String userId = scanner.next();
            System.out.print("Enter PIN: ");
            String pin = scanner.next();

            if (atm.login(userId, pin)) {
                System.out.println("Login successful!");
                while (true) {
                    atm.displayMenu();
                    int choice = scanner.nextInt();
                    atm.performTransaction(choice, scanner);
                }
            } else {
                System.out.println("Invalid User ID or PIN. Please try again.");
            }
        }
    }
}
