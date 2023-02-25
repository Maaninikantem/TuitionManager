public class EnrollStudent {
    private Profile profile;
    private int creditsEnrolled;

    public EnrollStudent(Profile profile, int creditsEnrolled){
        this.profile = profile;
        this.creditsEnrolled = creditsEnrolled;
    }

    public int getCreditsEnrolled() {
        return creditsEnrolled;
    }

    public Profile getProfile(){
        return profile;
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof EnrollStudent otherEnrollStudent){
            return profile.equals(otherEnrollStudent.profile) && creditsEnrolled == otherEnrollStudent.creditsEnrolled;
        }
        return false;
    }
    @Override
    public String toString(){
        return profile.toString() + ": credits enrolled: " + creditsEnrolled;
    }
    public String printTuition(Roster roster){
        Student student = roster.getStudent(getProfile());
        return  student.enrollString() + ": credits enrolled: " + creditsEnrolled + " tuition due: " + student.tuitionDue(creditsEnrolled);
    }

}
