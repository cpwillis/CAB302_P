package MazeDesignTool;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Maze {

    private int id;
    private int width;
    private int height;
    private String title;
    private String author;
    private Date created;
    private Date modified;
    public String window; public String getWindow() { return window; }
    public int [][] gridArray;

    public Maze(int width, int height, String title, String author) {
        this.id = 1;
        this.width = width;
        this.height = height;
        this.title = title;
        this.author = author;
        if (this.created == null) { this.created = new Date(); }
        this.modified = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM HH:mm"); //https://stackabuse.com/how-to-get-current-date-and-time-in-java/
        this.window = MessageFormat.format("{0} | {1} by {2} (Created: {3}, Last Modified: {4})",
                String.format("%03d", id), title, author, dt.format(created), dt.format(modified));
        this.gridArray = new int[][]{ // Hardcoded for Testing (0=Path, 1=Wall, 2=Start, 3=Finish)
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
    }

    // todo: object for creating 2d array

}
