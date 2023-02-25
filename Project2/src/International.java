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
        int OVERTIME = 16;
        int FULLTIME = 12;
        int PARTTIME = 3;
        double HEALTHINS = 2650;

        if(isStudyAbroad){
            FULLTIMERATE = 0;
            PARTTIMEPC = 0;
        }

        double tuitionDue = 0;
        if(credits > OVERTIME){
            int extraCredits = credits - OVERTIME;
            tuitionDue = FULLTIMERATE + UNIFEE + (PARTTIMEPC * extraCredits);
        }else if(credits >= FULLTIME){
            tuitionDue = FULLTIMERATE + UNIFEE;
        }else if(credits >= PARTTIME){
            tuitionDue = (credits * PARTTIMEPC) + (PERCENT * UNIFEE);
        }
        return tuitionDue + HEALTHINS;
    }

    public String enrollString(){
        String studyAbroad = "";
        if(isStudyAbroad){
            studyAbroad = "study abroad";
        }
        return getProfile() + "(" + "International student" + studyAbroad +")";
    }
}
