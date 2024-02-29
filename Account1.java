import java.util.*;
import java.io.*;
public class Account1 {
    static int account_no = 2345;
    String acc_person_name;
    int pin;
     double balance_amount;
    String unique_person_id;
    int a_no;
    void createAcc1(){
        a_no = account_no;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter acc holder name ");
        String acc_person_name = sc.nextLine();
        System.out.println("\n Enter the Username:");
        unique_person_id = sc.nextLine();
        int length_of_pin = 0;
        do {
            System.out.println("Enter the 4 digits pin:");
            pin = sc.nextInt();
            length_of_pin = String.valueOf(pin).length();
        }
        while(length_of_pin != 4);
        System.out.println("Enter the starting deposit amount");
        balance_amount = sc.nextDouble();
        System.out.println("Congrats!! Account Successfully Created");
        System.out.println("Account Details :\n" + a_no + "\nacc_person_name:"+acc_person_name+ "\nbalance_amount:"+balance_amount+"\nThank you!");
//        creating a file with the acc_number of the person
        String file_name = account_no +".txt";
        File file = new File(file_name);
        try{
            file.createNewFile();
            FileWriter writer = new FileWriter(file_name);
            writer.write("Account Created\n");
            writer.write("Account Number:"+account_no+"\n");
            writer.write("USER ID OF PERSON"+unique_person_id+"\n");
            writer.write("Account Holder Name:"+ acc_person_name+"\n");
            writer.write("Pin:"+pin+"\n");
            writer.write("balance_amount"+balance_amount+"\n");
            writer.write("Date"+ new Date()+"\n\n\n");
            writer.close();
            System.out.println("File"+file_name+"successfully_created\n");
        }
        catch (IOException e){
            System.out.println("An error occurred while creating the file"+file_name);
            e.printStackTrace();
        }
        try {
            Thread.sleep(6000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        account_no++;
    }
}
  class ATM{
        void withdraw(Account1 account){
            Scanner in = new Scanner(System.in);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Withdraw money mode\n");
            System.out.print("Enter amt in multiple of 100's-");
            double amount = in.nextDouble();
            if(amount % 100 == 0){
                if(account.balance_amount >= amount){
                    account.balance_amount -= amount;
                    System.out.println("Your Transaction is processing\n");
                    try {
//                open the text file for appending the new transaction
                        String file_name = account.a_no +".txt";
//                        System.out.println("The fileName is -"+ file_name);
                        FileWriter fileWriter = new FileWriter(file_name,true);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//                        writing the details of transaction to the text file
                        bufferedWriter.write("Date:"+ new Date()+"\n");
                        bufferedWriter.write("Withdrawal:"+amount+"\n");
                        bufferedWriter.write("Account Number:"+account.a_no+"\n");
                        bufferedWriter.write("Remaining Balance:"+ account.balance_amount+"\n\n");
//                        closing the file writer and buffer-writer
                        bufferedWriter.close();
                        fileWriter.close();
                    }catch (IOException e){
                        System.out.println("An error occurred while writing to the file.");
                        e.printStackTrace();
                    }
                    System.out.println("Thank you for Banking with our company!");
                    try{
                        Thread.sleep(7000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
                else {
                    System.out.print("Insufficient funds");
                }
            }
            else {
                System.out.println("Amount not entered in multiples of 100!");
                System.out.println("try again!");
            }
        }
        void deposit_by_transfer(Account1 account, double amount){
            account.balance_amount += amount;
            try {
                String file_name = account.a_no + ".txt";
                FileWriter fileWriter = new FileWriter(file_name,true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("Deposit:"+amount+"\n");
                bufferedWriter.write("Date:"+ new Date()+"\n");
                bufferedWriter.write("Account Number:"+account.a_no+"\n");
                bufferedWriter.write("Remaining balance"+account.balance_amount+"\n\n");
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred while writing the file");
                e.printStackTrace();

            }
        }
        void deposit(Account1 account){
            Scanner sc = new Scanner(System.in);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Money deposit mode");
            System.out.println("Enter the amount to be deposited");
            double amount = sc.nextDouble();
            account.balance_amount += amount;
            System.out.print("\033[H\033[2J");
            System.out.flush();
            try {
                String file_name = account.a_no +".txt";
                System.out.println("The file name -"+ file_name);
                FileWriter fileWriter = new FileWriter(file_name,true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("Deposit:"+amount+"\n");
                bufferedWriter.write("Date:"+ new Date()+"\n");
                bufferedWriter.write("Account Number:"+account.a_no+"\n");
                bufferedWriter.write("Remaining balance"+account.balance_amount+"\n\n");
                bufferedWriter.close();
                fileWriter.close();
            }catch (IOException e){
                System.out.println("An error occurred while writing the file.");
                e.printStackTrace();
            }
            System.out.println("Processing your Request! please wait");
            try {
                Thread.sleep(5000);
            }catch (InterruptedException e){

            }
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Transaction completed Successfully!");
                System.out.println("Thank you for Banking with us");
                System.out.println("!--Going to Login Page---!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
        }
        void Transfer(Account1 acc1, Account1 acc2, double amount){
            if (acc1.balance_amount >= amount){
                acc1.balance_amount -= amount;
                ATM a = new ATM();
                a.deposit_by_transfer(acc2,amount);
                try{
                    String file_name = acc1.a_no +".txt";
                    FileWriter fileWriter = new FileWriter(file_name,true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write("Transferred:"+amount+"\n");
                    bufferedWriter.write("Date:"+ new Date()+"\n");
                    bufferedWriter.write("Account Number:"+acc1.a_no+"\n");
                    bufferedWriter.write("Remaining balance"+acc1.balance_amount+"\n\n");
                    bufferedWriter.close();
                    fileWriter.close();
                }catch (IOException e){
                    System.out.println("An error occurred while writing the file.");
                    e.printStackTrace();
                }
                System.out.println("Processing your Request, Please wait!\n");
                try {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        void display_details(Account1 account){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Displaying account details\n");
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            String file_name = String.valueOf(account.a_no)+".txt";
            File file = new File(file_name);
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader  = new BufferedReader(fileReader);
                String line = null;
                while ((line = bufferedReader.readLine())!= null){
                    System.out.println(line);
                }
                bufferedReader.close();
            }
            catch (FileNotFoundException e){
                System.out.println("File not found:"+e.getMessage());
            }
            catch (IOException e){
                System.out.println("Error reading file:"+e.getMessage());
            }
            System.out.println("Screen will automatically timed out after 30 seconds. . .");
            try{
                Thread.sleep(30000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

        }
        void quit(){
            System.out.println("Thank you for Banking with Us! . . \n");
            return;
        }
    }

    class run_atm {
        int account_search_by_unique_id(Account1[] ac, String unique_id) {

            for (int i = 0; i <= 5; i++) {
                if (Objects.equals(unique_id, ac[i].unique_person_id))
                    return i;
            }
            return -1;

        }

        void demo(Account1[] ac) {
            Scanner sc = new Scanner(System.in);
            System.out.print("\n\n\nWelcome to ATM\n");
            System.out.println("Please Enter your Unique id");
            String unique_id = sc.nextLine();
            int i = account_search_by_unique_id(ac, unique_id);
            if (i == -1) {
                System.out.println("Account not registered Yet!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            } else {
                System.out.println("Enter your PIN number:\n");
                int pin = sc.nextInt();
                if (pin == ac[i].pin) {
//                System.out.println("Account Details :\n" + a_no + "\n acc_person_name:"+acc_person_name+ "\n balance_amount"+balance_amount+"\n");
                    System.out.println("Select the option as given below!\nWithdraw?--1\n Deposit?--2\n Account transfer?---3\n Display Account details?---4\n Quit?---5\n");
                    int ch;
                    ATM atm = new ATM();
                    ch = sc.nextInt();
                    switch (ch) {
                        case 1 -> atm.withdraw(ac[i]);
                        case 2 -> atm.deposit(ac[i]);
                        case 3 -> {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println("Enter Account Number to transfer the funds?\n");
                            String account_transfer = sc.nextLine();
                            int j = account_search_by_unique_id(ac, account_transfer);
                            if (j == -1) {
                                System.out.println("Account not yet Registered!");
                                return;
                            } else {
                                System.out.println("Enter Amount for Transferring funds?");
                                double amount = sc.nextDouble();
                                atm.Transfer(ac[i], ac[j], amount);

                            }
                        }

                        case 4 -> atm.display_details(ac[i]);
                        case 5 -> atm.quit();
                    }
                } else {
                    System.out.println("Wrong PIN Entered!\n Try Again");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
    }

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Account1[] ac = new Account1[5];
        for (int i = 0; i < 5; i++) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Create Account Mode-\n");
            ac[i] = new Account1();
            ac[i].createAcc1();
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        run_atm ob = new run_atm();
        for (; ; ) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            ob.demo(ac);
        }
    }
}


