import java.util.Scanner;

/**
 * BankTeller class, which handles and executes the creation of a BankTeller object.
 * @author Isabelle Chang, Surya Mantha
 */
public class BankTeller {
    private boolean boolCheck;
    private static final int ADD_COMP_CHECKING = 6;
    private static final int ADD_COMP = 7;
    private static final int CLOSE_COMP = 5;
    private static final int DEP_COMP = 6;
    private static final int WITH_COMP = 6;
    private static final int ALWAYS_LOYAL = 1;
    private static final int CLOSE_TEMP_VAR = 0;

    /**
     * Constructor method, which creates an instance of a BankTeller.
     */
    public BankTeller(){
        boolCheck = true;
    }

    /**
     * Method that manipulates and displays accounts based on commands read from the Scanner.
     */
    public void run(){
        System.out.println("Bank Teller is running.");
        AccountDatabase ad = new AccountDatabase();
        Scanner scan = new Scanner(System.in);
        while(boolCheck){
            String command = scan.nextLine();
            String[] components = command.split("\\s+");
            if(components[0].equals("O")) {ad = openAccount(components, ad);}
            else if(components[0].equals("C")) {ad = closeAccount(components, ad);}
            else if(components[0].equals("D")) {ad = deposit(components, ad);}
            else if(components[0].equals("W")) {ad = withdraw(components, ad);}
            else if(components[0].equals("P")) {displayOrder(ad);}
            else if(components[0].equals("PT")) {displayType(ad);}
            else if(components[0].equals("PI")) {displayFeeInterest(ad);}
            else if(components[0].equals("UB")) {displayUpdate(ad);} // any other parameters are needed to update balance?
            else if(components[0].equals("Q")){
                System.out.println("Bank Teller is terminated.");
                boolCheck = false;
            }
            else{System.out.println("Invalid command!");}
        }
    }

    /**
     * Method that opens an account and adds it to the account database.
     * @param comp String array object of components split up from the command read by the scanner.
     * @param ad AccountDatabase object to be manipulated based on the account.
     * @return AccountDatabase: AccountDatabase object after an account has been opened and added, or not.
     */

    public AccountDatabase openAccount(String[] comp, AccountDatabase ad){
        if((comp[1].equals("C") || comp[1].equals("MM")) && comp.length < ADD_COMP_CHECKING){System.out.println("Missing data for opening an account.");}
        else if(!(comp[1].equals("C") || comp[1].equals("MM")) && comp.length < ADD_COMP){System.out.println("Missing data for opening an account.");}
        else {
            Profile tempProfile = new Profile(comp[2], comp[3], comp[4]);
            Date tempDate = new Date(comp[4]);
            Date currDate = new Date();
            if(!isNumber(comp[5])){System.out.println("Not a valid amount.");}
            else if(Double.parseDouble(comp[5]) <= 0){System.out.println("Initial deposit cannot be 0 or negative.");}
            else if(comp[1].equals("MM") && Double.parseDouble(comp[5]) < 2500){System.out.println("Minimum of $2500 to open a MoneyMarket account.");}
            else if(ad.sameHolder(tempProfile,comp[1]) && ad.acctAccessCheck(tempProfile, comp[1]) == -1){System.out.println(tempProfile.toString() + " same account(type) is in the database.");}
            else if(!tempDate.isValid() || !(tempDate.compareTo(currDate) < 0)){System.out.println("Date of birth invalid.");}
            else if(comp[1].equals("CC") && !(Integer.parseInt(comp[6]) >= 0 && Integer.parseInt(comp[6]) <= 2)){System.out.println("Invalid campus code.");}
            else{
                if(ad.isAlreadyClosed(tempProfile, comp[1]) != -1){
                    ad.removeAcct(tempProfile, comp[1]);
                    System.out.println("Account reopened.");
                }
                else {
                    System.out.println("Account opened.");
                }
                if (comp[1].equals("C")) {ad.open(new Checking(tempProfile, false, Double.parseDouble(comp[5])));}
                else if (comp[1].equals("CC")) {ad.open(new CollegeChecking(tempProfile, false, Double.parseDouble(comp[5]), Integer.parseInt(comp[6])));}
                else if (comp[1].equals("S")) {ad.open(new Savings(tempProfile, false, Double.parseDouble(comp[5]), Integer.parseInt(comp[6])));}
                else if (comp[1].equals("MM")) {ad.open(new MoneyMarket(tempProfile, false, Double.parseDouble(comp[5]), ALWAYS_LOYAL));}
            }
        }
        return ad;
    }

    /**
     * Method that closes an account and deletes it from the account database if it has not already been closed. If isAlreadyClosed class is true, the AccountDatabase class does nothing.
     * @param comp String array object of components split up from the command read by the scanner.
     * @param ad AccountDatabase object to be manipulated based on the account.
     * @return AccountDatabse: AccountDatabase object after an account has been closed and deleted, or not.
     */
    public AccountDatabase closeAccount(String[] comp, AccountDatabase ad){
        if(comp.length < CLOSE_COMP){
            System.out.println("Missing data for closing an account.");
        }
        else{
            Profile tempProfile = new Profile(comp[2], comp[3], comp[4]);
            if(ad.isAlreadyClosed(tempProfile, comp[1]) != -1){System.out.println("Account is closed already");}
            if(comp[1].equals("C")){ad.close(new Checking(tempProfile, false, CLOSE_TEMP_VAR));}
            else if(comp[1].equals("CC")){ad.close(new CollegeChecking(tempProfile, false, CLOSE_TEMP_VAR, CLOSE_TEMP_VAR));}
            else if(comp[1].equals("S")){ad.close(new Savings(tempProfile, false,CLOSE_TEMP_VAR, CLOSE_TEMP_VAR));}
            else if(comp[1].equals("MM")){ad.close(new MoneyMarket(tempProfile, false, CLOSE_TEMP_VAR, ALWAYS_LOYAL));}
            System.out.println("Account closed.");
        }
        return ad;
    }

    /**
     * Method that updates the balance in an Account object with a deposit.
     * @param comp array object of components split up from the command read by the scanner.
     * @param ad AccountDatabase object to be manipulated based on the account whose balance is being changed.
     * @return AccountDatabase: AccountDatabase object after an account has received a deposit, or not.
     */
    public AccountDatabase deposit(String[] comp, AccountDatabase ad){
        if(comp.length < DEP_COMP){
            System.out.println("Missing data for opening an account.");
            return ad;
        }
        Profile tempProfile = new Profile(comp[2], comp[3], comp[4]);
        if(ad.depositAccessCheck(tempProfile, comp[1]) == -1){
            if (comp[1].equals("C")) {System.out.println(tempProfile.toString() + " Checking" + " is not in the database.");}
            else if (comp[1].equals("CC")) {System.out.println(tempProfile.toString() +  " College Checking" + " is not in the database.");}
            else if (comp[1].equals("S")) {System.out.println(tempProfile.toString() +  " Savings" + " is not in the database.");}
            else if (comp[1].equals("MM")) {System.out.println(tempProfile.toString() +  " Money Market" + " is not in the database.");}
        }
        else if(!isNumber(comp[5])){System.out.println("Not a valid amount.");}
        else if(Double.parseDouble(comp[5]) <= 0){System.out.println("Deposit - amount cannot be 0 or negative.");}
        else{
            if (comp[1].equals("C")) {ad.deposit(new Checking(tempProfile, false, Double.parseDouble(comp[5])));}
            else if (comp[1].equals("CC")) {ad.deposit(new CollegeChecking(tempProfile, false, Double.parseDouble(comp[5]), CLOSE_TEMP_VAR));}
            else if (comp[1].equals("S")) {ad.deposit(new Savings(tempProfile, false, Double.parseDouble(comp[5]), CLOSE_TEMP_VAR));}
            else if (comp[1].equals("MM")) {ad.deposit(new MoneyMarket(tempProfile, false, Double.parseDouble(comp[5]), ALWAYS_LOYAL));}
            System.out.println("Deposit - Balance updated.");
        }
        return ad;
    }

    /**
     * Method that updates the balance in an Account object with a withdrawal.
     * @param comp array object of components split up from the command read by the scanner.
     * @param ad AccountDatabase object to be manipulated based on the account whose balance is being changed.
     * @return AccountDatabase: AccountDatabase object after an account has received a withdrawal, or not.
     */
    public AccountDatabase withdraw(String[] comp, AccountDatabase ad){
        if(comp.length < WITH_COMP){
            System.out.println("Missing data for opening an account.");
            return ad;
        }
        Profile tempProfile = new Profile(comp[2], comp[3], comp[4]);
        boolean withTest = true;
        if(ad.depositAccessCheck(tempProfile, comp[1]) == -1){
            if (comp[1].equals("C")) {System.out.println(tempProfile.toString() + " Checking" + " is not in the database.");}
            else if (comp[1].equals("CC")) {System.out.println(tempProfile.toString() +  " College Checking" + " is not in the database.");}
            else if (comp[1].equals("S")) {System.out.println(tempProfile.toString() +  " Savings" + " is not in the database.");}
            else if (comp[1].equals("MM")) {System.out.println(tempProfile.toString() +  " Money Market" + " is not in the database.");}
        }
        else if(!isNumber(comp[5])){System.out.println("Not a valid amount.");}
        else if(Double.parseDouble(comp[5]) <= 0){System.out.println("Withdraw - amount cannot be 0 or negative.");}
        else{
            if (comp[1].equals("C")) {withTest = ad.withdraw(new Checking(tempProfile, false, Double.parseDouble(comp[5])));}
            else if (comp[1].equals("CC")) {withTest = ad.withdraw(new CollegeChecking(tempProfile, false, Double.parseDouble(comp[5]), CLOSE_TEMP_VAR));}
            else if (comp[1].equals("S")) {withTest = ad.withdraw(new Savings(tempProfile, false, Double.parseDouble(comp[5]), CLOSE_TEMP_VAR));}
            else if (comp[1].equals("MM")) {withTest = ad.withdraw(new MoneyMarket(tempProfile, false, Double.parseDouble(comp[5]), ALWAYS_LOYAL));}
            if(withTest){System.out.println("Withdraw - Balance updated.");}
            else{System.out.println("Withdraw - insufficient fund.");}
        }
        return ad;
    }

    /**
     * Method that displays all accounts in the database based on the current order that they are in.
     * @param ad AccountDatabase object to be displayed.
     */
    public void displayOrder(AccountDatabase ad){
        if(ad.getNumAcct() == 0){System.out.println("Account Database is empty!");}
        else {
            System.out.println("\n*list of accounts in the database.*");
            ad.print();
            System.out.println("end of list.*\n");
        }
    }

    /**
     * Method that displays all accounts in the database by type in alphabetical order.
     * @param ad AccountDatabase object whose accounts are to be manipulated and displayed.
     */
    public void displayType(AccountDatabase ad){
        if(ad.getNumAcct() == 0){System.out.println("Account Database is empty!");}
        else {
            System.out.println("\n*list of accounts by account type.");
            ad.printByAccountType();
            System.out.println("end of list.\n");
        }
    }

    /**
     * Method that displays all accounts in database, and their fees and monthly interest.
     * @param ad AccountDatabase object whose accounts are to be manipulated and displayed.
     */
    public void displayFeeInterest(AccountDatabase ad){
        if(ad.getNumAcct() == 0){System.out.println("Account Database is empty!");}
        else {
            System.out.println("\n*list of accounts with fee and monthly interest.*");
            ad.printFeeAndInterest();
            System.out.println("end of list.\n");
        }
    }

    /**
     * Method that updates balances in each account according to calculated fees and monthly interests, and then displays the resulting account database.
     * @param ad object whose accounts are to be manipulated and displayed.
     */
    public void displayUpdate(AccountDatabase ad){
        if(ad.getNumAcct() == 0){System.out.println("Account Database is empty!");}
        else {
            System.out.println("\n*list of accounts with updated balance.*");
            ad.printUpdate();
            System.out.println("end of list.\n");
        }

    }

    /**
     * (not sure about this one) Method that checks whether a given String value is a number.
     * @param amt String that method checks for numerical status.
     * @return False if amt is not a number and true otherwise.
     */
    public boolean isNumber(String amt){
        if(amt == null){return false;}
        try{
            double temp = Double.parseDouble(amt);
        } catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }

}