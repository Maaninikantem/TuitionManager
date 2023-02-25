/**
 This class is responsible for executing basic commands to manipulate or display a roster.
 @author Michael Mankiewicz
 */
public class Roster {
    private Student[] roster;
    private int size;
    /**
     This method will change a student's major in the roster.
     @param student the student to change the major for.
     @param newMajor the major to change to.
     @return true if the student exists.
     */
    public boolean changeMajor(Student student, Major newMajor){
        if (contains(student)){
            int studentIndex = find(student);
            roster[studentIndex].setMajor(newMajor);
            return true;
        }
        return false;
    }
    /**
     This method will find the index of where the student is in the roster.
     @param student the student to search for.
     @return the index of the student in the roster array.
     */
    private int find(Student student) {
        for(int i = 0; i< size; i++){
            if(roster[i].equals(student)){
                return i;
            }
        }
        return -1;
    } //search the given student in roster
    /**
     This method will increase the size of the roster by 4.
     */
    private void grow() {
        int rosterIncrement = 4;
        int newCapacity = roster.length + rosterIncrement;
        Student[] newRoster = new Student[newCapacity];
        for(int i = 0; i < size; i++){
            newRoster[i] = roster[i];
        }
        roster = newRoster;
    } //increase the array capacity by 4
    /**
     This method will add a new student element into the roster.
     @param student the student to add.
     @return false if student already in roster.
     */
    public boolean add(Student student){
        if(contains(student)){
            return false;
        }
        int rosterIncrement = 4;
        if(roster == null){
            roster = new Student[rosterIncrement];
        }
        if(size >= roster.length){
            grow();
        }
        roster[size] = student;
        size++;
        return true;
    } //add student to end of array
    /**
     This method will remove a student from the roster.
     @param student the student to remove.
     @return false if student not in roster.
     */
    public boolean remove(Student student){
        if(!contains(student)){
            return false;
        }
        for(int i = 0; i < size; i++){
            if(roster[i].equals(student)){
                for(int j = 0; j < (size - i - 1); j++){
                    roster[i + j] = roster[i + j + 1];
                }
                roster[size - 1] = null;
                size--;
            }
        }
        return true;
    }
    public Student getStudent(Profile profile){
        int studentIndex = findProfile(profile);
        if(studentIndex == -1){
            return null;
        }
        return roster[studentIndex];
    }

    private int findProfile(Profile profile) {
        for(int i = 0; i< size; i++){
            if(roster[i].getProfile().equals(profile)){
                return i;
            }
        }
        return -1;
    }
    //maintain the order after remove
    /**
     This method will determine if a student is already in the roster.
     @param student the student to search for.
     @return true if student in roster.
     */
    public boolean contains(Student student){
        return find(student) != -1;
    } //if the student is in roster
    /**
     This method will sort the roster by last name, first name, and date of birth.
     @param schoolFilter which school to sort. if equal to "all" then all schools will be sorted.
     */
    void sortDefault(String schoolFilter) {
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < size-1; i++)
        {
            if(!roster[i].getMajor().getSchoolName().equals(schoolFilter) && !schoolFilter.equals("all")){
                continue;
            }
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < size; j++){
                if(!roster[j].getMajor().getSchoolName().equals(schoolFilter) && !schoolFilter.equals("all")){
                    continue;
                }
                if (roster[j].compareTo(roster[min_idx]) < 0)
                    min_idx = j;
            }
            Student temp = roster[min_idx];
            roster[min_idx] = roster[i];
            roster[i] = temp;
        }
    }
    /**
     This method will determine if a student's academic standing is greater or less than another.
     @param student1 the student to compare from.
     @param student2 the student to compare to.
     @return 1 if a student has a great academic standing, -1 if less, and 0 if the same.
     */
    int compareStanding(Student student1, Student student2){

        int standing1;
        if(student1.getCreditCompleted() < 30){
            standing1 = 1;
        } else if(student1.getCreditCompleted() < 60){
            standing1 = 2;
        } else if(student1.getCreditCompleted() < 90){
            standing1 = 3;
        } else {
            standing1 = 4;
        }

        int standing2;
        if(student2.getCreditCompleted() < 30){
            standing2 = 1;
        } else if(student2.getCreditCompleted() < 60){
            standing2 = 2;
        } else if(student2.getCreditCompleted() < 90){
            standing2 = 3;
        } else {
            standing2 = 4;
        }

        if(standing1 > standing2){
            return 1;
        } else if (standing1 < standing2){
            return -1;
        }
        return 0;
    }
    /**
     This method will sort the roster by academic standing only.
     */
    void sortStanding() {
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < size-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < size; j++){

                if (compareStanding(roster[j],roster[min_idx]) < 0)
                    min_idx = j;
            }
            // Swap the found minimum element with the first
            // element
            Student temp = roster[min_idx];
            roster[min_idx] = roster[i];
            roster[i] = temp;
        }
    }
    /**
     This method will sort the roster by school and major.
     */
    void sortSchool() {
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < size-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < size; j++){

                if (roster[j].getMajor().compareTo(roster[min_idx].getMajor()) < 0)
                    min_idx = j;
            }
            // Swap the found minimum element with the first
            // element
            Student temp = roster[min_idx];
            roster[min_idx] = roster[i];
            roster[i] = temp;
        }
    }
    /**
     This method will display the roster in last name, first name, and DOB order.
     */
    public void print () {
        //sorting by last name first name, DOB
        String schoolFilter = "all";
        sortDefault(schoolFilter);

        String string = "";
        if(roster != null) {
            for (int i = 0; i < size; i++) {
                string += roster[i] + "\n";
            }
        } else {
            System.out.println("Student roster is empty!");
            return;
        }

        if(!string.equals("")){
            string = string.substring(0, string.length() - 1) ;
        } else {
            string = "";
        }
        System.out.println("* Student roster sorted by last name, first name, DOB **");
        System.out.println(string);
        System.out.println("* end of roster **");
    } //print roster sorted by profiles
    /**
     This method will display the roster in school and major order.
     */
    public void printBySchoolMajor() {
        sortSchool();
        String string = "";
        if(roster != null) {
            for (int i = 0; i < size; i++) {
                string += roster[i] + "\n";
            }
        } else {
            System.out.println("Student roster is empty!");
            return;
        }
        if(!string.equals("")){
            string = string.substring(0, string.length() - 1) ;
        } else {
            string = "";
        }
        System.out.println("* Student roster sorted by school, major **");
        System.out.println(string);
        System.out.println("* end of roster **");
    } //print roster sorted by school major
    /**
     This method will display the roster in increasing academic standing order.
     */
    public void printByStanding() {
        sortStanding();
        String string = "";
        if(roster != null) {
            for (int i = 0; i < size; i++) {
                string += roster[i] + "\n";
            }
        } else {
            System.out.println("Student roster is empty!");
            return;
        }
        if(!string.equals("")){
            string = string.substring(0, string.length() - 1) ;
        } else {
            string = "";
        }
        System.out.println("* Student roster sorted by standing **");
        System.out.println(string);
        System.out.println("* end of roster **");
    } //print roster sorted by standing
    /**
     This method will list the students in a specific school in last name, first name, and DOB order.
     @param schoolName the school to display students from.
     */
    public void printInSchool(String schoolName){
        sortDefault(schoolName);

        String string = "";
        if(roster != null) {
            for (int i = 0; i < size; i++) {
                if (roster[i].getMajor().getSchoolName().equals(schoolName)) {
                    string += roster[i] + "\n";
                }
            }
        } else {
            System.out.println("Student roster is empty!");
            return;
        }

        if(!string.equals("")){
            string = string.substring(0, string.length() - 1) ;
        } else {
            System.out.println("School doesn't exist: " + schoolName);
            return;
        }
        System.out.println("* Students in " + schoolName + " **");
        System.out.println(string);
        System.out.println("* end of list **");
    }
}
