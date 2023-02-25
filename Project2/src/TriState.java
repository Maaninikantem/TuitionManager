public class TriState extends NonResident {

    private String state;

    public TriState(Profile profile, Major major, int creditCompleted, String state) {

        super(profile, major, creditCompleted);
        this.state = state;
    }

    public double tuitionDue(int credits){
        double FULLTIMERATE = 29737;
        double PARTTIMEPC = 966;
        double UNIFEE = 3268;
        double PERCENT = 0.8;
        int OVERTIME = 16;
        int FULLTIME = 12;
        int PARTTIME = 3;
        double discount = 0;

        if(state.equals("NY")){
            discount = 4000;
        } else if(state.equals("CT")){
            discount = 5000;
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
        return tuitionDue - discount;
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
        return getProfile() + " (" + getMajor().getMajorCode()+ " " + getMajor().getMajorName() + " " + getMajor().getSchoolName() + ") " + "credits completed: " + getCreditCompleted() + " (" + standing + ") (tri-state: " + state + ")";
    }
    public String enrollString(){
        return getProfile() + "(" + "Tristate" + state +")";
    }
}