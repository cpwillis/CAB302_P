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
    private ArrayList<Point> get_random_neighbours(Point node, int width, int height) {
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

    public List<Point> generate_solution(Point start_point, Point desired_point) {
        Queue<List<Point>> nodes = new LinkedList<>();
        boolean[] visited = new boolean[width * height];
        Arrays.fill(visited, false);
        List<Point> path = new ArrayList<>();
        path.add(start_point);
        nodes.offer(path);
        visited[start_point.x * width + start_point.y] = true;
        while (!nodes.isEmpty()) {
            path = nodes.poll();
            if (path.get(path.size() - 1).equals(desired_point)) {
                return path;
            }
            for (Point n : get_random_neighbours(path.get(path.size() - 1), width, height)) {
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


    public void generateRandomMaze() {
        int width_thin = width / 2;
        int height_thin = height / 2;
        if (width % 2 == 0) {
            --width_thin;
        }
        if (height % 2 == 0) {
            --height_thin;
        }
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
        gridArray[0][1] = 2; //Add entrance
        Point prevPoint = new Point();
        boolean[] visited = new boolean[width_thin * height_thin];
        Arrays.fill(visited, false);
        Stack<Point> nodes = new Stack<>();
        nodes.push(new Point(0, 0));
        visited[0] = true;
        boolean hasNeighbour;
        ArrayList<Point> disconnected = new ArrayList<>();
        while (!nodes.empty()) {
            hasNeighbour = false;
            Point node = nodes.pop();
            if (!visited[node.x * width_thin + node.y]) {
                if (node.x == prevPoint.x && node.y == (prevPoint.y + 1)) {
                    gridArray[2 * prevPoint.x + 1][2 * (prevPoint.y + 1)] = 0;
                } else if (node.x == (prevPoint.x + 1) && node.y == prevPoint.y) {
                    gridArray[2 * (prevPoint.x + 1)][2 * prevPoint.y + 1] = 0;
                } else if (node.x == (prevPoint.x - 1) && node.y == prevPoint.y) {
                    gridArray[2 * prevPoint.x][2 * prevPoint.y + 1] = 0;
                } else if (node.x == (prevPoint.x) && node.y == (prevPoint.y - 1)) {

                    gridArray[2 * prevPoint.x + 1][2 * prevPoint.y] = 0;
                }
            }
            ArrayList<Point> neighbours = get_random_neighbours(node, width_thin, height_thin);
            for (Point p : neighbours) {
                if (!visited[p.x * width_thin + p.y]) {
                    nodes.push(p);
                    hasNeighbour = true;
                }
            }
            if (!hasNeighbour) {
                disconnected.add((node));
            }

            visited[node.x * width_thin + node.y] = true;
            prevPoint = node;
        }
        Collections.shuffle(disconnected);
        for (Point p : disconnected) {
            if (generate_solution(new Point(2 * p.x + 1, 2 * p.y + 1), new Point(0, 1)) == null) {
                if (gridArray[2 * p.x][2 * p.y + 1] == 1 && p.x != 0) { //Up
                    gridArray[2 * p.x][2 * p.y + 1] = 0;
                } else if (gridArray[2 * p.x + 2][2 * p.y + 1] == 1 && p.x != height_thin - 1) { //down
                    gridArray[2 * p.x + 2][2 * p.y + 1] = 0;
                } else if (gridArray[2 * p.x + 1][2 * p.y] == 1 && p.y != 0) { //left
                    gridArray[2 * p.x + 1][2 * p.y] = 0;
                } else if (gridArray[2 * p.x + 1][2 * p.y + 2] == 1 && p.y != width_thin - 1) { //right
                    gridArray[2 * p.x + 1][2 * p.y + 2] = 0;
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
        gridArray[height - 1][width - 2] = 3; //Add exit

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
        List<Point> solution = generate_solution(new Point(1, 0), new Point(height - 1, width - 2));
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

