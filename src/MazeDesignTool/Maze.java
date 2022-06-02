package MazeDesignTool;

import javax.swing.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Maze extends JFrame {

    private int id;
    private int width;
    private int height;
    private String title;
    private String author;
    private Date created;
    private Date modified;
    public String windowName; public String getWindowName() { return windowName; }
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
        this.windowName = MessageFormat.format("{0} | {1} by {2} (Created: {3}, Last Modified: {4})",
                String.format("%03d", id), title, author, dt.format(created), dt.format(modified));
        // this.gridArray = abc;
    }

    // todo: object for creating 2d array

    public void generateRandomMaze(){
        //Modify gridArray
    }

    public ArrayList<String> mazeSolution(){
        //return a ArrayList of instructions as strings to get from beginning to end, if there is no solution return
        //null
        return null;
    }
}

