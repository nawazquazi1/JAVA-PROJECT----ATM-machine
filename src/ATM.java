import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        // init Scanner
        Scanner sc = new Scanner(System.in);
        // init bank
        Bank bank = new Bank("Bank of America");

        // add a user, which also creates a saving account
        User user = bank.addUser("nawaz", "quazi", "1234");
        User user1 = bank.addUser("akib", "quazi", "4321");


        //add a checking account for our user
        Account account = new Account("Checking", user, bank);
        Account account1 = new Account("Checking", user1, bank);
        user1.addAccount(account1);
        bank.addAccount(account);
        user.addAccount(account);
        bank.addAccount(account);

        User curUser;
        while (true) {

            // stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(bank, sc);
            // stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }

    public static User mainMenuPrompt(Bank bank, Scanner sc) {
        //init
        String userId;
        String pin;
        User user;

        // prompt the user for user ID/pin combo until a correct one is  reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
            System.out.print("Enter User ID : ");
            userId = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            // try to get the user object corresponding ton the ID and pin combo
            user = bank.userLogin(userId, pin);
            if (user == null) {
                System.out.println("Incorrect user ID/pin Combination. "
                        + "please try again."
                );
            }
        } while (user == null);
        return user;
    }

    public static void printUserMenu(User user, Scanner sc) {

        // print a summary of the user's accounts
        user.printAccountSummary();

        // init
        int choice;

        // user menu
        do {
            System.out.printf("Welcome %s, What would you like to do ? \n", user.getFirstName());
            System.out.println("\t1) Show account transaction history");
            System.out.println("\t2) Withdrawal");
            System.out.println("\t3) Deposit");
            System.out.println("\t4) Transfer");
            System.out.println("\t5) Quit");
            System.out.println();
            System.out.println("Enter choice");
            choice = sc.nextInt();
            if (choice < 0 || choice > 5) {
                System.out.println("Invalid choice. please choose 1-5");
            }
        } while (choice < 1 || choice > 5);

        // process the  choice
        switch (choice) {
            case 1:
                ATM.showTransHistory(user, sc);
                break;
            case 2:
                ATM.withdrawalFunds(user, sc);
                break;
            case 3:
                ATM.depositFunds(user, sc);
                break;
            case 4:
                ATM.transferFunds(user, sc);
                break;
            case 5:
                // gobble up of previous input
                sc.nextLine();
                break;
        }

        // redisplay this menu unless the user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(user, sc);
        }
    }

    public static void showTransHistory(User user, Scanner sc) {
        int theAcct;

        // get account whose transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transaction you want to see : ", user.numAccounts()
            );
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= user.numAccounts()) {
                System.out.println("Invalid account. please try again");
            }
        } while (theAcct < 0 || theAcct >= user.numAccounts());

        // print the transaction history
        user.printAcctTransHistory(theAcct);
    }

    public static void transferFunds(User user, Scanner sc) {
        //init
        int fromAcct;
        int toAcct;
        double amount;
        double accBal;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number(1-%d) of the account\n" + "to transfer from : ", user.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= user.numAccounts()) {
                System.out.println("Invalid accounts : please try again");
            }
        } while (fromAcct < 0 || fromAcct >= user.numAccounts());
        accBal = user.getAcctBalance(fromAcct);

        // get the account to transfer to
        do {
            System.out.printf("Enter the number(1-%d) of the account\n" + "to transfer to: ", user.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= user.numAccounts()) {
                System.out.println("Invalid accounts : please try again");
            }
        } while (toAcct < 0 || toAcct >= user.numAccounts());

        // get the amount to transfer to
        do {
            System.out.printf("Enter the amount transfer (max $%.02f): $", accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println(" Amount must be greater than zero");
            } else if (amount > accBal) {
                System.out.printf("Amount must not be greater than zero\n" +
                        "balance of $%.02f.\n", accBal);
            }
        } while (amount < 0 || amount > accBal);

        // finally, do the transfer
        user.addAcctTransaction(fromAcct, -1 * amount, String.format(
                "Transfer to account %s", user.getAcctUUID(toAcct)));
        user.addAcctTransaction(toAcct, amount, String.format(
                "Transfer to account %s", user.getAcctUUID(fromAcct)));
    }

    public static void withdrawalFunds(User user, Scanner sc) {
        int fromAcct;
        double amount;
        double accBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number(1-%d) of the account\n" + "to withdraw from : ", user.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= user.numAccounts()) {
                System.out.println("Invalid accounts : please try again");
            }
        } while (fromAcct < 0 || fromAcct >= user.numAccounts());
        accBal = user.getAcctBalance(fromAcct);

        do {
            System.out.printf("Enter the amount withdraw (max $%.02f): $", accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println(" Amount must be greater than zero");
            } else if (amount > accBal) {
                System.out.printf("Amount must not be greater than zero\n" +
                        "balance of $%.02f.\n", accBal);
            }
        } while (amount < 0 || amount > accBal);

        // gobble up of previous input
        sc.nextLine();

        // get a memo
        System.out.println("Enter a memo :");
        memo = sc.nextLine();

        // do the withdrawal
        user.addAcctTransaction(fromAcct, -1 * amount, memo);
    }

    public static void depositFunds(User user, Scanner sc) {
        //init
        int toAcco;
        double amount;
        double accBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number(1-%d) of the account\n" + "to deposit in : ", user.numAccounts());
            toAcco = sc.nextInt() - 1;
            if (toAcco < 0 || toAcco >= user.numAccounts()) {
                System.out.println("Invalid accounts : please try again");
            }
        } while (toAcco < 0 || toAcco >= user.numAccounts());
        accBal = user.getAcctBalance(toAcco);

        do {
            System.out.printf("Enter the amount transfer (max $%.02f): $", accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println(" Amount must be greater than zero");
            }
        } while (amount < 0);

        // gobble up of previous input
        sc.nextLine();

        // get a memo
        System.out.print("Enter a memo :");
        memo = sc.nextLine();

        // do the withdrawal
        user.addAcctTransaction(toAcco, amount, memo);
    }

}
