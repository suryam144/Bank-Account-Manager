/**
 * AccountDatabase class, which handles the creation and implementation of an AccountDatabase object.
 */
public class AccountDatabase {
    private Account[] accounts;
    private int numAcct;

    private static final int INIT_ACCTS = 4;
    private static final int NOT_FOUND = -1;
    private static final int GROW_ARR = 4;

    /**
     * Constructor Method, which creates an instance of AccountDatabase
     */
    public AccountDatabase(){
        accounts = new Account[INIT_ACCTS];
        numAcct = 0;
    }

    /**
     * Finds the location of an account in the accounts array with an account as a parameter
     * @param account Account representing an account to be searched in the array
     * @return Returns the index of the location of the account; NOT_FOUND otherwise
     */
    private int find(Account account){
        for(int i = 0; i < numAcct; i++){
            if(accounts[i].getType().equals(account.getType())){
                if(accounts[i].getHolder().equals(account.getHolder())){
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }

    /**
     * Method that increases the size of the accounts array by 4
     */
    private void grow(){
        Account[] tempArr = new Account[numAcct + GROW_ARR];
        for(int i = 0; i < numAcct; i++){
            tempArr[i] = accounts[i];
        }
        accounts = tempArr;
    }

    /**
     * Method that opens an account and adds it to the accounts array
     * @param account Account representing an account to be added to the array
     * @return Boolean: Returns true if the account can be opened
     */
    public boolean open(Account account){
        if(account.getBalance() <= 0){return false;}
        else if(account.getType().equals("MoneyMarket") && account.getBalance() < 2500){return false;}
        if(accounts.length == numAcct && accounts.length != 0){
            grow();
        }
        numAcct++;
        accounts[numAcct - 1] = account;
        return true;
    }

    /**
     * Method that closes an account in the accounts array
     * @param account Account representing an account in the array
     * @return Boolean: true if the account is closed; false if the account is not in the array
     */
    public boolean close(Account account){
        int index = find(account);
        if(index == -1){return false;}
        accounts[index].closeAcct();
        accounts[index].setBalance(0);
        if(accounts[index].getType().equals("Savings") || accounts[index].getType().equals("MoneyMarket")){
            accounts[index].changeLoyal(0);
        }
        return true;
    }

    /**
     * Method that removes an account from the accounts array
     * @param prof Profile of an account
     * @param type Type of account the account is
     * @return Boolean: True of the account was removed;
     * false if the account does not exist in the array and therefore cannot be removed
     */
    public boolean removeAcct(Profile prof, String type){
        int acctIndex = isAlreadyClosed(prof, type);
        if(acctIndex == -1){return false;}
        for(int i = acctIndex; i < numAcct - 1; i++){
            accounts[i] = accounts[i + 1];
        }
        accounts[numAcct- 1] = null;
        numAcct--;
        return true;
    }

    /**
     * Method that represents a deposit for an account
     * @param account Account with the balance to be deposited
     */
    public void deposit(Account account){
        int index = find(account);
        accounts[index].deposit(account.balance);
    }
    /**
     * Method that represents a withdrawal for an account
     * @param account Account with the balance to be withdrawn
     * @return Boolean: True if the withdrawal was successful; false if it was not successful
     */
    public boolean withdraw(Account account){
        int index = find(account);
        if(accounts[index].getBalance() - account.balance < 0){return false;}
        accounts[index].withdraw(account.balance);
        return true;
    }

    /**
     * Method to print out contents of array
     */
    public void print(){
        for(int i = 0; i < numAcct; i++){
            System.out.println(accounts[i].toString());
        }
    }

    /**
     * Method to print out the contends of the array, sorted by account type
     */
    public void printByAccountType(){
        Account[] tempAccts = accounts;
        for(int i = 1; i < numAcct; i++){
            Account pivot = tempAccts[i];
            int j = i - 1;
            while(j >= 0 && accounts[j].getType().compareTo(pivot.getType()) > 0){
                tempAccts[j+1] = tempAccts[j];
                j = j - 1;
            }
            tempAccts[j + 1] = pivot;
        }
        for(int i = 0; i < numAcct; i++){
            System.out.println(accounts[i].toString());
        }
    }

    /**
     * Method to print out the contents of the array, including fee and monthly interest
     */
    public void printFeeAndInterest(){
        for(int i = 0; i < numAcct; i++){
            System.out.println(accounts[i].toString() + "::fee $" + accounts[i].fee() + "::monthly interest $" + accounts[i].monthlyInterest());
        }
    }

    /**
     * Method to print out the contents of the array, after fee and monthly interest have been included in the balances
     */
    public void printUpdate(){
        for(int i = 0; i < numAcct; i++){
            accounts[i].balance = accounts[i].balance - accounts[i].fee() + accounts[i].monthlyInterest();
        }
        print();
    }

    /**
     * Method to get the number of accounts in the array
     * @return Integer: numAcct variable
     */
    public int getNumAcct(){
        return numAcct;
    }

    /**
     * Method to determine if the account's holder exists already in the accounts array under the same type of account requested
     * @param prof Profile of account to be checked
     * @param type Type of account to be checked
     * @return Boolean: True if the account exists in the array; false if it does not exist in the array
     */
    public boolean sameHolder(Profile prof, String type){
        for(int i = 0; i < numAcct; i++){
            if(accounts[i].getHolder().equals(prof)){
                if((accounts[i].getType().equals("Checking") && (type.equals("C") || type.equals("CC")))
                        || (accounts[i].getType().equals("CollegeChecking") && (type.equals("C") || type.equals("CC")))
                        || (accounts[i].getType().equals("Savings") && (type.equals("S")))
                        || (accounts[i].getType().equals("MoneyMarket") && type.equals("MM"))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to deterine if the account requested is closed or not
     * @param prof Profile of account to be checked
     * @param type Type of account to be checked
     * @return index of the location of the account, if closed; NOT_FOUND if the account is not closed.
     */
    public int isAlreadyClosed(Profile prof, String type){
        for(int i = 0; i < numAcct; i++) {
            if (accounts[i].getHolder().equals(prof)) {
                if((accounts[i].getType().equals("Checking") && (type.equals("C") || type.equals("CC")))
                        || (accounts[i].getType().equals("CollegeChecking") && (type.equals("C") || type.equals("CC")))
                        || (accounts[i].getType().equals("Savings") && (type.equals("S")))
                        || (accounts[i].getType().equals("MoneyMarket") && type.equals("MM"))){
                    if(accounts[i].isClosed()){
                        return i;
                    }
                }
            }
        }
        return NOT_FOUND;
    }

    /**
     * Method that determines if an account is closed based on an inserted profile and account type
     * @param prof Profile of the account to be searched in the accounts array
     * @param type Type of the account to be searched in the array
     * @return Integer: index of the account in the array; -1 if it is not in the array
     */
    public int acctAccessCheck(Profile prof, String type){
        for(int i = 0; i < numAcct; i++) {
            if (accounts[i].getHolder().equals(prof)) {
                if((accounts[i].getType().equals("Checking") && (type.equals("C")))
                        || (accounts[i].getType().equals("CollegeChecking") && type.equals("CC"))
                        || (accounts[i].getType().equals("Savings") && (type.equals("S")))
                        || (accounts[i].getType().equals("MoneyMarket") && type.equals("MM"))){
                    if(accounts[i].isClosed()){
                        return i;
                    }
                }
            }
        }
        return NOT_FOUND;
    }
    /**
     * Method that determines if an account is closed based on an inserted profile and account type for money to be deposited
     * @param prof Profile of the account to be searched in the accounts array
     * @param type Type of the account to be searched in the array
     * @return Integer: index of the account in the array; -1 if it is not in the array
     */
    public int depositAccessCheck(Profile prof, String type){
        for(int i = 0; i < numAcct; i++) {
            if (accounts[i].getHolder().equals(prof)) {
                if((accounts[i].getType().equals("Checking") && (type.equals("C")))
                        || (accounts[i].getType().equals("CollegeChecking") && type.equals("CC"))
                        || (accounts[i].getType().equals("Savings") && (type.equals("S")))
                        || (accounts[i].getType().equals("MoneyMarket") && type.equals("MM"))){
                        return i;
                }
            }
        }
        return NOT_FOUND;
    }
}
