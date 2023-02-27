public class International extends NonResident{

    private boolean isStudyAbroad;

    public International(Profile profile, Major major, int creditCompleted, boolean isStudyAbroad) {

        super(profile, major, creditCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

    public double tuitionDue(int credits){
        double FULLTIMERATE = 29737;
        double PARTTIMEPC = 966;
        double UNIFEE = 3268;
        double PERCENT = 0.8;
        double OVERTIME = 16;
        double FULLTIME = 12;
        double PARTTIME = 3;
        double HEALTHINS = 2650;

        if(isStudyAbroad){
            FULLTIMERATE = 0;
            PARTTIMEPC = 0;
        }

        double tuitionDue = 0;
        if(credits > OVERTIME){
            double extraCredits = credits - OVERTIME;
            tuitionDue = FULLTIMERATE + UNIFEE + (PARTTIMEPC * extraCredits);
        }else if(credits >= FULLTIME){
            tuitionDue = FULLTIMERATE + UNIFEE;
        }else if(credits >= PARTTIME){
            tuitionDue = (credits * PARTTIMEPC) + (PERCENT * UNIFEE);
        }
        return tuitionDue + HEALTHINS;
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
        if(isStudyAbroad){
            return getProfile() + " (" + getMajor().getMajorCode()+ " " + getMajor().getMajorName() + " " + getMajor().getSchoolName() + ") " + "credits completed: " + getCreditCompleted() + " (" + standing + ") (international:study abroad)";
        } else{
            return getProfile() + " (" + getMajor().getMajorCode()+ " " + getMajor().getMajorName() + " " + getMajor().getSchoolName() + ") " + "credits completed: " + getCreditCompleted() + " (" + standing + ") (international)";
        }
    }
    public String enrollString(){
        String studyAbroad = "";
        if(isStudyAbroad){
            studyAbroad = "study abroad";
        }
        return getProfile() + "(" + "International student" + studyAbroad +")";
    }
    @Override
    public boolean isValid(int creditsEnrolled){
        if(isStudyAbroad){
            if(creditsEnrolled >= 3 && creditsEnrolled <= 12){
                return true;
            }
            System.out.println("(International studentstudy abroad) " + creditsEnrolled +": invalid credits hours.");
            return false;
        } else {
            if(creditsEnrolled >= 12 && creditsEnrolled <= 24){
                return true;
            }
            System.out.println("(International student) " + creditsEnrolled +": invalid credits hours.");
            return false;
        }
    }
}
