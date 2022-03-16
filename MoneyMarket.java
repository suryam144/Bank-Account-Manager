import java.text.DecimalFormat;
/**
 * Money Market class, which handles the creation and implementation of a Money Market object.
 * @author Isabelle Chang, Surya Mantha
 */
public class MoneyMarket extends Savings{

    public static final double INTEREST_RATE = (0.008/12);
    public static final double LOYALTY_INTEREST_RATE = (0.0095/12);
    public static final int FEE_WAIVE = 2500;
    public static final int MONTHLY_FEE = 10;
    public static final int WAIVED_FEE_VAL = 0;
    public static final int NOT_FOUND = -1;

    private static final int ALWAYS_LOYAL = 1;
    private int isLoyal;
    private int withdrawalAmt = 0;

    /**
     * Constructor methods, which creates an instance of Money Market.
     * @param holder Profile representing the holder of a checking account.
     * @param closed Boolean value representing whether an account is open (false) or closed (true).
     * @param balance Double value representing the balance in a checking account.
     * @param loyalty Int value representing whether the account belongs to a loyal customer.
     *                This is overrided to make the customer always loyal in the next line
     */
    public MoneyMarket(Profile holder, boolean closed, double balance, int loyalty) {
        super(holder, closed, balance, loyalty);
        isLoyal = ALWAYS_LOYAL;
    }

    /**
     * Method that represents an instance of Money Market as a string.
     * @return "Money Market" followed by Account class attributes for a non-loyal account
     */
    @Override
    public String toString(){
        return "Money Market " + super.toString() + "::withdrawal: " + withdrawalAmt;
    }

    /**
     * Method which withdraws an amount from the account's balance
     * @param amount Integer representing the withdrawal value to be subtracted.
     */
    @Override
    public void withdraw(double amount){
        withdrawalAmt++;
        if(balance < FEE_WAIVE){isLoyal = 0;}
        super.withdraw(amount);
    }

    /**
     * Method that returns the monthly interest based on the loyalty of the account
     * @return returns the monthly interest based on whether the account is loyal or not.
     */
    @Override
    public double monthlyInterest() {
        if(isLoyal == 0){
            DecimalFormat fmt = new DecimalFormat("##,###0.00");
            double balFormat = balance * INTEREST_RATE;
            return Double.parseDouble(fmt.format(balFormat));
        }
        else if(isLoyal == 1){
            DecimalFormat fmt = new DecimalFormat("##,###0.00");
            double balFormat = balance * LOYALTY_INTEREST_RATE;
            return Double.parseDouble(fmt.format(balFormat));
        }
        return NOT_FOUND;
    }
    /**
     * Method that calculates monthly fee based on the current balance in an instance of Money Market,
     * waiving it if the balance is above a certain value or based on the withdrawal amount.
     * @return Double representing the fee associated with this instance of Checking.
     */
    @Override
    public double fee() {
        double temp;
        if(balance < FEE_WAIVE || withdrawalAmt > 3){temp = MONTHLY_FEE;}
        else{temp = WAIVED_FEE_VAL;}
        DecimalFormat fmt = new DecimalFormat("##,###0.00");
        return Double.parseDouble(fmt.format(temp));
    }
    /**
     * Method that uses a String to represent an instance of Money Market.
     * @return String saying "MoneyMarket" to represent an instance of Checking.
     */
    @Override
    public String getType() {return "MoneyMarket";}

    /**
     * Method that returns the value of the interest rate based on loyalty
     * @return Double: INTEREST_RATE if account is not loyal, LOYALTY_INTEREST_RATE if account is loyal, -1 otherwise
     */
    public double getInterest(){
        if(isLoyal == 0){return INTEREST_RATE;}
        else if(isLoyal == 1){return LOYALTY_INTEREST_RATE;}
        return NOT_FOUND;
    }

    /**
     * Changes the loyalty status of the account
     * @param amt Integer representing the loyalty status of an account, with 0 representing a non-loyal account and 1 representing a loyal one.
     * @return Integer: amt variable
     */
    public int changeLoyal(int amt){
        isLoyal = amt;
        return amt;
    }
}
