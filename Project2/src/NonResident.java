public class NonResident extends Student{

    /**
     *
     * @param profile the porfile of the student
     * @param major the major of the student
     * @param creditCompleted the amount of credits teh student finished
     */
    public NonResident(Profile profile, Major major, int creditCompleted) {
        super(profile, major, creditCompleted);
    }

    public double tuitionDue(int credits){
        double FULLTIMERATE = 29737;
        double PARTTIMEPC = 966;
        double UNIFEE = 3268;
        double PERCENT = 0.8;
        double OVERTIME = 16;
        double FULLTIME = 12;
        double PARTTIME = 3;

        double tuitionDue = 0;
        if(credits > OVERTIME){
            double extraCredits = credits - OVERTIME;
            tuitionDue = FULLTIMERATE + UNIFEE + (PARTTIMEPC * extraCredits);
        }else if(credits >= FULLTIME){
            tuitionDue = FULLTIMERATE + UNIFEE;
        }else if(credits >= PARTTIME){
            tuitionDue = (credits * PARTTIMEPC) + (PERCENT * UNIFEE);
        }
        return tuitionDue;
    }

    public boolean isResident() {
        return false;
    }
    public String enrollString(){
        return getProfile() + "(" + "Non-Resident" + ")";
    }

    @Override
    public String toString(){
        String standing;

        if(getCreditCompleted() < 30){
            standing = "Freshman";
        } else if(getCreditCompleted() < 60){
            standing = "Sophomore";
        } else if(getCreditCompleted() < 90){
            standing = "Junior";
        } else {
            standing = "Senior";
        }
        return getProfile() + " (" + getMajor().getMajorCode()+ " " + getMajor().getMajorName() + " " + getMajor().getSchoolName() + ") " + "credits completed: " + getCreditCompleted() + " (" + standing + ") (non-resident)";
    }
}
