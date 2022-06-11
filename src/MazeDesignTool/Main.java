package MazeDesignTool;

/**
 * Class used to run the application.
 */
public class Main {

    /**
     * Method initialises the GUI.
     */
    public static void main(String[] args) {
        try {
            GUI init = new GUI();
            init.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
