import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;


    /**
     * Return the user's first name
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param pin       the user's account pin number
     * @param bank      the bank object that the user is a customer of
     */

    public User(String firstName, String lastName, String pin, Bank bank) {
        this.firstName = firstName;
        this.lastName = lastName;
        // security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error , caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        // get a new ,unique universal Id for the user
        this.uuid = bank.getNewUserUUID();
        //create empty list of account
        this.accounts = new ArrayList<>();
        // print log message
        System.out.printf("New user %s,%s with ID %s created. \n", lastName, firstName, this.uuid);
    }

    /**
     * Add an Account for the user
     *
     * @param account
     */

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    /**
     * return the user's UUID
     *
     * @return
     */

    public String getUuid() {
        return this.uuid;
    }

    /**
     * check Whether a given pin matches the true user pin
     *
     * @param pin the pin to check
     * @return Whether the pin is valid or not
     */
    public boolean validatePin(String pin) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(messageDigest.digest(pin.getBytes()), this.pinHash);

        } catch (NoSuchAlgorithmException a) {
            System.out.println("Error , caught NoSuchAlgorithmException");
            a.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    /**
     * print summaries for the accounts of this user
     */

    public void printAccountSummary() {
        System.out.printf("\n\n%s's accounts summary\n", this.getFirstName());
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("%d) %s\n",i+1,this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Get the number of accounts the user
     * @return the number of accounts
     */

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIdx){
        this.accounts.get(acctIdx).printAcctTransHistory();
    }

   public double getAcctBalance(int accIdx){
        return this.accounts.get(accIdx).getBalance();
   }

   public String getAcctUUID(int accIdx){
        return this.accounts.get(accIdx).getUuid();
   }
   public void addAcctTransaction(int acctIdx,double amount,String memo){
        this.accounts.get(acctIdx).addTransaction(amount,memo);
   }

}


