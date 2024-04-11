import java.util.Calendar;
public class HeartRates {
    private String FNAME;
    private String LNAME;
    // YOB = Year of Birth
    private int YOB;
    // MOB = Month of Birth
    private int MOB;
    // DOB = Day of Birth
    private int DOB;
    private int CURRENT_YEAR;
    private int CURRENT_MONTH;
    private int AGE;

    private HeartRates(String FNAME, String LNAME, int YOB, int MOB, int DOB) {
        this.FNAME = FNAME;
        this.LNAME = LNAME;
        Calendar calendar = Calendar.getInstance();
        CURRENT_YEAR = calendar.get(Calendar.YEAR);
        CURRENT_MONTH = calendar.get(Calendar.MONTH);
        if (CURRENT_YEAR - YOB > 110 || CURRENT_YEAR - YOB < 0) {
            throw new ArithmeticException("Error with Year of Birth");
        }
        int AGE = CURRENT_YEAR - this.YOB;
        if (MOB < CURRENT_MONTH) {
            AGE = AGE-1;
        }
        this.YOB = YOB;
        this.MOB = MOB;
        this.DOB = DOB;
    }

    // Mutator methods
    public void setLNAME(String LNAME) {
        this.LNAME = LNAME;
    }
    public void setFNAME(String FNAME) {
        this.FNAME = FNAME;
    }
    public void setYOB(int YOB) {
        this.YOB = YOB;
    }
    public void setMOB(int MOB) {
        this.MOB = MOB;
    }
    public void setDOB(int DOB) {
        this.DOB = DOB;
    }

    // Getter Methods
    public String getLNAME(){
        return this.LNAME;
    }
    public String getFNAME(){
        return this.FNAME;
    }
    public int getYOB(){
        return this.YOB;
    }
    public int getMOB(){
        return this.MOB;
    }
    public int getDOB(){
        return this.DOB;
    }

    public int getAge() {
        return AGE;
    }
    public String getTargetHeartRate() {
        int maxRate = 220 - AGE;
        int upperBound = (int)Math.round(maxRate * 0.85);
        int lowerBound = (int)Math.round(maxRate * 0.5);
        String targetRate = lowerBound + " - " + upperBound;
        return targetRate;
    }
    public int maxHeartRate() {
        int maxRate = 220 - AGE;
        return maxRate;
    }
    public void printInformation() {
        String targetRate = getTargetHeartRate();
        int maxRate = maxHeartRate();
        System.out.println("First Name: " + FNAME);
        System.out.println("Last Name: " +LNAME);
        System.out.println("Age: " + AGE);
        System.out.println("Maximum Heart rate: " + maxRate);
        System.out.println("Target Heart Rate: " +targetRate);

    }
}