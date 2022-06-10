package MazeDesignTool;


import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class Maze extends JFrame {

    private int id;
    private int width;
    private int height;
    private String title;
    private String author;
    private Date created;
    private Date modified;
    public String windowName;

    public String getWindowName() {
        return windowName;
    }

    public int[][] gridArray;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Maze(int width, int height, String title, String author) {
        this.id = 1;
        this.width = width;
        this.height = height;
        this.title = title;
        this.author = author;
        this.gridArray = new int[height][width];
        if (this.created == null) {
            this.created = new Date();
        }
        this.modified = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM HH:mm"); //https://stackabuse.com/how-to-get-current-date-and-time-in-java/
        this.windowName = MessageFormat.format("{0} | {1} by {2} (Created: {3}, Last Modified: {4})",
                String.format("%03d", id), title, author, dt.format(created), dt.format(modified));
        // this.gridArray = abc;
    }


    // todo: object for creating 2d array
    private ArrayList<Point> getRandomNeighbours(Point node, int width, int height) {
        ArrayList<Point> neighbours = new ArrayList<>(4);
        if (node.x != height - 1) {
            neighbours.add(new Point(node.x + 1, node.y)); // Down one
        }
        if (node.x != 0) {
            neighbours.add(new Point(node.x - 1, node.y)); // Up one
        }
        if (node.y != width - 1) {
            neighbours.add(new Point(node.x, node.y + 1)); // Right one
        }
        if (node.y != 0) {
            neighbours.add(new Point(node.x, node.y - 1)); // Left one
        }
        Collections.shuffle(neighbours);
        return neighbours;
    }

    public List<Point> generateSolution(Point startPoint, Point desiredPoint) {
        Queue<List<Point>> nodes = new LinkedList<>();
        boolean[] visited = new boolean[width * height];
        Arrays.fill(visited, false);
        List<Point> path = new ArrayList<>();
        path.add(startPoint);
        nodes.offer(path);
        visited[startPoint.x * width + startPoint.y] = true;
        while (!nodes.isEmpty()) {
            path = nodes.poll();
            if (path.get(path.size() - 1).equals(desiredPoint)) {
                return path;
            }
            for (Point n : getRandomNeighbours(path.get(path.size() - 1), width, height)) {
                if (!visited[n.x * width + n.y] && gridArray[n.x][n.y] != 1) {
                    List<Point> newPath = new ArrayList<>(path);
                    newPath.add(n);
                    nodes.offer(newPath);
                    visited[n.x * width + n.y] = true;
                }
            }
        }
        return null;
    }

    public void generateRandomMaze(boolean startEndImages, ArrayList<Point> logos) {
        int widthThin = width / 2;
        int heightThin = height / 2;
        if (width % 2 == 0) {
            --widthThin;
        }
        if (height % 2 == 0) {
            --heightThin;
        }
        //Create grid
        for (int row = 0; row != height; ++row) {
            for (int column = 0; column != width; ++column) {
                if (row == 0 || row == height - 1 || column == 0 || column == width - 1) {
                    gridArray[row][column] = 1;
                }
                if (row % 2 == 0 || column % 2 == 0) {
                    gridArray[row][column] = 1;
                }
            }
        }
        Stack<Point> nodes = new Stack<>();
        boolean[] visited = new boolean[widthThin * heightThin];
        Arrays.fill(visited, false);
        nodes.push(new Point(0, 0));
        visited[0] = true;
        if(logos!=null){
            int totalRowLength=0;
            int totalColumnLength=0;
            for(Point logo : logos) {
                totalRowLength+=(int) Math.ceil((double) logo.x / 2);
                totalColumnLength+=(int) Math.ceil((double) logo.y / 2);
            }
            int columnSpace =1;
            if(totalRowLength<=((heightThin-3*(logos.size()-1)))) { //Loosely packed
                for (Point logo : logos) {
                    int logoHeight = (int) Math.ceil((double) logo.x / 2);
                    int logoWidth = (int) Math.ceil((double) logo.y / 2);
                    int rowSpace = (int) Math.floor(Math.random() * (widthThin - logoWidth -2) + 2);
                    for (int i = columnSpace; i != logoHeight + columnSpace; ++i) {
                        for (int j = rowSpace; j != logoWidth + rowSpace; ++j) {
                            visited[i * widthThin + j] = true;
                        }
                    }
                    columnSpace += 3;
                }
            }
            else{ //Tightly packed
                int rowSpace = 2;
                for (Point logo : logos) {
                    int logoHeight = (int) Math.ceil((double) logo.x / 2);
                    int logoWidth = (int) Math.ceil((double) logo.y / 2);
                    if(rowSpace>=widthThin-3) {
                        columnSpace += 3;
                        rowSpace=1;
                    }
                    for (int i = columnSpace; i != logoHeight + columnSpace; ++i) {
                        for (int j = rowSpace; j != logoWidth + rowSpace; ++j) {
                            visited[i * widthThin + j] = true;
                        }
                    }
                    rowSpace += logoWidth+ 1;
                }
            }
        }

        while (!nodes.empty()) {
            Point currentCell = nodes.pop();
            ArrayList<Point> neighbours = getRandomNeighbours(currentCell, widthThin, heightThin);
            for (Point chosenCell : neighbours) {
                if (!visited[chosenCell.x * widthThin + chosenCell.y]) {
                    nodes.push(currentCell);
                    if (currentCell.x == chosenCell.x && currentCell.y == (chosenCell.y + 1)) { //Right
                        gridArray[2 * chosenCell.x + 1][2 * (chosenCell.y + 1)] = 0;
                    } else if (currentCell.x == (chosenCell.x + 1) && currentCell.y == chosenCell.y) { //Down
                        gridArray[2 * (chosenCell.x + 1)][2 * chosenCell.y + 1] = 0;
                    } else if (currentCell.x == (chosenCell.x - 1) && currentCell.y == chosenCell.y) { //Up
                        gridArray[2 * chosenCell.x][2 * chosenCell.y + 1] = 0;
                    } else if (currentCell.x == (chosenCell.x) && currentCell.y == (chosenCell.y - 1)) { //Left

                        gridArray[2 * chosenCell.x + 1][2 * chosenCell.y] = 0;
                    }
                    visited[chosenCell.x * widthThin + chosenCell.y] = true; //Mark chosen cell as visited
                    nodes.push(chosenCell);
                    break;
                }
            }
            }
        if (height % 2 == 0) {
            for (int i = 0; i != width; ++i) {
                gridArray[height - 2][i] = gridArray[height - 3][i];
                gridArray[height - 3][i] = gridArray[height - 4][i];
            }
        }
        if (width % 2 == 0) {
            for (int i = 0; i != height; ++i) {
                gridArray[i][width - 2] = gridArray[i][width - 3];
                gridArray[i][width - 3] = gridArray[i][width - 4];
            }
        }
        if(startEndImages){
            for(int i =1; i!= 4; ++i){
                for(int j =1; j!= 4; ++j){
                    gridArray[i][j] = 2;
                }
            }
            for(int i =height-4; i!= height-1; ++i){
                for(int j =width-4; j!= width-1; ++j){
                    gridArray[i][j] = 2;
                }
            }
        }
        if(!startEndImages) {
            gridArray[0][1] = 2; //Add entrance
            gridArray[height - 1][width - 2] = 3; //Add exit
        }
    }

    public double percentDeadEndCells() {
        //Loop over interior cells of maze
        int numDeadEndCells = 0;
        int numCells=0;
        for (int row = 1; row != height - 1; ++row) {
            for (int column = 1; column != width - 1; ++column) {
                if ((gridArray[row + 1][column] == 1 ? 1 : 0) + (gridArray[row - 1][column] == 1 ? 1 : 0) +
                        (gridArray[row][column + 1] == 1 ? 1 : 0) + (gridArray[row][column - 1] == 1 ? 1 : 0) >= 3) {
                    ++numDeadEndCells;
                }
                if(gridArray[row][column]==0){
                    ++numCells;
                }
            }
        }
        return ((double) numDeadEndCells / (numCells))*100;
    }
    public double percentCellsReachedInSolution() {
        List<Point> solution = generateSolution(new Point(0, 1), new Point(height - 1, width - 2));
        int numCells = 0;
        for (int row = 1; row != height - 1; ++row) {
            for (int column = 1; column != width - 1; ++column) {
                if (gridArray[row][column] == 0) {
                    ++numCells;
                }
            }
        }
        return ((double) (solution.size()-2)/numCells)*100;
    }
}

