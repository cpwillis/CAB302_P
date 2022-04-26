package MazeDesignTool;

import javax.swing.*;
import java.awt.*;


public class BorderPanel {

    /**
     * Organises smallpanels into BorderLayout, which is within largePanel
     *
     * @param largePanel  A panel that stores all smallPanels
     * @param smallPanels Array of smaller panels used to fill the BorderLayout, with strictly 5 elements
     * @return One panel with BorderLayout
     */
    public static JPanel borderPanelPos(JPanel largePanel, JPanel[] smallPanels) {

        largePanel.add(smallPanels[0], BorderLayout.NORTH);
        largePanel.add(smallPanels[1], BorderLayout.SOUTH);
        largePanel.add(smallPanels[2], BorderLayout.WEST);
        largePanel.add(smallPanels[3], BorderLayout.EAST);
        largePanel.add(smallPanels[4], BorderLayout.CENTER);

        return largePanel;
    }


    /**
     * Sets the Colour and border panel distance in order of NORTH, SOUTH, WEST, EAST
     *
     * @param c Array of border panel colours
     * @param b Array of border panel distance
     * @return Set of Border Panels Array
     */
    public static JPanel[] setBorderPanel(Color[] c, int[] b) {

        JPanel[] panels = new JPanel[5];

        for (int i = 0; i < panels.length; i++) {

            JPanel panel = new JPanel();
            panel.setBackground(c[i]);

            if (i < 2) {
                panel.setPreferredSize(new Dimension(0, b[i]));
            } else if (i < 4) {
                panel.setPreferredSize(new Dimension(b[i], 0));
            }

            panels[i] = panel;

        }
        return panels;
    }

}
