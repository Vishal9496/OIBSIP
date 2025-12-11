import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

class ATM {

   // Withdraw money from account
   void withdraw(Account1 account, Scanner scanner) {
      System.out.print("\u001b[H\u001b[2J");
      System.out.flush();
      System.out.println("Withdraw money mode\n");
      System.out.print("Enter amt in multiple of 100's: ");
      
      // Validate numeric input
      while (!scanner.hasNextDouble()) {
         System.out.print("Invalid input! Enter a valid amount: ");
         scanner.next();
      }
      double amount = scanner.nextDouble();
      scanner.nextLine();
      
      if (amount % 100.0 != 0.0) {
         System.out.println("Amount not entered in multiples of 100!");
         System.out.println("try again!");
         return;
      }
      
      if (account.balance_amount < amount) {
         System.out.println("Insufficient funds. Current balance: " + account.balance_amount);
         return;
      }
      
      account.balance_amount -= amount;
      System.out.println("Your Transaction is processing\n");

      // Write transaction to file
      try {
         String fileName = account.a_no + ".txt";
         FileWriter fileWriter = new FileWriter(fileName, true);
         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
         
         bufferedWriter.write("Date:" + new Date() + "\n");
         bufferedWriter.write("Withdrawal:" + amount + "\n");
         bufferedWriter.write("Account Number:" + account.a_no + "\n");
         bufferedWriter.write("Remaining Balance:" + account.balance_amount + "\n\n");
         
         bufferedWriter.close();
         fileWriter.close();
      } catch (IOException e) {
         System.out.println("An error occurred while writing to the file.");
         e.printStackTrace();
      }

      System.out.println("Thank you for Banking with our company!");

      try {
         Thread.sleep(3000L);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      System.out.print("\u001b[H\u001b[2J");
      System.out.flush();
   }

   // Deposit money to account
   void deposit(Account1 account, Scanner scanner) {
      System.out.print("\u001b[H\u001b[2J");
      System.out.flush();
      System.out.println("Money deposit mode");
      System.out.println("Enter the amount to be deposited: ");
      
      // Validate numeric input
      while (!scanner.hasNextDouble()) {
         System.out.print("Invalid input! Enter a valid amount: ");
         scanner.next();
      }
      double amount = scanner.nextDouble();
      scanner.nextLine();
      
      if (amount <= 0) {
         System.out.println("Deposit amount must be positive!");
         return;
      }
      
      account.balance_amount += amount;
      System.out.print("\u001b[H\u001b[2J");
      System.out.flush();

      // Write transaction to file
      try {
         String fileName = account.a_no + ".txt";
         System.out.println("The file name - " + fileName);
         FileWriter fileWriter = new FileWriter(fileName, true);
         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
         
         bufferedWriter.write("Deposit:" + amount + "\n");
         bufferedWriter.write("Date:" + new Date() + "\n");
         bufferedWriter.write("Account Number:" + account.a_no + "\n");
         bufferedWriter.write("Remaining balance:" + account.balance_amount + "\n\n");
         
         bufferedWriter.close();
         fileWriter.close();
      } catch (IOException e) {
         System.out.println("An error occurred while writing the file.");
         e.printStackTrace();
      }

      System.out.println("Processing your Request! please wait");

      try {
         Thread.sleep(3000L);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      System.out.print("\u001b[H\u001b[2J");
      System.out.flush();
      System.out.println("Transaction completed Successfully!");
      System.out.println("Thank you for Banking with us");
      System.out.println("!--Going to Login Page---!");

      try {
         Thread.sleep(3000L);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   // Helper method for deposit via transfer
   void deposit_by_transfer(Account1 account, double amount) {
      account.balance_amount += amount;

      try {
         String fileName = account.a_no + ".txt";
         FileWriter fileWriter = new FileWriter(fileName, true);
         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
         
         bufferedWriter.write("Deposit:" + amount + "\n");
         bufferedWriter.write("Date:" + new Date() + "\n");
         bufferedWriter.write("Account Number:" + account.a_no + "\n");
         bufferedWriter.write("Remaining balance:" + account.balance_amount + "\n\n");
         
         bufferedWriter.close();
         fileWriter.close();
      } catch (IOException e) {
         System.out.println("An error occurred while writing the file");
         e.printStackTrace();
      }
   }

   // Transfer money between accounts
   void Transfer(Account1 fromAccount, Account1 toAccount, double amount) {
      if (amount <= 0) {
         System.out.println("Transfer amount must be positive!");
         return;
      }
      
      if (fromAccount.balance_amount < amount) {
         System.out.println("Insufficient funds. Current balance: " + fromAccount.balance_amount);
         return;
      }
      
      fromAccount.balance_amount -= amount;
      deposit_by_transfer(toAccount, amount);

      // Log transaction to file
      try {
         String fileName = fromAccount.a_no + ".txt";
         FileWriter fileWriter = new FileWriter(fileName, true);
         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
         
         bufferedWriter.write("Transferred:" + amount + "\n");
         bufferedWriter.write("To Account:" + toAccount.a_no + "\n");
         bufferedWriter.write("Date:" + new Date() + "\n");
         bufferedWriter.write("Account Number:" + fromAccount.a_no + "\n");
         bufferedWriter.write("Remaining balance:" + fromAccount.balance_amount + "\n\n");
         
         bufferedWriter.close();
         fileWriter.close();
      } catch (IOException e) {
         System.out.println("An error occurred while writing the file.");
         e.printStackTrace();
      }

      System.out.println("Processing your Request, Please wait!\n");

      try {
         Thread.sleep(3000L);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      
      System.out.println("Transfer completed successfully!");
   }

   // Display account transaction history
   void display_details(Account1 account) {
      System.out.print("\u001b[H\u001b[2J");
      System.out.flush();
      System.out.println("Displaying account details\n");

      try {
         Thread.sleep(2000L);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      String fileName = account.a_no + ".txt";
      File file = new File(fileName);

      // Read and display file contents
      try {
         FileReader fileReader = new FileReader(file);
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         String line;

         while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
         }

         bufferedReader.close();
         fileReader.close();
      } catch (FileNotFoundException e) {
         System.out.println("File not found: " + e.getMessage());
      } catch (IOException e) {
         System.out.println("Error reading file: " + e.getMessage());
      }

      System.out.println("\nScreen will automatically timed out after 30 seconds. . .");

      try {
         Thread.sleep(30000L);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   void quit() {
      System.out.println("Thank you for Banking with Us! . . \n");
   }
}