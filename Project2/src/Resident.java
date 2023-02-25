public class Resident extends Student{

    private int scholarship;

    public Resident(Profile profile, Major major, int creditCompleted) {
        super(profile, major, creditCompleted);
        scholarship = 0;
    }

    public double tuitionDue(int credits){
        double FULLTIMERATE = 12536;
        double PARTTIMEPC = 404;
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
        return tuitionDue - scholarship;
    }

    public boolean isResident() {
        return true;
    }

    public String enrollString(){
        return getProfile() + "(" + "Resident" + ")";
    }

}
