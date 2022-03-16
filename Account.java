import java.text.DecimalFormat;

/**
 * Abstract Account class, which handles the creation and implementation of an Account object.
 * @author Isabelle Chang, Surya Mantha
 */
public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    /**
     * Constructor method, which creates an instance of Account.
     * @param holder Instance of Profile representing the account holder.
     * @param closed Boolean value that represents the opening/closing status of an account.
     * @param balance Double representing the balance in an account.
     */
    public Account(Profile holder, boolean closed, double balance){
        this.holder = holder;
        this.closed = closed;
        this.balance = balance;
    }

    /**
     * Method which checks if one instance of Account is equal to another instance of Account.
     * @param obj Account object.
     * @return Returns true if input account is the same as caller account and false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        Account acct = (Account) obj;
        return (holder.equals(acct.holder)) && (closed == acct.closed) && (balance == acct.balance) && (getType().equals(acct.getType()));
    }

    /**
     * Method which represents information in the account as a String.
     * @return String representation of the account of the format "holder ::Balance $", with "::CLOSED" added on if account is closed.
     */
    @Override
    public String toString(){
        DecimalFormat fmt = new DecimalFormat("##,###0.00");
        double balFormat = balance;
        if(!isClosed()){
            return holder + "::Balance $" + fmt.format(balFormat);
        }
        else{return holder + "::Balance $" + fmt.format(balFormat) + "::CLOSED";}
    }

    /**
     * Method which represents a withdrawal from an account's balance by subtracting a withdrawal value from the account balance.
     * @param amount Integer representing the withdrawal value to be subtracted.
     */
    public void withdraw(double amount){balance -= amount;}

    /**
     * Method which represents a deposit to an account's balance by adding a deposit value to the account balance.
     * @param amount Integer representing the deposit value to be added.
     */
    public void deposit(double amount){balance += amount;}

    /**
     * Method which closes an account by changing the boolean value closed to true.
     */
    public void closeAcct(){closed = true;}

    /**
     * Method which checks whether an account is closed.
     * @return Boolean value true if account is closed, and false if it is not.
     */
    public boolean isClosed(){return closed;}

    /**
     * Method that gets the balance of an instance of Account.
     * @return Double value representing the amount in an account's balance.
     */
    public double getBalance(){return balance;}

    /**
     * Method that sets the balance of an instance of Account.
     * @param amt Double value representing the amount that is to become the balance amount for an instance of Account.
     */
    public void setBalance(double amt){
        DecimalFormat fmt = new DecimalFormat("##,###0.00");
        balance = Double.parseDouble(fmt.format(amt));
    }

    /**
     * Method that gets the holder of an instance of Account.
     * @return Profile object representing the profile of an account holder.
     */
    public Profile getHolder(){return holder;}

    /**
     * Abstract method that calculates the monthly interest of an account.
     * @return Double representing the monthly interest for an account.
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method that calculates the fee for an account.
     * @return Double representing the fee for an account.
     */
    public abstract double fee();

    /**
     * Abstract method that gets the type of an account.
     * @return String representing the type of an account.
     */
    public abstract String getType();

    /**
     * Abstract method that changes the loyalty status of an account.
     * @param amt Integer representing loyalty status, with 0 being non-loyal and 1 being loyal.
     * @return Integer representing the loyalty status of an account, with 0 being non-loyal and 1 being loyal.
     */
    public abstract int changeLoyal(int amt);
}
