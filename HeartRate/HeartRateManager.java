// Names: Kevin Vandenberg
// Class: CS 145
// NOTICE: CHATGPT, W3schools, GeeksforGeeks has been used as reference and for asking questions about various functions on Java.
//         Nothing has been directly copied from these sources. They have merely been used to further my understanding towards Java.
//
// SECOND NOTICE: I have changed the instance variables FOR HeartRates class to follow proper naming conventions
//                Take this into account while going over my submission as they were originally incorrect.
//                
// POSSIBLE EXTRA CREDIT:
// - I added a functionality to the program using Arraylist to let the user add as many people as they want
//   without restarting the program. This is found on line 21, and lines 219 to 247. Lines 219 - 247 consists of the
//   method used to add more people to the program
// - I've put this program together into a single file using package. It can be found on line 15
//   It's expecting to be in a folder called "HeartRate," which should be alongside the program
// - I've used try, catch, and throw throughout the program. Most of the time, this is used as error correction.
//   You can find an example at lines 238 - 245 and 230 - 236. I've used throw at line 323 and other locations in the program.

package HeartRate;
import java.util.Calendar;
import java.util.Scanner;
import java.util.ArrayList;
// Manager class for the program
public class HeartRateManager {
    private static ArrayList<HeartRates> People = new ArrayList<>(); // Stores all the people that may be added to the program
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        introduceProgram(); // Introduces the program
        boolean isSwitch = true;
        displayMainMenu(); // Displays main menu

        // Below is a loop for the user to select through the menu, which may navigate to other menus depending on the input.
        do {
            System.out.println("________________________________");
            String input = scanner.nextLine().toLowerCase().trim();
            isSwitch = menuSelection(input, scanner);
            if (input.equals("all") || input.equals("all") || input.equals("a") || input.equals("d") || input.equals("c")) {
                System.out.println("________________________________");
                displayMainMenu();
            }
        } while(isSwitch);
        scanner.close();

    }
    
    // This method takes the users input from the main menu selection, interpets it, and then executes a command
    // based on their input. This lets the user to execute the various commands that may bring them to the other menus.
    public static boolean menuSelection(String input, Scanner scanner) {
        boolean isSwitch = true;
        switch(input)  {
            case "a": {addPerson(scanner); break; } // Adds a new person object to the program
            case "d": {displayPersonInfo(scanner); break; } // Displays information of one chosen object
            case "all": {displayAll(); break; } // Displays all information about all objects
            case "c": {changeInformation(scanner); break; } // Changes a type of information of a chosen object
            case "q": {isSwitch = false; return isSwitch; } // quits the program
            case "display": {displayMainMenu(); break; } // Displays the menu again
            default: { // Default case if the command isnt recognized
                System.out.println("Command not found. Type another command.");
                System.out.println("If you need to see the menu again, type \"DISPLAY\"");
                break; }

        }
        return isSwitch;
    }
    
    // Displays all information of all objects that are currently stored in the program
    public static void displayAll() {
        for (HeartRates p : People) {
            System.out.println("________________________________");
            p.printInformation();
        }
        System.out.println("________________________________");
    }
    
    // Cycles through the array list to find a specific person from the Arraylist 
    public static int getPerson(int id, Scanner scanner) {
        while (true) {
            try {
                String storedName = null;
                String name = scanner.nextLine().toLowerCase().trim();
                int count = 0;
                // Goes through the array with each person, and looks for a specific person
                for (HeartRates p : People) {
                    String tempName = p.getFNAME().toLowerCase();
                    if (tempName.equals(name)) {
                        storedName = tempName;
                        id = count;
                    } count++;
                }
                if (name.equals("q")) { break; } // Case when the user wants to quit program
                // Case when name not found
                else if (storedName == null){ System.out.println("Name not found. Input another name or type \"Q\"");}
                else {break;}
            } catch(ArithmeticException e) {
                System.out.println("Ran into error while looking for desired Person");
                e.printStackTrace();
            }
        } return id;
    }
    
    // Main part for changing the information of one of the person objects
    // Calls the method getPerson to find the specific person they want to change
    // Then brings up another method to figure out what they want to change about that person
    public static void changeInformation(Scanner scanner) {
        int id = -1;
        System.out.println("Enter the name of the person that you would like to change information about (Type q to cancel)");
        id = getPerson(id, scanner);
        if (!(id == -1)) {
            changeSelection(scanner, id);
        }
    }
    
    // The method below is a continuation of the changeInformation method.
    // It asks the user to type a command, receives it, interprets it, and then executes the purpose of the command accordingly
    // Depending on the command, it will call a mutator method from the object and change the value.
    public static void changeSelection(Scanner scanner, int id) {
        boolean Switcher = true; while (Switcher) {
            // Line below displays menu information
            changeTypesMenu();System.out.printf("________________________________%n%s", "Type a command:");
            String input = scanner.nextLine().trim().toUpperCase();
            String changedString = null;
            // Checks if input is a specific value, if so it asks for what it will be changed to
            if (input.equals("LN") || input.equals("FN") || input.equals("YOB") || input.equals("MOB") || input.equals("DOB")) {
                System.out.printf("Type what you would like to change it to%n%s%n%s", "________________________________", "Input:");
                changedString = scanner.nextLine().trim(); }
            switch (input) {
                // LN and FN changes first name and last name
                case "LN": {People.get(id).setLNAME(changedString);System.out.println("Last name changed");Switcher = false;break;}
                case "FN": {People.get(id).setFNAME(changedString);System.out.println("First name Changed");Switcher = false;break;}
                case "YOB": { try { // This changes the Year of Birth.
                        int year = Integer.parseInt(changedString);
                        if (year < 0) {throw new ArithmeticException("Year not valid"); // May need to change the year exception
                        } else {People.get(id).setYOB(year); System.out.println("Year changed"); Switcher = false;}
                    } catch(Exception e ) {System.out.println("Inputted year isn\'t valid"); } break; }
                case "MOB": { try { // This changes the month of birth
                        int month = Integer.parseInt(changedString);
                        if (month > 12 || month < 1) {throw new ArithmeticException("Out of bounds");} 
                        else {People.get(id).setMOB(month); System.out.println("Month changed"); Switcher = false;}
                    } catch (Exception e) {System.out.println("Inputted month isnt valid.");} break; }
                case "DOB": { try { // This changes the date of birth
                        int day = Integer.parseInt(changedString);
                        if (day > 31 || day < 1) { throw new ArithmeticException("Out of bounds"); } 
                        else {People.get(id).setDOB(day);System.out.println("Day changed");Switcher = false;}
                    } catch (Exception e) { System.out.println("Inputted day isnt valid"); }break; }
                case "Q": {Switcher = false;break;} // This quits out of the loop
                default: {System.out.println("Command not recognized.");break;} // Default value if anything else is typed
            } System.out.println("________________________________"); }
    }
    
    // This is the menu for the changing a value of a person object.
    public static void changeTypesMenu() {
        System.out.println("Change Attribute Menu:");
        System.out.println("    LN: Change Last Name");
        System.out.println("    FN: Change First Name");
        System.out.println("    YOB: Change Year of Birth");
        System.out.println("    MOB: Change Year of Birth");
        System.out.println("    DOB: Change Year of Birth");
        System.out.println("    Q: Quit the command");
    }
    
    // This method is the beginning to display specific information about a person
    // It calls the getPerson method to figure out who the user wants to change, then brings them to the selection.
    public static void displayPersonInfo(Scanner scanner) {
        int id = -1;
        System.out.println("Enter the name of the person that you would like to display information about (Type q to cancel)");
        id = getPerson(id, scanner);
        if (!(id == -1)) {
            displaySelection(scanner, id);
        }
    }
    
    // This is the selection to display specific information about a person.
    // The purpose is to bring up a menu for the selection of the various commands to be done.
    // It will take these commands and then execute it accordingly
    public static void displaySelection(Scanner scanner, int id) {
        infoTypesMenu();
        boolean Switcher = true;
        while (Switcher) {
            System.out.print("Type a command:");
            String input = scanner.nextLine().trim().toUpperCase();
            System.out.println("________________________________");
            switch (input) {
                case "ALL": {People.get(id).printInformation(); break; } // Displays all information
                case "FN": {System.out.println("    " + People.get(id).getFNAME()); break; } // Displays just First Name
                case "LN": {System.out.println("    " + People.get(id).getLNAME()); break; } // Displays just last name
                case "AGE": {System.out.println("    " + People.get(id).getAGE()); break;} // displays just age
                // Displays just the target heart rate
                case "THA": {System.out.println("    " + People.get(id).getTargetHeartRate()); break;}
                // Displays the max heart rate
                case "MHA": {System.out.println("    " + People.get(id).getMaxHeartRate()); break;}
                case "DOB": { // Displays the date of birth
                    String dateOfBirth = People.get(id).getMOB() + "/" + People.get(id).getDOB() + "/" + People.get(id).getYOB();
                    System.out.println("    " + dateOfBirth);
                    break;
                }
                case "DISPLAY": {infoTypesMenu(); break;}
                case "Q": {Switcher = false; break;}
                default: {System.out.println("Command not recognized. Try again. Type \"DISPLAY\" if you need to see the menu again.");}

            }
           System.out.println("________________________________");
        }
    }
    
    // This is the menu of the various commands that can be done for displaying information about a specific person
    public static void infoTypesMenu() {
        System.out.println("Information Menu:");
        System.out.println("    ALL: Displays all information");
        System.out.println("    FN: First Name");
        System.out.println("    LN: Last Name");
        System.out.println("    AGE: Age");
        System.out.println("    THA: Target Heart Rate");
        System.out.println("    MHA: Max Heart Rate");
        System.out.println("    DOB: Date of Birth");
        System.out.println("    Q: Quit the command");
    }
    
    // This method goes through the process of adding a person to the program
    // It requests all the information needed, then it will create the object of the person
    // Afterwards, it adds the person to an ArrayList that contains all people in the program.
    public static void addPerson(Scanner scanner) {
        System.out.println("Provide the first name of the person");
        String firstName = scanner.nextLine().trim();
        System.out.println("Provide the last name of the person");
        String lastName = scanner.nextLine().trim();
        System.out.println("Provide the DOB of this person using the format MM/DD/YYYY");
        String dateOfBirth;
        int[] birth = {};
        // Loop to correct and ensure that the date of birth follows all requirements
        while (true) { // Has an inner loop as there are two different error points
            while (true) {
                try {
                    dateOfBirth = scanner.nextLine().trim();
                    birth = processDOB(dateOfBirth);
                    break;
                } catch (Exception e) {
                    System.out.println("Follow the format MM/DD/YYYY");
                }
            }
            try {
                HeartRates person = new HeartRates(firstName, lastName, birth[0], birth[1], birth[2]);
                People.add(person);
                break;
            } catch (Exception e) {
                System.out.println("The date of birth for this person is invalid");
                System.out.println("Provide the DOB of this person using the format MM/DD/YYYY");
            }
        }
    }

    //This method processes the date of birth from the format MM/DD/YYYY
    // It converts it into an array of {Year, month, day} and returns it back to wherever it was called
    public static int[] processDOB(String dateOfBirth) {
        int p1 = dateOfBirth.indexOf("/");
        int p2 = dateOfBirth.indexOf("/", (p1 + 1));
        int month = Integer.parseInt(dateOfBirth.substring(0, p1));
        int day = Integer.parseInt(dateOfBirth.substring((p1 + 1), p2));
        int year = Integer.parseInt(dateOfBirth.substring(p2 + 1));
        int[] dob = {year, month, day};
        return dob;
    }

    // This is the method to display the menu for the main menu
    public static void displayMainMenu() {
        System.out.println("Main Menu");
        System.out.println("    a: (Add Person) Add a person to the program");
        System.out.println("    all: Display's all people currenty within the system and their relevant information");
        System.out.println("    d: (Display information) Displays information about the person");
        System.out.println("    c: (Change information) Changes information about the person");
        System.out.println("    q: (Quit) quits the program");
    }
    
    // This introduces the program to the user
    public static void introduceProgram() {
        System.out.println("Welcome to the Target-Heart-Rate Calculator");
        System.out.println("You will provide information about a person");
        System.out.println("This program will then be processed");
        System.out.println("Afterwards, you can either display or change the information about a person");
        System.out.println("Input one of the valid commands from the menu to get started");
    }
}

// Object class for the program
class HeartRates {
    private String fName; // First Name
    private String lName; // Last Name
    // YOB = Year of Birth
    private int yob;
    // MOB = Month of Birth
    private int mob;
    // DOB = Day of Birth
    private int dob;
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private int age;

    // This is the constructor method for the object
    public HeartRates(String fName, String lName, int yob, int mob, int dob) {
        this.fName = fName;
        this.lName = lName;
        Calendar calendar = Calendar.getInstance();

        // Sets various information about the current date of the year
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Makes sure that the year is reasonable
        if ((currentYear - yob) > 110 || (currentYear - yob) < 0 || mob > 12 || mob < 1 || dob > 31 || dob < 1) {
            throw new ArithmeticException("The date of birth for this person is invalid");
        }
        this.age = currentYear - yob;
        if (mob > currentMonth && dob > this.currentDay) {
            this.age = this.age-1;
        }
        this.yob = yob;
        this.mob = mob;
        this.dob = dob;
    }
    
    // This method calculates the age when given a year, month, and day.
    private void calculateAge(int yob, int mob, int dob) {
        if ((this.currentYear - yob) > 110 || (this.currentYear - yob) < 0 || mob > 12 || mob < 1 || dob > 31 || dob < 1) {
            throw new ArithmeticException("The new date of birth is invalid. Make sure your input is valid");
        } else {
            this.age = this.currentYear - yob;
        }
        if (mob > this.currentMonth && dob > this.currentDay) {
            this.age = this.age -1;
        }
    }
    
    // The methods below are all mutator methods. They don't need too much explanation.
    // The one below is to set a given last name to the instance variable for last name.
    public void setLNAME(String lName) {
        this.lName = lName;
    }
    // First Name
    public void setFNAME(String fName) {
        this.fName = fName;
    }
    // Year of birth, things related to the birth date recalculates the age after it is called
    public void setYOB(int yob) {
        calculateAge(yob, this.mob, this.dob);
        this.yob = yob;
    }
    // Month of birth
    public void setMOB(int mob) {
        calculateAge(this.yob, mob, this.dob);
        this.mob = mob;
    }
    // Day of birth
    public void setDOB(int dob) {
        calculateAge(this.yob, this.mob, dob);
        this.dob = dob;
    }
    // End of mutator methods

    // These are the various getter methods
    // Last Name getter method, gets the value for the instance variable LNAME
    public String getLNAME(){
        return this.lName;
    }
    // First Name
    public String getFNAME(){
        return this.fName;
    }
    // Year of Birth
    public int getYOB(){
        return this.yob;
    }
    // Month of Birth
    public int getMOB(){
        return this.mob;
    }
    // Day of birth
    public int getDOB(){
        return this.dob;
    }
    // Age
    public int getAGE() {
        return age;
    }
    // Target heart rate
    public String getTargetHeartRate() {
        int maxRate = getMaxHeartRate();
        int upperBound = (int)Math.round(maxRate * 0.85);
        int lowerBound = (int)Math.round(maxRate * 0.5);
        String targetRate = lowerBound + " - " + upperBound;
        return targetRate;
    }
    // Max heart rate
    public int getMaxHeartRate() {
        int maxRate = 220 - this.age;
        return maxRate;
    }
    // End of getter methods

    // This method takes all of the various instance variables in the object, and then prints it out when executed.
    public void printInformation() {
        String targetRate = getTargetHeartRate();
        int maxRate = getMaxHeartRate();
        System.out.println("First Name: " + this.fName);
        System.out.println("Last Name: " +this.lName);
        System.out.println("Age: " + this.age);
        System.out.println("Maximum Heart rate: " + maxRate);
        System.out.println("Target Heart Rate: " + targetRate);

    }
}
