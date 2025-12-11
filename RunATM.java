import java.util.Objects;
import java.util.Scanner;

class RunATM {
   
   // Search for account by unique ID and return its index
   int account_search_by_unique_id(Account1[] accounts, String uniqueId) {
      if (accounts == null || uniqueId == null) {
         return -1;
      }
      
      for (int i = 0; i < accounts.length; i++) {
         if (accounts[i] != null && Objects.equals(uniqueId, accounts[i].unique_person_id)) {
            return i;
         }
      }
      return -1;
   }

   void demo(Account1[] accounts) {
      Scanner scanner = new Scanner(System.in);
      
      System.out.print("\n\n\nWelcome to ATM\n");
      System.out.println("Please Enter your Unique id");
      String enteredUniqueId = scanner.nextLine();
      
      int accountIndex = this.account_search_by_unique_id(accounts, enteredUniqueId);
      
      if (accountIndex == -1) {
         System.out.println("Account not registered Yet!");
         try {
            Thread.sleep(3000L);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         return;
      }
      
      // Verify PIN
      System.out.println("Enter your PIN number:\n");
      int enteredPin = scanner.nextInt();
      scanner.nextLine();
      
      if (enteredPin != accounts[accountIndex].pin) {
         System.out.println("Wrong PIN Entered!\n Try Again");
         try {
            Thread.sleep(3000L);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         return;
      }
      
      // Display menu options
      System.out.println("Select the option as given below!\nWithdraw?--1\n Deposit?--2\n Account transfer?---3\n Display Account details?---4\n Quit?---5\n");
      ATM atm = new ATM();
      int choice = scanner.nextInt();
      scanner.nextLine();
      
      switch (choice) {
         case 1:
            atm.withdraw(accounts[accountIndex], scanner);
            break;
         case 2:
            atm.deposit(accounts[accountIndex], scanner);
            break;
         case 3:
            // Clear screen for transfer operation
            System.out.print("\u001b[H\u001b[2J");
            System.out.flush();
            System.out.println("Enter Account Number to transfer the funds?\n");
            String targetUniqueId = scanner.nextLine();
            
            int targetAccountIndex = this.account_search_by_unique_id(accounts, targetUniqueId);
            if (targetAccountIndex == -1) {
               System.out.println("Account not yet Registered!");
               return;
            }
            
            System.out.println("Enter Amount for Transferring funds?");
            double transferAmount = scanner.nextDouble();
            scanner.nextLine();
            
            atm.Transfer(accounts[accountIndex], accounts[targetAccountIndex], transferAmount);
            break;
         case 4:
            atm.display_details(accounts[accountIndex]);
            break;
         case 5:
            System.out.println("Thank you for using ATM!");
            break;
         default:
            System.out.println("Invalid choice! Please select 1-5.");
      }
   }
}