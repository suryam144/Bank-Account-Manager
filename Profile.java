import java.util.Locale;

/**
 * Profile class, which handles the creation and implementation of a Profile object.
 * @author Isabelle Chang, Surya Mantha
 */
public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor method, which creates an instance of a Profile.
     * @param first String representing the first name of an account holder.
     * @param last String representing the last name of an account holder.
     * @param dob String representing the date of birth of an account holder.
     */
    public Profile(String first, String last, String dob){
        fname = first;
        lname = last;
        this.dob = new Date(dob);
    }

    /**
     * Method checking whether two profiles are the same.
     * @param tempProfile An instance of Profile that gets compared to another instance of Profile.
     * @return Boolean value false if Profile instances are not equal and true if they are.
     */
    public boolean equals(Profile tempProfile){
        return (fname.toLowerCase().compareTo(tempProfile.fname.toLowerCase()) == 0) && (lname.toLowerCase().compareTo(tempProfile.lname.toLowerCase())) == 0
                && (dob.compareTo(tempProfile.dob)) == 0;
    }

    /**
     * Method that represents a Profile object as a String.
     * @return String representing a Profile in the following format: "first name last name date of birth".
     */
    public String toString(){return fname + " " + lname + " " + dob;}

}
