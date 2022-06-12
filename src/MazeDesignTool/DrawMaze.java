package MazeDesignTool;

import javax.swing.*;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

public class DrawMaze extends Component implements ActionListener{
    Maze maze;
    final JPopupMenu menu = new JPopupMenu("Menu");
    final JMenuItem  saveMaze= new JMenuItem ("Save Maze");
    final JMenuItem  genSol = new JMenuItem ("Show/Hide Solution");
    final JMenuItem  percentDeadEnds = new JMenuItem ("Maze Information");
    JInternalFrame window;
    boolean showingSolution = false;
    DB data;

    class PopupActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Show/Hide Solution")){viewSolution();}
            else if(e.getActionCommand().equals("Maze Information")){viewMetrics();}
            else if(e.getActionCommand().equals("Save Maze")){
                try {
                    data.addMaze(maze);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public void viewMetrics() {
        double percentDeadEnds = maze.percentDeadEndCells();
        double percentCellsInSolution = maze.percentCellsReachedInSolution();
        String message = String.format("Percentage of cells that are dead ends: %.2f%%\n" +
                                        "Percentage of cells that are reached in a solution: %.2f%%",
                                        percentDeadEnds, percentCellsInSolution);
        JOptionPane.showMessageDialog(null, message, "Maze Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public void viewSolution() {
        if(showingSolution) {showingSolution = false; maze.hideSolution();}
        else{showingSolution = true; maze.drawSolution();}
        window.repaint();
    }

    public DrawMaze(Maze maze, boolean generateRandomMaze, JInternalFrame window){
        data = new DB();
        menu.add(genSol);
        menu.add(percentDeadEnds);
        menu.add(saveMaze);
        window.add(menu);
        ActionListener actionListener = new PopupActionListener();
        genSol.addActionListener(actionListener);
        percentDeadEnds.addActionListener(actionListener);
        saveMaze.addActionListener(actionListener);
        this.maze = maze;
        this.window = window;
        try {maze.generateRandomMaze(false, null);}
        catch (Exception ignored){}

        window.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()== MouseEvent.BUTTON1 && !showingSolution) {
                    maze.updateMaze(e.getX(), e.getY());
                    window.repaint();
                }
                else if(e.getButton()== MouseEvent.BUTTON3){
                    menu.show(window , e.getX(), e.getY());
                }
                super.mouseClicked(e);
            }
        });
    }

    public void paint(Graphics g){
        int[][] gridArray = maze.gridArray;
        super.paint(g);
        for (int row = 0; row < gridArray.length; row++) {
            for (int col = 0; col < gridArray[0].length; col++) {
                Color color = switch (gridArray[row][col]) {
                    case 1 -> Color.BLACK;      // wall
                    case 2 -> Color.PINK;       // start
                    case 3 -> Color.GREEN;      // end
                    case 4 -> Color.LIGHT_GRAY; // solution
                    default -> Color.WHITE;     // empty
                };
                g.setColor(color);
                g.fillRect(30 * col, 30 * row, 30, 30);
                g.setColor(Color.BLACK);
                g.drawRect(30 * col, 30 * row, 30, 30);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.equals(genSol)){
            System.out.println(maze.getWindowName());
        }
    }
}
