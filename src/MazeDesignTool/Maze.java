package MazeDesignTool;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Maze extends JFrame {
    int id = 1;
    private final int width; //public int getMazeWidth() {return width;}
    private final int height; //public int getMazeHeight() {return height;}
    private boolean hasEntrance = true;
    private boolean hasExit = true;
    private String title;
    private String author;
    private Date created;
    private Date modified;
    public String windowName; public String getWindowName() {return windowName;}
    public int[][] gridArray;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName(){return title;}

    public String getAuthor(){return author;}

    public String getCreated(){return DateToString(created);}

    public String getModified(){return DateToString(modified);}

    public void setName(String name){this.title = title;}

    public void setAuthor(String author){this.author = author;}

    public void setCreated(){String created = DateToString(this.created);}

    public void setModified(){String modda = DateToString(modified);}

        public String DateToString(Date date){
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM HH mm");
            String truedate = dt.format(date);
            return truedate;
        }
    /**
     * object contains applicable information of each maze (e.g. id, dimensions, date)
     * @param width number of columns
     * @param height number of rows
     * @param title maze title
     * @param author maze author
     */
    public Maze(int width, int height, String title, String author) {

        this.width = width;
        this.height = height;
        this.title = title;
        this.author = author;
        this.gridArray = new int[height][width];
        if (this.created == null) {this.created = new Date();}
        this.modified = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM HH:mm");
        this.windowName = MessageFormat.format("{0} | {1} by {2} (Created: {3}, Last Modified: {4})",
                String.format("%03d", id), title, author, dt.format(created), dt.format(modified));
        // this.gridArray = abc;
        id++;
    }

    /**
     * For a grid with dimensions of a given width and height, given a particular cell within the grid return a shuffled
     * array of all neighbouring cells that are in the grid.
     * @param cell a Point that is within the grid defined by width and height.
     * @param width the number of columns in the grid, where the most left columns is the 0th column.
     * @param height the number of rows in the grid, where the top row is the 0th row.
     * @return a shuffled ArrayList of Points, where each Point is a valid adjacent cell to cell.
     */
    private ArrayList<Point> getRandomNeighbours(Point cell, int width, int height) {
        ArrayList<Point> neighbours = new ArrayList<>(4);
        if (cell.x != height - 1) {
            neighbours.add(new Point(cell.x + 1, cell.y)); // Down one
        }
        if (cell.x != 0) {
            neighbours.add(new Point(cell.x - 1, cell.y)); // Up one
        }
        if (cell.y != width - 1) {
            neighbours.add(new Point(cell.x, cell.y + 1)); // Right one
        }
        if (cell.y != 0) {
            neighbours.add(new Point(cell.x, cell.y - 1)); // Left one
        }
        Collections.shuffle(neighbours);
        return neighbours;
    }

    /**
     * For a maze encoded in gridArray, such that values of 0 indicates path cells, values of 1 indicate wall cells,
     * values of 2 indicate entrance cells and values of 3 indicate exit cells. use breadth-first search to find a path
     * which starts at startPoint and ends at desiredPoint which only traverses path cells and each step is always to an
     * adjacent cell, if such a path exists.
     * @param startPoint Indicates the cell at which the path would start if it exists, this cell must not be a
     *                   wall cell.
     * @param desiredPoint Indicates the cell at which the path would end if it exists, this cell must not be a
     *                     wall cell.
     * @return A List of Points which indicates a valid path from startPoint to desiredPoint, if such a path exists,
     * otherwise, null.
     */
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

    /**
     * Encodes a solvable maze in gridArray such that values of 0 indicates path cells, values of 1 indicate wall cells,
     * values of 2 indicate entrance cells and values of 3 indicate exit cells. In this maze the walls cells are path
     * cells are of the same dimensions and the maze is completely surrounded by wall cells except for a single entrance
     * and exit cells. The maze generation allows for both start and end images indicated by, startEndImages. And logos,
     * whose dimensions are encoded in the ArrayList logos.
     * @param startEndImages Indicates whether the start and end of the maze is an entrance or exit cell in the wall
     *                       surrounding the maze. Or, a 3x3 grid of cells in the top left corner or the bottom right
     *                       corner of the walls surrounding the maze respectively.
     * @param logos A list of dimensions of desired logos that are to placed randomly into the maze, where dimensions
     *              correspond to the size of grid of cells that the logo takes up in the maze. These dimensions must be
     *              odd.
     */
    public void generateRandomMaze(boolean startEndImages, ArrayList<Point> logos) throws  MazeCreationException{
        if(width>100 ||height>100){
            throw new MazeCreationException(String.format("A maze of dimensions %dx%d cells exceeds the maximum maze" +
                    " dimensions of 100x100", width,height));
        }else if(width<5 || height<5 && !startEndImages && logos == null){
            throw new MazeCreationException(String.format("A randomly generated maze of dimensions %dx%d cells is less " +
                    "than the minimum randomly generated maze dimensions of 5x5", width,height));
        }else if(width<9 || height<9 && startEndImages){
            throw new MazeCreationException(String.format("A randomly generated maze of dimensions %dx%d cells cannot " +
                    "contain logos.", width,height));
        }else if(width<11 || height<11 && startEndImages && logos!=null){
            throw new MazeCreationException(String.format("A randomly generated maze of dimensions %dx%d cells cannot " +
                    "contain logos and start and end images.", width,height));
        }
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

        if (startEndImages) {
            visited[widthThin]=true;
            visited[1]=true;
            visited[widthThin+1]=true;
            nodes.pop();
            nodes.push(new Point(0,1));
            gridArray[3][3]=1;
            gridArray[4][1]=0;
            if(height%2!=0 || width%2!=0){
                visited[widthThin*(heightThin-1)-2]=true;
                gridArray[height-4][width-4]=1;
                gridArray[height-5][width-4]=1;
                gridArray[height-4][width-5]=1;
            }
        }
            if (logos != null) {
                try {
                int buffer = 0;
                if (startEndImages) {
                    buffer = 1;
                }
                int totalRowLength = 0;
//                int totalColumnLength = 0;
                for (Point logo : logos) {
                    if (logo.x % 2 != 0 || logo.y % 2 != 0) {
                        throw new MazeCreationException("Logo dimensions must be odd.");
                    }
                    totalRowLength += (int) Math.ceil((double) logo.x / 2);
//                    totalColumnLength += (int) Math.ceil((double) logo.y / 2);
                }
                if (totalRowLength >= (widthThin * heightThin - 3 * logos.size())) {
                    throw new MazeCreationException(String.format("Dimension of logo(s) does not fit into a maze of" +
                            " %dx%d.", width, height));
                }
                int columnSpace = 1;
                if (totalRowLength <= ((heightThin - 3 * (logos.size() - 1)))) { //Loosely packed
                    for (Point logo : logos) {
                        int logoHeight = (int) Math.ceil((double) logo.x / 2);
                        int logoWidth = (int) Math.ceil((double) logo.y / 2);
                        int rowSpace = (int) Math.floor(Math.random() * (widthThin - logoWidth - 1 - buffer) + 1 + buffer);
                        for (int i = columnSpace; i != logoHeight + columnSpace; ++i) {
                            for (int j = rowSpace; j != logoWidth + rowSpace; ++j) {
                                visited[i * widthThin + j] = true;
                            }
                        }
                        columnSpace += 3;
                    }
                } else { //Tightly packed
                    int rowSpace = 2;
                    for (Point logo : logos) {
                        int logoHeight = (int) Math.ceil((double) logo.x / 2);
                        int logoWidth = (int) Math.ceil((double) logo.y / 2);
                        if (rowSpace >= widthThin - 3) {
                            columnSpace += 3;
                            rowSpace = 1;
                        }
                        for (int i = columnSpace; i != logoHeight + columnSpace; ++i) {
                            for (int j = rowSpace; j != logoWidth + rowSpace; ++j) {
                                visited[i * widthThin + j] = true;
                            }
                        }
                        rowSpace += logoWidth + 1;
                    }
                }
            }
                catch (ArrayIndexOutOfBoundsException e){
                    throw new MazeCreationException(String.format("Dimension of logo(s) does not fit into a maze of  %dx%d.",
                            width, height));
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
            if (startEndImages) {
                for (int i = 1; i != 3; ++i) {
                    for (int j = 1; j != 3; ++j) {
                        gridArray[i][j] = 2;
                    }
                }
                for (int i = height - 3; i != height - 1; ++i) {
                    for (int j = width - 3; j != width - 1; ++j) {
                        gridArray[i][j] = 2;
                    }
                }
            }
            if (!startEndImages) {
                gridArray[0][1] = 2; //Add entrance
                gridArray[height - 1][width - 2] = 3; //Add exit
            }
    }

    /**
     * For a maze encoded in gridArray, such that values of 0 indicates path cells, values of 1 indicate wall cells,
     * values of 2 indicate entrance cells and values of 3 indicate exit cells. Find the number of path cells which are
     * adjacent to three wall cells, dead ends. And find the number of path cells in the maze, return the ratio of these
     * numbers respectively as a percentage.
     * @return The ratio of the number of dead end cells, path cells surrounded by three wall cells, and the number of
     * path cells in the encoded in gridArray as a percentage.
     */
    public double percentDeadEndCells() {
        int numDeadEndCells = 0;
        int numCells=0;
        for (int row = 1; row != height - 1; ++row) {
            for (int column = 1; column != width - 1; ++column) {
                if ((gridArray[row + 1][column] == 1 ? 1 : 0) + (gridArray[row - 1][column] == 1 ? 1 : 0) +
                        (gridArray[row][column + 1] == 1 ? 1 : 0) + (gridArray[row][column - 1] == 1 ? 1 : 0) >= 3) {
                        ++numDeadEndCells;
                }
                if ((gridArray[row][column] == 0) || (gridArray[row][column] == 4)) {++numCells;}
            }
        }
        return ((double) numDeadEndCells / (numCells))*100;
    }

    /**
     * For a maze encoded in gridArray, such that values of 0 indicates path cells, values of 1 indicate wall cells,
     * values of 2 indicate entrance cells and values of 3 indicate exit cells. Use generateSolution where the starting
     * cell is the entrance of the maze and the desired cell is the end of the maze to return a list of cells which are
     * a solution to the maze. The size of the list with the path indicates the number of path cells that must be
     * stepped through in a solution. The ratio of the size of the path, excluding the entrance and exit cells, and the
     * total number of path cells in the maze gives the ratio of path cells that must be stepped through in order to
     * reach the end of the maze.
     * @return the ratio of the number of path cells that must be stepped through in a solution, excluding the entrance
     * and exit cells, and the total number of cells in the maze as a percentage.
     */
    public double percentCellsReachedInSolution() {
        List<Point> solution = generateSolution(new Point(0, 1), new Point(height - 1, width - 2));
        int numCells = 0;
        int emptyCells = 0;
        for (int row = 1; row != height - 1; ++row) {
            for (int column = 1; column != width - 1; ++column) {
                if (gridArray[row][column] == 0) { ++emptyCells; }
                if ((gridArray[row][column] == 0) || (gridArray[row][column] == 4)) {++numCells;}
            }
        }
        if (emptyCells == numCells) {return 0;}
        else {return ((double) (solution.size()-2)/numCells)*100;}
    }

    public void drawSolution(){
        Point startPoint = null;
        Point endPoint = null;
        List<Point> path = null;

        for(int i=0;i!=height;++i){
            for(int j=0;j!=width;++j) {
                if(gridArray[i][j]==2){
                    startPoint = new Point(i,j);
                }
                else if(gridArray[i][j]==3){
                    endPoint = new Point(i,j);
                }
            }
        }
        if(startPoint!=null && endPoint!=null){
            path=generateSolution(startPoint,endPoint);
        }
        if(path!=null){
            for(int i =1; i!=path.size()-1;++i){
                    gridArray[path.get(i).x][path.get(i).y] = 4;
            }
        }
    }

    public void hideSolution() {
        for (int row = 1; row != height - 1; ++row) {
            for (int column = 1; column != width - 1; ++column) {
                if (gridArray[row][column] == 4) {
                    gridArray[row][column] = 0;
                }
            }
        }
    }

    public void updateMaze(int x, int y){
        int row = (int)Math.floor((double)y/30)-1;
        int column = (int)Math.floor((double)x/30);
        if(row<=height && column<=width) {
            if (row == 0 || row == height - 1 || column == 0 || column == width - 1) {
                if(gridArray[row][column] == 2){
                    gridArray[row][column] = 1;
                    hasEntrance =false;
                }
                else if(gridArray[row][column] == 3){
                    gridArray[row][column] = 1;
                    hasExit = false;
                }
                else if(gridArray[row][column] == 1 && !hasEntrance){
                    gridArray[row][column]=2;
                    hasEntrance =true;
                }
                else if(gridArray[row][column] == 1 && hasEntrance &&!hasExit){
                    gridArray[row][column] = 3;
                    hasExit =true;
                }
            }
            else {
                if (gridArray[row][column] == 0) {
                    gridArray[row][column] = 1;
                } else if (gridArray[row][column] == 1) {
                    gridArray[row][column] = 0;
                }
            }
        }
    }
}
