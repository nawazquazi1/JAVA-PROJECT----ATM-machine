import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;


    /**
     * create a new Bank object with empty lists of users and account
     * @param name the na,e of the bank
     */

    public Bank(String name) {
        this.name = name;
        this.users=new ArrayList<>();
        this.accounts=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    /**
     * generate a new universally unique ID for an User
     * @return the uuid
     */

    public String getNewUserUUID() {
        // init
        String uuid;
        Random random = new Random();
        int len = 6;
        boolean nonUnique;
        // continue looping until we get a unique ID
        do {
            // generate the number
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) random.nextInt(10));
            }
            // check to make sure it's unique
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUuid()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);


        return uuid;
    }

    /**
     * generate a new universally unique ID for an account
     *
     * @return the uuid
     */

    public String getNewAccountUUID() {
        // init
        String uuid;
        Random random = new Random();
        int len = 10;
        boolean nonUnique;
        // continue looping until we get a unique ID
        do {
            // generate the number
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) random.nextInt(10));
            }
            // check to make sure it's unique
            nonUnique = false;
            for (Account account : this.accounts) {
                if (uuid.compareTo(account.getUuid()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;
    }

    /**
     * Add an account
     *
     * @param account
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    /**
     * Create a new user of the bank
     *
     * @param firstname the user's firstName
     * @param lastName  the user's LastName
     * @param pin       the user's pin
     * @return the new user object
     */

    public User addUser(String firstname, String lastName, String pin) {

        // create a new User object and add it to our list
        User newUser = new User(firstname, lastName, pin, this);
        this.users.add(newUser);

        // create a saving Account for the user
        Account account = new Account("Saving", newUser, this);
        newUser.addAccount(account);
        this.addAccount(account);

        return newUser;
    }

    /**
     * get the user object associated with a particular userID and pin,
     * if they are valid
     *
     * @param userID the UUID of the user to log in
     * @param pin    the pin of the user
     * @return the user object, if the login id successful, or null,if it is not
     */

    public User userLogin(String userID, String pin) {

        // search through list of users
        for (User u : this.users) {
            // check user ID is correct
            if (u.getUuid().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        // if we haven't found the user or have an incorrect pin
        return null;

    }

}
