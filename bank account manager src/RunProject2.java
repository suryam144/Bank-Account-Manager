/**
 * RunProject2 class, which executes all other classes. Uses main method to call the run method in the BankTeller class.
 */
public class RunProject2 {
    public static void main(String[] args){
        BankTeller bank = new BankTeller();
        bank.run();
    }
}