import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;

public abstract class BorderPage extends JFrame{

    private static final int pos_x = 700;
    private static final int pos_y = 200;
    private static final int width = 600;
    private static final int height = 500;
    /**
     * calls JFrame and changes top left window name
     * @param s String of window name
     */
    public BorderPage(String s){
        super(s);
    }


    /**
     * Sets a fixed Size and Location of window and shows the window.
     */
    public void setWindow(){
        setPreferredSize(new Dimension(width, height));
        setLocation(new Point(pos_x, pos_y));
        pack();
        setVisible(true);
    }


    /**
     * Organise smallPanels into BorderLayout, which is within Window
     * @param smallPanels Array of panels used to fill BorderLayout, with strictly 5 elements
     */
    public void borderPanelPos(JPanel smallPanels[]){

        add(smallPanels[0], BorderLayout.NORTH);
        add(smallPanels[1], BorderLayout.SOUTH);
        add(smallPanels[2], BorderLayout.WEST);
        add(smallPanels[3], BorderLayout.EAST);
        add(smallPanels[4], BorderLayout.CENTER);

    }





}
