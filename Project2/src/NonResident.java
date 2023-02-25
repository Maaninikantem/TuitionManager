public class NonResident extends Student{


    public NonResident(Profile profile, Major major, int creditCompleted) {
        super(profile, major, creditCompleted);
    }

    public double tuitionDue(int credits){
        double FULLTIMERATE = 29737;
        double PARTTIMEPC = 966;
        double UNIFEE = 3268;
        double PERCENT = 0.8;
        int OVERTIME = 16;
        int FULLTIME = 12;
        int PARTTIME = 3;

        double tuitionDue = 0;
        if(credits > OVERTIME){
            int extraCredits = credits - OVERTIME;
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
}
