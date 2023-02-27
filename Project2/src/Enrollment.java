/**
 * This class defines the list of the students who are enrolled in classes this semester
 * @author Maanini Kantem, Michael Mankiewicz
 */
public class Enrollment {

    private EnrollStudent[] enrollStudents;
    private int size;

    /**
     * This method is used to increase the capacity of the enrollement array, when the students are added
     */
    private void grow() {
        int rosterIncrement = 4;
        int newCapacity = enrollStudents.length + rosterIncrement;
        EnrollStudent[] newEnrollStudents = new EnrollStudent[newCapacity];
        for(int i = 0; i < size; i++){
            newEnrollStudents[i] = enrollStudents[i];
        }
        enrollStudents = newEnrollStudents;
    }
    /**
     This method will add a registered student into the enrollment.
     @param enrollStudent the student to add.
     */

    public void add(EnrollStudent enrollStudent){
        if(contains(enrollStudent)){
            System.out.println(enrollStudent + " is already enrolled");
            return;
        }
        int rosterIncrement = 4;
        if(enrollStudents == null){
            enrollStudents = new EnrollStudent[rosterIncrement];
        }
        if(size >= enrollStudents.length){
            grow();
        }
        enrollStudents[size] = enrollStudent;
        size++;
        System.out.println(enrollStudent);
    }

    //move the last one in the array to replace the deleting index position

    /**
     * This method removes the registered student from the enrollment
     * @param enrollStudent the student to remove
     */
    public void remove(EnrollStudent enrollStudent){
        int indexOfEnrollStudent = find(enrollStudent);
        enrollStudents[indexOfEnrollStudent] = enrollStudents[size-1];
        size--;
    }

    /**
     * this method is created to help find the enrolled students in the list
     * @param enrollStudent to find the index of the element
     * @return the index of found, -1 if the enrollStudent was not found
     */
    private int find(EnrollStudent enrollStudent) {
        int NOT_FOUND = -1;
        for(int i = 0; i < size; i++){
            if(enrollStudents[i].equals(enrollStudent)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * This method was created to find the enrollStudent based on their profile
     * @param profile the profile of the enrollStudent
     * @return the index of found, -1 if the enrollStudent's profile was not found
     */
    public int findProfile(Profile profile){
        int NOT_FOUND = -1;
        for(int i = 0; i< size; i++){
            if(enrollStudents[i].getProfile().equals(profile)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * this method helps us get the enrollStudent we are tryign to access by the index
     * @param profile based on the profile of the enrollStudent
     * @return return the index of the enrollStudent if found, null if not found
     */
    public EnrollStudent getEnrollStudent(Profile profile){
        int NOT_FOUND = -1;
        int studentIndex = findProfile(profile);
        if(studentIndex == NOT_FOUND){
            return null;
        }
        return enrollStudents[studentIndex];
    }

    /**
     * this method uses the find method to let the user know if the student is in the list or not
     * @param enrollStudent based on the input it searches for the student in the list
     * @return true or false based on found or not found
     */
    public boolean contains(EnrollStudent enrollStudent){
        int NOT_FOUND = -1;
        return find(enrollStudent) != NOT_FOUND;
    }

    /**
     * this method is used to print the list of enrolledStudents in the list so far, based on their profiles, credits enrolled
     * this method prints the list without sorting them in any particular order
     */
    public void print() {
        String print = "";
        if (size == 0) {
            System.out.println("Enrollment is empty!");
            return;
        }

        print += "* Enrollment **\n";

        for (int i = 0; i < size; i++) {
            print += enrollStudents[i] + "\n";
        }
        System.out.print(print);
        System.out.println("* end of enrollment **");
    }//print the array as is without sorting

    /**
     * This method prints the enrolledStudents tuition due based on their status and the credits they are taking
     * @param roster takes the students from the roster and if they are in the enroll list, it will display the tuition due
     */
    public void printTuition(Roster roster) {
        String print = "";
        if (size == 0) {
            System.out.println("Enrollment is empty!");
            return;
        }

        print += "* Tuition due **\n";

        for (int i = 0; i < size; i++) {
            print += enrollStudents[i].printTuition(roster) + "\n";
        }
        System.out.print(print);
        System.out.println("* end of Tuition due **");
    }

    /**
     * This method adds the enrolled credits of the student to the total credits completed
     * then prints out all the students who have finished the credits over 120 and are eligible for graduation
     * @param roster
     */
    public void semesterEnd(Roster roster){
        for(int i = 0; i < size; i++) {
            EnrollStudent currEnrollStudent = enrollStudents[i];
            Profile currentProfile = currEnrollStudent.getProfile();
            int creds = currEnrollStudent.getCreditsEnrolled();
            if (roster.contains(currentProfile)) {
                roster.getStudent(currentProfile).addCredits(creds);
            }
        }
        printSemEnd(roster);
    }

    /**
     * this is helper method for the semesterEnd method
     * this method is then called on the semesterEnd method
     * @param roster it takes in the roster's student instance and prints the students based on the credits completed
     */
    public void printSemEnd(Roster roster){

            String print = "";
            if (size == 0) {
                System.out.println("Enrollment is empty!");
                return;
            }

            print += "* List of students eligible for Graduation **\n";

            for (int i = 0; i < size; i++) {
                EnrollStudent currEnrollStudent = enrollStudents[i];
                Profile currentProfile = currEnrollStudent.getProfile();
                if(roster.contains(currentProfile)){
                    Student student1 = roster.getStudent(currentProfile);
                    if(student1.getCreditCompleted() >= 120){
                        print += student1 + "\n";
                    }
                }

            }
            System.out.print(print);
            System.out.println("* end of list **");
        }
}



