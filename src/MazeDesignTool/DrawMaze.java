package MazeDesignTool;

import javax.swing.*;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawMaze extends Component implements ActionListener{
    Maze maze;
    final JPopupMenu menu = new JPopupMenu("Menu");
    final JMenuItem  genSol = new JMenuItem ("Generate solution");
    JInternalFrame window;

    class PopupActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           if(e.getActionCommand().equals("Generate solution")){

           }
        }
    }

    public DrawMaze(Maze maze, boolean generateRandomMaze, JInternalFrame window){
        menu.add(genSol);
        window.add(menu);
        ActionListener actionListener = new PopupActionListener();
        genSol.addActionListener(actionListener);
        this.maze = maze;
        this.window = window;
        try {
            maze.generateRandomMaze(false, null);
        }
        catch (Exception e){}


        window.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()== MouseEvent.BUTTON1) {
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
        int[][] gridArray =maze.gridArray;
        super.paint(g);
        for (int row = 0; row < gridArray.length; row++) {
            for (int col = 0; col < gridArray[0].length; col++) {
                Color color = switch (gridArray[row][col]) {
                    case 1 -> Color.BLACK;
                    case 2 -> Color.PINK;
                    case 3 -> Color.GREEN;
                    default -> Color.WHITE;
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

