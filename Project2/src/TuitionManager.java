import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 This class interprets commands given by the user and manipulates or displays the roster.
 @author Michael Mankiewicz
 */
public class TuitionManager {
    private boolean running;
    private Scanner scanner;
    Roster studentRoster = new Roster();
    Enrollment enrolledStudents = new Enrollment();
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
    private void addStudent(String typeOfStudent, String[] tokens) {
        String firstName = tokens[1];
        String lastName = tokens[2];
        String dateOfBirth = tokens[3];
        String majorStr = tokens[4].toUpperCase();
        Major majorMajor = Major.valueOf(majorStr);
        int credits;
        try {
            credits = Integer.parseInt(tokens[5]);
        } catch (NumberFormatException e) {
            System.out.println("Credits completed invalid: not an integer!");
            return;
        }

        boolean isStudyAbroad = false;
        String state = "";

        if(typeOfStudent.equals("T")){
            state = tokens[6].toUpperCase();
        }
        if(typeOfStudent.equals("I")){
            isStudyAbroad = Boolean.parseBoolean(tokens[6]);
        }

        Date theDateOfBirth = new Date(dateOfBirth);

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
        Student newStudent = null;

        switch(typeOfStudent){
            case "R":
                newStudent = new Resident(newProfile, majorMajor, credits);
                break;
            case "N":
                newStudent = new NonResident(newProfile, majorMajor, credits);
                break;
            case "T":
                newStudent = new TriState(newProfile, majorMajor, credits, state);
                break;
            case "I":
                newStudent = new International(newProfile, majorMajor, credits, isStudyAbroad);
                break;
        }

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
        Profile newProfile = new Profile(lastName, firstName, theDateOfBirth);
        Student studentToRemove = studentRoster.getStudent(newProfile);

        // remove student from roster
        if(studentRoster.remove(studentToRemove)){
            System.out.println(newProfile + " removed from the roster.");
        } else {
            System.out.println(newProfile + " is not in the roster.");
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
        Student studentToChangeMajor = studentRoster.getStudent(newProfile);

        //check if major exists
        if(!majorExists(majorStr)){
            System.out.println("Major code invalid: " + majorStr);
            return;
        }

        // change major
        if(studentRoster.changeMajor(studentToChangeMajor, Major.valueOf(majorStr))){
            System.out.println(newProfile + " major changed to " + majorStr);
        } else {
            System.out.println(newProfile + " is not in the roster.");
        }
    }

    public void loadStudents() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("studentList.txt"));
        while(scanner.hasNextLine())
        {
            String studentLine = scanner.nextLine();
            String[] studentParameters = studentLine.split(",");
            addStudent(studentParameters[0], studentParameters);
        }
    }

    public void enrollStudent(String[] tokens){
        String firstName = tokens[1];
        String lastName = tokens[2];
        String dateOfBirth = tokens[3];
        int creditsEnrolled;
        try {
            creditsEnrolled = Integer.parseInt(tokens[4]);
        } catch (NumberFormatException e) {
            System.out.println("Credits enrolled invalid: not an integer!");
            return;
        }

        Date date = new Date(dateOfBirth);
        Profile newProfile = new Profile(firstName, lastName, date);
        Major fillerMajor = null;
        int fillerCredits = 0;
        Student newStudent = new Resident(newProfile, fillerMajor, fillerCredits);

        if(!newStudent.isValid(creditsEnrolled)){
            System.out.println("Invalid credit hours");
        }

        EnrollStudent enrollStudent = new EnrollStudent(newProfile, creditsEnrolled);

        enrolledStudents.add(enrollStudent);
    }

    public void awardScholarship(String[] tokens){
        String firstName = tokens[1];
        String lastName = tokens[2];
        String dateOfBirth = tokens[3];
        int scholarship;
        if(tokens.length < 5){
            System.out.println("Missing data in line command");
            return;
        }

        try {
            scholarship = Integer.parseInt(tokens[4]);
        } catch (NumberFormatException e) {
            System.out.println("Amount is not an integer!");
            return;
        }

        Date date = new Date(dateOfBirth);
        Profile newProfile = new Profile(firstName, lastName, date);
        Student newStudent = studentRoster.getStudent(newProfile);

        if(newStudent == null) {
            System.out.println(newProfile + " is not in the roster");
        } else if (newStudent.isResident()){
            int creditsEnrolled = enrolledStudents.getEnrollStudent(newProfile).getCreditsEnrolled();
            if(creditsEnrolled < 12){
                System.out.println(newProfile + " part time student is not eligible for the scholarship.");
            }
            if(scholarship <= 0 || scholarship > 10000){
                System.out.println(scholarship + ": Invalid Amount");
            }
            Resident newResident = (Resident) newStudent;
            newResident.setScholarship(scholarship);
            System.out.println(newProfile + " scholarship amount updated");
        } else {
            System.out.println(newStudent + " is not eligible for scholarship");
        }

    }

    /**
     This method tokenizes the users input into an array, and executes different methods based on the first token.
     @param command the String the user typed in.
     */
    private void processCommand(String command) throws FileNotFoundException {
        String[] tokens = command.split("\\s+");
        String theCommand = tokens[0];

        switch (theCommand) {
            case "Q":
                running = false;
                break;
            case "AR":
                addStudent("R",tokens);
                break;
            case "AN":
                addStudent("N",tokens);
                break;
            case "AT":
                addStudent("T",tokens);
                break;
            case "AI":
                addStudent("I",tokens);
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
                loadStudents();
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
                printEnrollment();
                break;
            case "PT":
                printTuition();
                break;
            case "SE":
                semesterEnd(tokens);
                break;
            // add more cases to handle other commands
            default:
                System.out.println(theCommand + " is an invalid command!");
        }
    }

    private void printTuition() {
        studentsEnrolled.
    }

    private void dropStudent(String[] tokens) {
        String firstName = tokens[1];
        String lastName = tokens[2];
        String dateOfBirth = tokens[3];
        Date theDateOfBirth = new Date(dateOfBirth);

        Profile newProfile = new Profile(lastName, firstName, theDateOfBirth);

        // remove student from roster
        if(enrolledStudents.remove(newProfile)){
            System.out.println(newProfile + " removed from the roster.");
        } else {
            System.out.println(newProfile + " is not in the roster.");
        }
    }

    private void printEnrollment() {
        enrolledStudents.print();
    }

    /**
     This constructor initializes running to false, and creates a scanner instance.
     */
    public TuitionManager(){
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
