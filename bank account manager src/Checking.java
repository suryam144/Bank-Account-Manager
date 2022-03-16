import java.text.DecimalFormat;

/**
 * Checking class, which handles the creation and implementation of a Checking object.
 */
public class Checking extends Account{

    private int fee;
    private static final int FEE_WAIVE = 1000;
    private static final int MONTHLY_FEE_VAL = 25;
    private static final int WAIVED_FEE_VAL = 0;
    private static final double INTEREST_RATE = (0.001/12);

    /**
     * Constructor methods, which creates an instance of Checking.
     * @param holder Profile representing the holder of a checking account.
     * @param closed Boolean value representing whether an account is open (false) or closed (true).
     * @param balance Double value representing the balance in a checking account.
     */
    public Checking(Profile holder, boolean closed, double balance){
        super(holder, closed, balance);
        if(balance < FEE_WAIVE){fee = MONTHLY_FEE_VAL;}
        else{fee = WAIVED_FEE_VAL;}
    }

    /**
     * Method that represents an instance of Checking as a string.
     * @return "Checking::" followed by Account class attributes for a non-loyal account; "Account not valid" otherwise.
     */
    @Override
    public String toString(){
        return "Checking::" + super.toString();
    }

    /**
     * Method that represents a withdrawal from a checking account.
     * @param amount Integer representing the withdrawal value to be subtracted.
     */
    @Override
    public void withdraw(double amount){
        super.withdraw(amount);
        fee();
    }

    /**
     * Method that represents a deposit to a checking account.
     * @param amount Integer representing the deposit value to be added.
     */
    @Override
    public void deposit(double amount){
        super.deposit(amount);
        fee();
    }

    /**
     * Method that calculates the monthly interest given the balance and interest rate tied to a checking account.
     * @return Double value representing the monthly interest.
     */
    @Override
    public double monthlyInterest(){
        DecimalFormat fmt = new DecimalFormat("##,###0.00");
        double balFormat = balance * INTEREST_RATE;
        return Double.parseDouble(fmt.format(balFormat));
    }

    /**
     * Method that calculates monthly fee based on the current balance in an instance of Checking, waiving it if the balance is above a certain value.
     * @return Double representing the fee associated with this instance of Checking.
     */
    @Override
    public double fee(){
        double temp;
        if(balance < FEE_WAIVE){temp = MONTHLY_FEE_VAL;}
        else{temp = WAIVED_FEE_VAL;}
        DecimalFormat fmt = new DecimalFormat("##,###0.00");
        return Double.parseDouble(fmt.format(temp));
    }

    /**
     * Method that uses a String to represent an instance of Checking.
     * @return String saying "Checking" to represent an instance of Checking.
     */
    @Override
    public String getType(){
        return "Checking";
    }

    /**
     * Method that changes the loyalty status of an instance of Savings.
     * @param amt Integer representing the loyalty status of an account, with 0 representing a non-loyal account and 1 representing a loyal one.
     * @return Integer 0, which makes account non-loyal.
     */
    @Override
    public int changeLoyal(int amt){return 0;}

}