package MazeDesignTool;

import javax.swing.*;
import java.awt.*;


public class MazePage extends BorderPage {
    private static final int gridRow = 3;
    private static final int gridCol = 3;
    private static final int gridSize = gridRow * gridCol;
    private final JPanel[] gridPanels;
    private final Color[] backgroundColours = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.WHITE};
    private final int[] backgroundBoundaries = {100, 100, 100, 100, 0};
    private final JPanel[] backgroundPanels;
    Color[] gridColours = {Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE};
    int[] gridBoundaries = {2, 2, 2, 2, 0};
    private JPanel[] panelsGrid = new JPanel[5];


    public MazePage() {


        super("Maze");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        backgroundPanels = BorderPanel.setBorderPanel(backgroundColours, backgroundBoundaries);

        super.borderPanelPos(backgroundPanels);

        backgroundPanels[4].setLayout(new GridLayout(gridRow, gridCol));


        gridPanels = new JPanel[gridSize];

        for (int i = 0; i < gridSize; i++) {
            gridPanels[i] = createTable(i);
            backgroundPanels[4].add(gridPanels[i]);
        }


        // Display the window.
        super.setWindow();
    }

    public static void main(String[] args) {
        new MazePage();


    }

    private JPanel createTable(int i) {
        JPanel panelGrid = new JPanel(new BorderLayout());


        panelsGrid = BorderPanel.setBorderPanel(gridColours, gridBoundaries);
        panelGrid = BorderPanel.borderPanelPos(panelGrid, panelsGrid);

        contentGridPanel(panelsGrid[4]);

        return panelGrid;
    }

    public void contentGridPanel(JPanel panel) {
        panel.add(new JLabel("Label 1"));

    }

}
