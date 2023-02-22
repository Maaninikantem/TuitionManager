public class EnrollStudent {
    private Profile profile;
    private int creditsEnrolled;

    public EnrollStudent(Profile profile, int creditsEnrolled){
        this.profile = profile;
        this.creditsEnrolled = creditsEnrolled;
    }

    public EnrollStudent(Profile profile) {
        this.profile = profile;
    }

    public EnrollStudent(int creditsEnrolled){
        this.creditsEnrolled = creditsEnrolled;
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
        return profile.toString() + " Credits Enrolled: " + creditsEnrolled;
    }

}
