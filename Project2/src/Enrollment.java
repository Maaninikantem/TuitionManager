public class Enrollment {

    private EnrollStudent[] enrollStudents;
    private int size;
    private void grow() {
        int rosterIncrement = 4;
        int newCapacity = enrollStudents.length + rosterIncrement;
        EnrollStudent[] newEnrollStudents = new EnrollStudent[newCapacity];
        for(int i = 0; i < size; i++){
            newEnrollStudents[i] = enrollStudents[i];
        }
        enrollStudents = newEnrollStudents;
    } //increase the array capacity by 4
    /**
     This method will add a new student element into the roster.
     @param student the student to add.
     @return false if student already in roster.
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


    public void remove(EnrollStudent enrollStudent){
        int indexOfEnrollStudent = find(enrollStudent);
        enrollStudents[indexOfEnrollStudent] = enrollStudents[size-1];
        size--;
    }

    private int find(EnrollStudent enrollStudent) {
        for(int i = 0; i < size; i++){
            if(enrollStudents[i].equals(enrollStudent)){
                return i;
            }
        }
        return -1;
    }
    public int findProfile(Profile profile){
        for(int i = 0; i< size; i++){
            if(enrollStudents[i].getProfile().equals(profile)){
                return i;
            }
        }
        return -1;
    }
    public EnrollStudent getEnrollStudent(Profile profile){
        int studentIndex = findProfile(profile);
        if(studentIndex == -1){
            return null;
        }
        return enrollStudents[studentIndex];
    }
    public boolean contains(EnrollStudent enrollStudent){
        return find(enrollStudent) != -1;
    }
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
    public void printTuition(Roster roster) {
        String print = "";
        if (size == 0) {
            System.out.println("Enrollment is empty!");
            return;
        }

        print += "* Enrollment **\n";

        for (int i = 0; i < size; i++) {
            print += enrollStudents[i].printTuition(roster) + "\n";
        }
        System.out.print(print);
        System.out.println("* end of enrollment **");
    }

    public void semesterEnd(Roster roster){
        for(int i = 0; i <= size; i++) {
            EnrollStudent currEnrollStudent = enrollStudents[i];
            Profile currentProfile = currEnrollStudent.getProfile();
            int creds = currEnrollStudent.getCreditsEnrolled();
            if (roster.contains(currentProfile)) {
                roster.getStudent(currentProfile).addCredits(creds);
            }
        }
        printSemEnd(roster);
    }

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



