import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Date class, which handles the implementation of a date system.
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUATERCENTENNIAL = 400;

    private static final int MAX_MONTHS = 12;
    private static final int MIN_MONTHS = 1;
    private static final int MIN_DAY = 1;
    private static final int THIRTY_ONE_MAX = 31;
    private static final int THIRTY_MAX = 30;
    private static final int[] THIRTY_ONE_DAYS = {1, 3, 5, 7, 8, 10, 12};
    private static final int FEBRUARY = 2;
    private static final int FEB_NON_LEAP_MAX = 28;
    private static final int FEB_LEAP_MAX = 29;

    /**
     * Constructor method, which creates an instance of Date in the following String format "mm/dd/yyyy"
     *
     * @param date String object giving the date in "mm/dd/yyyy" form
     */
    public Date(String date) {
        String[] dateComp = date.split("/");
        this.month = Integer.parseInt(dateComp[0]);
        this.day = Integer.parseInt(dateComp[1]);
        this.year = Integer.parseInt(dateComp[2]);
    }

    /**
     * Constructor method, which creates an instance of Date with today's date using the Calendar class.
     */
    public Date() {
        Calendar cal = Calendar.getInstance();
        this.month = cal.get((Calendar.MONTH)) + 1;
        this.day = cal.get((Calendar.DATE));
        this.year = cal.get((Calendar.YEAR));
    }

    /**
     * Checks whether the Date object has a valid month and date
     *
     * @return boolean value: True if the month and date are valid and false otherwise.
     */
    public boolean isValid() {
        Date today = new Date();
        int monthTest = 0;
        if (this.month < MIN_MONTHS || this.month > MAX_MONTHS) {
            return false;
        }
        if (this.day < MIN_DAY) {
            return false;
        }
        for (int i : THIRTY_ONE_DAYS) {
            if (i == this.month) {
                monthTest = 1;
                break;
            }
        }
        if (monthTest == 1) {
            return this.day <= THIRTY_ONE_MAX;
        } else if (this.month == FEBRUARY) {
            if (leapYearTest(this.year)) {
                return this.day <= FEB_LEAP_MAX;
            } else {
                return this.day <= FEB_NON_LEAP_MAX;
            }
        } else {
            return this.day <= THIRTY_MAX;
        }
    }

    /**
     * Checks whether a given year is a leap year or not.
     *
     * @param year An integer representing the year.
     * @return boolean value: True if the given year is a leap year and false otherwise.
     */
    public boolean leapYearTest(int year) {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                return year % QUATERCENTENNIAL == 0;
            }
            return true;
        }
        return false;
    }


    /**
     * Compares one instance of Date to another instance of Date.
     *
     * @param date A Date object.
     * @return integer: -1 if first Date is smaller than second Date; 0 if first and second Date instances are equal, 1 if first Date is larger than second Date
     */
    @Override
    public int compareTo(Date date) {
        if (this.year == date.year) {
            if (this.month == date.month) {
                return Integer.compare(this.day, date.day);
            }
            return Integer.compare(this.month, date.month);
        }
        return Integer.compare(this.year, date.year);
    }

    /**
     * Returns a date as a String in the format "mm/dd/yyyy"
     *
     * @return String: Date represented as a String
     */
    @Override
    public String toString() {
        String strOne = new DecimalFormat("0000").format(this.year);
        return this.month + "/" + this.day + "/" + strOne;
    }

    /**
     * Method to get the day within a date
     *
     * @return integer: day variable
     */
    public int getDay() {
        return day;
    }

    /**
     * Method to get the year within a date
     *
     * @return integer: year variable
     */
    public int getYear() {
        return year;
    }

    /**
     * Method to get the month within a date
     *
     * @return integer: month variable
     */
    public int getMonth() {
        return month;
    }
}