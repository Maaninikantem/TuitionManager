import java.util.Scanner;
/**
 This class interprets commands given by the user and manipulates or displays the roster.
 @author Michael Mankiewicz
 */
public class TuitionManager {
    private boolean running;
    private Scanner scanner;
    Roster studentRoster = new Roster();
    /**
     This method determines if a given major exists within the major enum.
     @param majorName the major to search for.
     @return true if the major exists within the enum.
     */
    private Boolean majorExists(String majorName){
        boolean majorExists = false;
        for (Major major : Major.values()) {
            if (major.name().equals(majorName)) {
                majorExists = true;
                break;
            }
        }
        return majorExists;
    }
    /**
     This method will validate a users input and then add a new student into the roster.
     @param tokens the commands the user inputs.
     */
    private void addStudent(String[] tokens) {
        String firstName = tokens[1];
        String lastName = tokens[2];
        String dateOfBirth = tokens[3];
        String majorStr = tokens[4].toUpperCase();
        Date theDateOfBirth = new Date(dateOfBirth);
        int credits;
        try {
            credits = Integer.parseInt(tokens[5]);
        } catch (NumberFormatException e) {
            System.out.println("Credits completed invalid: not an integer!");
            return;
        }
        if (credits < 0) {
            System.out.println("Credits completed invalid: cannot be negative!");
            return;
        }
        if(!theDateOfBirth.isValid()){
            System.out.println("DOB invalid: " + theDateOfBirth + " not a valid calendar date!" );
            return;
        }
        if(!theDateOfBirth.isAtleastSixteen()){
            System.out.println("DOB invalid: " + theDateOfBirth + " younger than 16 years old." );
            return;
        }
        if(!majorExists(majorStr)){
            System.out.println("Major code invalid: " + majorStr);
            return;
        }
        Profile newProfile = new Profile(lastName, firstName, theDateOfBirth);
        Student newStudent = new Student(newProfile, Major.valueOf(majorStr), credits);
        if(studentRoster.add(newStudent)){
            System.out.println(newStudent.getProfile().toString() + " added to the roster.");
        } else {
            System.out.println(newStudent.getProfile().toString() + " is already in the roster.");
        }
    }
    /**
     This method will validate a users input and then remove a student from the roster.
     @param tokens the commands the user inputs.
     */
    private void removeStudent(String[] tokens) {
        String firstName = tokens[1];
        String lastName = tokens[2];
        String dateOfBirth = tokens[3];
        Date theDateOfBirth = new Date(dateOfBirth);

        // Validate date of birth
        if(!theDateOfBirth.isValid()){
            System.out.println("DOB invalid: " + theDateOfBirth + " not a valid calendar date!" );
            return;
        }
        if(!theDateOfBirth.isAtleastSixteen()){
            System.out.println("DOB invalid: " + theDateOfBirth + " younger than 16 years old." );
            return;
        }

        Profile newProfile = new Profile(lastName, firstName, theDateOfBirth);
        Student newStudent = new Student(newProfile);

        // remove student from roster
        if(studentRoster.remove(newStudent)){
            System.out.println(newStudent.getProfile().toString() + " removed from the roster.");
        } else {
            System.out.println(newStudent.getProfile().toString() + " is not in the roster.");
        }
    }
    /**
     This method will run the print command in the roster.
     */
    private void printStudents(){
        studentRoster.print();
    }
    /**
     This method will run the printByStanding command in the roster.
     */
    private void printStanding(){
        studentRoster.printByStanding();
    }
    /**
     This method will run the printBySchoolMajor command in the roster.
     */
    private void printBySchoolMajor(){
        studentRoster.printBySchoolMajor();
    }
    /**
     This method will run the printInSchool command in the roster.
     Will capitalize the schoolName first.
     */
    private void printInSchool(String[] tokens){
        String schoolName = tokens[1].toUpperCase();
        studentRoster.printInSchool(schoolName);
    }
    /**
     This method validates a user's inputs and then changes a given student's major.
     @param tokens the user's inputs.
     */
    private void changeMajor(String[] tokens){
        String firstName = tokens[1];
        String lastName = tokens[2];
        String dateOfBirth = tokens[3];
        String majorStr = tokens[4].toUpperCase();
        Date theDateOfBirth = new Date(dateOfBirth);

        // Validate date of birth
        if(!theDateOfBirth.isValid()){
            System.out.println("DOB invalid: " + theDateOfBirth + " not a valid calendar date!" );
            return;
        }
        if(!theDateOfBirth.isAtleastSixteen()){
            System.out.println("DOB invalid: " + theDateOfBirth + " younger than 16 years old." );
            return;
        }

        Profile newProfile = new Profile(lastName, firstName, theDateOfBirth);
        Student newStudent = new Student(newProfile);

        //check if major exists
        if(!majorExists(majorStr)){
            System.out.println("Major code invalid: " + majorStr);
            return;
        }

        // change major
        if(studentRoster.changeMajor(newStudent, Major.valueOf(majorStr))){
            System.out.println(newStudent.getProfile().toString() + " major changed to " + majorStr);
        } else {
            System.out.println(newStudent.getProfile().toString() + " is not in the roster.");
        }
    }
    /**
     This method tokenizes the users input into an array, and executes different methods based on the first token.
     @param command the String the user typed in.
     */
    private void processCommand(String command){
        String[] tokens = command.split("\\s+");
        String theCommand = tokens[0];

        switch (theCommand) {
            case "Q":
                running = false;
                break;
            case "AR":
                addResident(tokens);
                break;
            case "AN":
                addNonResident(tokens);
                break;
            case "AT":
                addTristate(tokens);
                break;
            case "AI":
                addInternational(tokens);
                break;
            case "R":
                removeStudent(tokens);
                break;
            case "P":
                printStudents();
                break;
            case "PS":
                printStanding();
                break;
            case "PC":
                printBySchoolMajor();
                break;
            case "L":
                printInSchool(tokens);
                break;
            case "LS":
                loadStudents(tokens);
                break;
            case "C":
                changeMajor(tokens);
                break;
            case "E":
                enrollStudent(tokens);
                break;
            case "D":
                dropStudent(tokens);
                break;
            case "S":
                awardScholarship(tokens);
                break;
            case "PE":
                printEnrollment(tokens);
                break;
            case "PT":
                printTuition(tokens);
                break;
            case "SE":
                semesterEnd(tokens);
                break;
            // add more cases to handle other commands
            default:
                System.out.println(theCommand + " is an invalid command!");
        }
    }
    /**
     This constructor initializes running to false, and creates a scanner instance.
     */
    public RosterManager(){
        running = false;
        scanner = new Scanner(System.in);
    }
    /**
     This method turns the boolean running to true, and starts reading user input
     */
    public void run(){
        running = true;
        System.out.println("Roster Manager running...");
        while(running){
            String command = scanner.nextLine();
            processCommand(command);
        }
        System.out.println("Roster Manager terminated");
    }
}
