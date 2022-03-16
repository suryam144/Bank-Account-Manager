import java.text.DecimalFormat;
/**
 * College Checking class, which handles the creation and implementation of a College Checking object.
 * @author Isabelle Chang, Surya Mantha
 */
public class CollegeChecking extends Checking{

    public static final double MONTHLY_FEE = 0;
    public static final double INTEREST_RATE = (0.0025/12);

    private int schoolStatus;

    /**
     * Constructor methods, which creates an instance of College Checking.
     * @param holder Profile representing the holder of a college checking account.
     * @param closed Boolean value representing whether an account is open (false) or closed (true).
     * @param balance Double value representing the balance in a checking account.
     * @param status Integer value representating the school a person/profile attends
     */
    public CollegeChecking(Profile holder, boolean closed, double balance, int status) {
        super(holder, closed, balance);
        schoolStatus = status;
    }

    /**
     * Method that determines if two College Checking accounts are equal to one another
     * @param obj Account object.
     * @return Boolean: True if the accounts are equal; False if they are not equal.
     */
    @Override
    public boolean equals(Object obj){
        CollegeChecking acct = (CollegeChecking) obj;
        return super.equals(acct) && (schoolStatus == acct.getSchoolStatus());
    }
    /**
     * Method that represents an instance of College Checking as a string.
     * @return "College " followed by Checking class attributes for a non-loyal account; "Account not valid" otherwise.
     */
    @Override
    public String toString(){
        if(schoolStatus == 0){return "College" + super.toString() + "::" + "NEW_BRUNSWICK";}
        else if(schoolStatus == 1){return "College" + super.toString() + "::" + "NEWARK";}
        else if(schoolStatus == 2){return "College" + super.toString() + "::" + "CAMDEN";}
        else{return "Account not valid";}
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
     * Method that returns the monthly fee.
     * @return Double representing the fee associated with this instance of Checking.
     */
    @Override
    public double fee() {
        DecimalFormat fmt = new DecimalFormat("##,###0.00");
        return Double.parseDouble(fmt.format(MONTHLY_FEE));
    }

    /**
     * Method that uses a String to represent an instance of College Checking.
     * @return String saying "CollegeChecking" to represent an instance of College Checking.
     */
    @Override
    public String getType(){return "CollegeChecking";}

    public int getSchoolStatus(){return schoolStatus;}
}
