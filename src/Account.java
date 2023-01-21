import java.util.ArrayList;

public class Account {
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    /**
     * @param name   the name of the account
     * @param holder the user object that holds this account
     * @param bank   the bank that issuse the account
     */
    public Account(String name, User holder, Bank bank) {
        // set the account name and holder
        this.name = name;
        this.holder = holder;
        // get new account uuID
        this.uuid = bank.getNewAccountUUID();

        // init transactions
        this.transactions = new ArrayList<>();


    }

    /**
     * get the account Id
     * @return the UUID
     */

    public String getUuid() {
        return this.uuid;
    }

    /**
     * Get summary line for the account
     * @return the String summary
     */

    public String getSummaryLine() {
        // get the account's balance
        double balance = this.getBalance();
        // format the summary line, depending on the whether the balance is negative
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    /**
     * Get the balance of this account by adding the  amounts of the transaction
     * @return the balance value Transaction.
     */

    public double getBalance(){
        double balance=0;
        for (Transaction t:this.transactions){
            balance+=t.getAmount();
        }
        return balance;
    }

    public void printAcctTransHistory(){
        System.out.printf("\nTransaction history for account %s \n",this.uuid);
        for (int i=this.transactions.size()-1;i>=0;i--){
            System.out.println(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }
    public void addTransaction(double amount,String memo){
        // create new transaction object and add it to our list
        Transaction newTransaction=new Transaction(amount,memo,this);
        this.transactions.add(newTransaction);
    }
}
