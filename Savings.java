import java.text.DecimalFormat;

/**
 * Savings class, which handles the creation and implementation of a Savings object.
 * @author Isabelle Chang, Surya Mantha
 */
public class Savings extends Account{

    public static final double INTEREST_RATE = (0.003/12);
    public static final double LOYALTY_INTEREST_RATE = (0.0045/12);
    public static final int FEE_WAIVE = 300;
    public static final int MONTHLY_FEE = 6;
    public static final int WAIVED_FEE_VAL = 0;
    public static final int NOT_FOUND = -1;

    private int isLoyal;

    /**
     * Constructor method to create an instance of Savings.
     * @param holder Profile object representing the holder of a savings account.
     * @param closed Boolean representing whether a savings account is closed.
     * @param balance Double representing the total balance in a savings account.
     * @param loyalty Integer representing the loyalty status of an account: 0 for not loyal, 1 for loyal.
     */
    public Savings(Profile holder, boolean closed, double balance, int loyalty) {
        super(holder, closed, balance);
        isLoyal = loyalty;
    }

    /**
     * Method that determines whether two classes are equivalent to one another
     * @param obj object of an Account type
     * @return Boolean: returns true if the accounts are equivalent; returns false otherwise
     */
    @Override
    public boolean equals(Object obj){
        Savings acct = (Savings) obj;
        return (super.equals(acct)) && (isLoyal == acct.getLoyalty());
    }

    /**
     * Method that represents an instance of Savings as a string. Adds "Loyal" if the Savings account is loyal, and returns "Account not valid" if the account is not a valid one.
     * @return "Savings::" followed by Account class attributes for a non-loyal account; "Savings::" followed by Account class attributes and "Loyal" for a loyal account; "Account not valid" otherwise.
     */
    @Override
    public String toString(){
        if(isLoyal == 0){return "Savings::" + super.toString();}
        else if(isLoyal == 1){return "Savings::" + super.toString() + "::" + "Loyal";}
        else{return "Account not valid";}
    }

    /**
     * Method that calculates the monthly interest given the balance and interest rate tied to a savings account.
     * @return Double value representing the monthly interest.
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
     * Method that calculates monthly fee based on the current balance in an instance of Savings, waiving it if the balance is above a certain value.
     * @return Double representing the fee associated with this instance of Savings.
     */
    @Override
    public double fee() {
        double temp;
        if(balance < FEE_WAIVE){temp = MONTHLY_FEE;}
        else{temp = WAIVED_FEE_VAL;}
        DecimalFormat fmt = new DecimalFormat("##,###0.00");
        return Double.parseDouble(fmt.format(temp));
    }

    /**
     * Method that uses a String to represent an instance of Savings.
     * @return String saying "Savings" to represent an instance of Savings.
     */
    @Override
    public String getType() {return "Savings";}

    /**
     * Method that discloses whether an instance of Savings is loyal.
     * @return Integer representing the loyalty status of an account with 0 representing a non-loyal account and 1 representing a loyal one.
     */
    public int getLoyalty(){return isLoyal;}

    /**
     * Method that changes the loyalty status of an instance of Savings.
     * @param amt Integer representing the loyalty status of an account, with 0 representing a non-loyal account and 1 representing a loyal one.
     * @return Integer 1, which makes the account loyal.
     */
    public int changeLoyal(int amt){
        isLoyal = amt;
        return amt;
    }
}