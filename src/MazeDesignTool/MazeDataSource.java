package MazeDesignTool;

import java.util.Set;

public interface MazeDataSource {
    void addMaze(Maze m);
    Maze getMaze(String name);
    int getSize();
    void deleteMaze(String Name);
    void close();
    Set<String> nameSet();
}
