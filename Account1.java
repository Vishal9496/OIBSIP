import java.util.*;
import java.io.*;

public class Account1 {
    static int account_no = 2345;

    String acc_person_name;
    String unique_person_id;
    int pin;
    double balance_amount;
    int a_no;

    void createAcc1(Scanner sc) {
        a_no = account_no;

        System.out.println("Enter Account Holder Name: ");
        this.acc_person_name = sc.nextLine();

        System.out.println("Enter the Username (Unique ID): ");
        this.unique_person_id = sc.nextLine();

        int length_of_pin;
        do {
            System.out.println("Enter a 4-digit PIN: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Enter a 4-digit PIN: ");
                sc.next();
            }
            this.pin = sc.nextInt();
            sc.nextLine();
            length_of_pin = String.valueOf(pin).length();
            if (length_of_pin != 4) {
                System.out.println("PIN must be exactly 4 digits!");
            }
        } while (length_of_pin != 4);

        System.out.println("Enter the starting deposit amount: ");
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input! Enter a valid amount: ");
            sc.next();
        }
        this.balance_amount = sc.nextDouble();
        sc.nextLine();

        System.out.println("\nCongrats!! Account Successfully Created");
        System.out.println("Account Details:\nAccount No: " + a_no +
                "\nName: " + acc_person_name +
                "\nBalance: " + balance_amount + "\n");

        String file_name = a_no + ".txt";

        try {
            File file = new File(file_name);
            file.createNewFile();

            FileWriter writer = new FileWriter(file_name);
            writer.write("Account Created\n");
            writer.write("Account Number: " + a_no + "\n");
            writer.write("USER ID: " + unique_person_id + "\n");
            writer.write("Account Holder Name: " + acc_person_name + "\n");
            writer.write("PIN: " + pin + "\n");
            writer.write("Balance Amount: " + balance_amount + "\n");
            writer.write("Date: " + new Date() + "\n\n");
            writer.close();

            System.out.println("File " + file_name + " successfully created\n");

        } catch (IOException e) {
            System.out.println("Error creating file " + file_name);
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}

        account_no++;
    }
}

class ATM {

    void withdraw(Account1 account, Scanner in) {
        System.out.println("Withdraw Money Mode");
        System.out.println("Enter amount in multiples of 100: ");
        
        while (!in.hasNextDouble()) {
            System.out.println("Invalid input! Enter a valid amount: ");
            in.next();
        }
        double amount = in.nextDouble();
        in.nextLine();

        if (amount % 100 == 0) {
            if (account.balance_amount >= amount) {
                account.balance_amount -= amount;

                try {
                    FileWriter fw = new FileWriter(account.a_no + ".txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write("Date: " + new Date() + "\n");
                    bw.write("Withdrawal: " + amount + "\n");
                    bw.write("Remaining Balance: " + account.balance_amount + "\n\n");

                    bw.close();
                    fw.close();

                } catch (IOException e) {
                    System.out.println("Error writing file");
                    e.printStackTrace();
                }

                System.out.println("Withdrawal Successful!");
                System.out.println("Remaining Balance: " + account.balance_amount);

            } else {
                System.out.println("Insufficient funds. Current balance: " + account.balance_amount);
            }
        } else {
            System.out.println("Amount must be in multiples of 100");
        }
    }

    void deposit(Account1 account, Scanner sc) {
        System.out.println("Deposit Mode");
        System.out.println("Enter amount to deposit:");
        
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input! Enter a valid amount: ");
            sc.next();
        }
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Deposit amount must be positive!");
            return;
        }

        account.balance_amount += amount;

        try {
            FileWriter fw = new FileWriter(account.a_no + ".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Deposit: " + amount + "\n");
            bw.write("Date: " + new Date() + "\n");
            bw.write("Remaining Balance: " + account.balance_amount + "\n\n");

            bw.close();
            fw.close();

        } catch (IOException e) {
            System.out.println("Error writing file");
            e.printStackTrace();
        }

        System.out.println("Deposit Successful");
        System.out.println("New Balance: " + account.balance_amount);
    }

    void deposit_by_transfer(Account1 acc, double amount) {
        acc.balance_amount += amount;

        try {
            FileWriter fw = new FileWriter(acc.a_no + ".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Transferred Deposit: " + amount + "\n");
            bw.write("Date: " + new Date() + "\n");
            bw.write("Remaining Balance: " + acc.balance_amount + "\n\n");

            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing transfer");
            e.printStackTrace();
        }
    }

    void Transfer(Account1 acc1, Account1 acc2, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive!");
            return;
        }

        if (acc1.balance_amount >= amount) {
            acc1.balance_amount -= amount;

            deposit_by_transfer(acc2, amount);

            try {
                FileWriter fw = new FileWriter(acc1.a_no + ".txt", true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("Transferred: " + amount + "\n");
                bw.write("To Account: " + acc2.a_no + "\n");
                bw.write("Date: " + new Date() + "\n");
                bw.write("Remaining Balance: " + acc1.balance_amount + "\n\n");

                bw.close();
                fw.close();
            } catch (IOException e) {
                System.out.println("Error writing transfer log");
                e.printStackTrace();
            }

            System.out.println("Transfer Successful");
            System.out.println("Remaining Balance: " + acc1.balance_amount);
        } else {
            System.out.println("Not enough balance. Current balance: " + acc1.balance_amount);
        }
    }

    void display_details(Account1 acc) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(acc.a_no + ".txt"));
            String line;

            System.out.println("\n========== Account Statement ==========");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("=======================================\n");

            br.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
            e.printStackTrace();
        }
    }
}

class run_atm {

    int account_search_by_unique_id(Account1[] ac, String id) {
        for (int i = 0; i < ac.length; i++) {
            if (ac[i] != null && ac[i].unique_person_id != null && ac[i].unique_person_id.equals(id)) {
                return i;
            }
        }
        return -1;
    }

    boolean demo(Account1[] ac, Scanner sc) {
        System.out.println("\n========== ATM Menu ==========");
        System.out.println("Enter your Unique ID (or 'exit' to quit): ");
        String uid = sc.nextLine();

        if (uid.equalsIgnoreCase("exit")) {
            return false;
        }

        int i = account_search_by_unique_id(ac, uid);

        if (i == -1) {
            System.out.println("Account Not Found");
            return true;
        }

        System.out.println("Enter your PIN: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input! Enter your PIN: ");
            sc.next();
        }
        int pin = sc.nextInt();
        sc.nextLine();

        if (pin != ac[i].pin) {
            System.out.println("Wrong PIN");
            return true;
        }

        ATM atm = new ATM();

        System.out.println("""
                Choose Option:
                1. Withdraw
                2. Deposit
                3. Transfer
                4. Display Account Details
                5. Quit
                """);

        while (!sc.hasNextInt()) {
            System.out.println("Invalid input! Enter a number (1-5): ");
            sc.next();
        }
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> atm.withdraw(ac[i], sc);
            case 2 -> atm.deposit(ac[i], sc);
            case 3 -> {
                System.out.println("Enter Unique ID to Transfer To: ");
                String uid2 = sc.nextLine();

                int j = account_search_by_unique_id(ac, uid2);

                if (j == -1) {
                    System.out.println("Target Account Not Found");
                    return true;
                }

                if (i == j) {
                    System.out.println("Cannot transfer to the same account!");
                    return true;
                }

                System.out.println("Enter Amount: ");
                while (!sc.hasNextDouble()) {
                    System.out.println("Invalid input! Enter a valid amount: ");
                    sc.next();
                }
                double amt = sc.nextDouble();
                sc.nextLine();

                atm.Transfer(ac[i], ac[j], amt);
            }
            case 4 -> atm.display_details(ac[i]);
            case 5 -> {
                System.out.println("Thank you for using ATM!");
                return true;
            }
            default -> System.out.println("Invalid choice! Please select 1-5.");
        }
        
        return true;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to ATM System!");
        System.out.println("How many accounts do you want to create? (1-10): ");
        
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input! Enter a number (1-10): ");
            sc.next();
        }
        int numAccounts = sc.nextInt();
        sc.nextLine();

        if (numAccounts < 1 || numAccounts > 10) {
            System.out.println("Invalid number. Creating 5 accounts by default.");
            numAccounts = 5;
        }

        Account1[] ac = new Account1[numAccounts];

        for (int i = 0; i < numAccounts; i++) {
            System.out.println("\n========== Create Account " + (i + 1) + " ==========");
            ac[i] = new Account1();
            ac[i].createAcc1(sc);
        }

        System.out.println("\n========== All Accounts Created Successfully! ==========");
        System.out.println("Starting ATM Operations...\n");

        run_atm r = new run_atm();

        while (true) {
            boolean continueRunning = r.demo(ac, sc);
            if (!continueRunning) {
                System.out.println("Exiting ATM System. Goodbye!");
                break;
            }
        }

        sc.close();
    }
}