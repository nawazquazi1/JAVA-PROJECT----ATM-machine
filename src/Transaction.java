import java.util.Date;

public class Transaction {
    private double amount;
    private Date date;
    private String memo;
    private Account account;

    /**
     * Create a new Transaction
     * @param amount  the amount Transacted
     * @param account the account the Transaction belongs to
     */

    public Transaction(double amount, Account account) {
        this.amount = amount;
        this.account = account;
        this.date = new Date();
        this.memo = "";

    }

    /**
     * Create a new Transaction
     * @param amount  the amount Transacted
     * @param memo    the memo for the Transaction
     * @param account the account the Transaction belongs to
     */

    public Transaction(double amount, String memo, Account account) {
        // call the two-arg constructor
        this(amount, account);
        // set the memo
        this.memo = memo;
    }

    /**
     * Get the amount of the Transaction
     * @return the amount
     */

    public double getAmount() {
        return this.amount;
    }

    /**
     * Get a String Summarizing the transaction
     * @return the summary String
     */

    public String getSummaryLine() {
        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s ", this.date.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s ", this.date.toString(), -this.amount, this.memo);
        }
    }
}
