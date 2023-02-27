import java.text.DecimalFormat;

public class EnrollStudent {
    private Profile profile;
    private int creditsEnrolled;

    public EnrollStudent(Profile profile, int creditsEnrolled){
        this.profile = profile;
        this.creditsEnrolled = creditsEnrolled;
    }

    public EnrollStudent(Profile profile){
        this.profile = profile;
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
        DecimalFormat df = new DecimalFormat("##,###.00");
        Student student = roster.getStudent(profile);
        return  student.enrollString() + ": credits enrolled: " + creditsEnrolled + " tuition due: $" + df.format(student.tuitionDue(creditsEnrolled));
    }

}
