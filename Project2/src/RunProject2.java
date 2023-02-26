import java.io.FileNotFoundException;

/**
 This is the main class which initiates the code to start running
 @author Michael Mankiewicz
 */
public class RunProject2 {
    public static void main(String[] args) throws FileNotFoundException {
        TuitionManager tuitionManager = new TuitionManager();
        tuitionManager.run();
    }
}
