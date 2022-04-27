package MazeDesignTool;

import javax.swing.*;
import java.awt.*;

public class DrawMaze extends Component {

    public final int [][] gridArray = { // Hardcoded for Testing (0=Path, 1=Wall, 2=Start, 3=Finish)
            {1,2,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,1,0,1,0,1,0,0,0,0,0,1},
            {1,0,1,0,0,0,1,0,1,1,1,0,1},
            {1,0,0,0,1,1,1,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,1,1,1,0,1},
            {1,0,1,0,1,1,1,0,1,0,0,0,1},
            {1,0,1,0,1,0,0,0,1,1,1,0,1},
            {1,0,1,0,1,1,1,0,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,1,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,2,1}};

    public void paint(Graphics g) {
        super.paint(g);
        for (int row = 0; row < gridArray.length; row++) {
            for (int col = 0; col < gridArray[0].length; col++) {
                Color color = switch (gridArray[row][col]) {
                    case 1 -> Color.BLACK;
                    case 2 -> Color.PINK;
                    default -> Color.WHITE;
                };
                g.setColor(color);
                g.fillRect(30 * col, 30 * row, 30, 30);
                g.setColor(Color.BLACK);
                g.drawRect(30 * col, 30 * row, 30, 30);
            }
        }
    }
}
