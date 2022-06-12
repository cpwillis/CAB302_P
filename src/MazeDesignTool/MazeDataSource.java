package MazeDesignTool;

import java.io.IOException;
import java.util.Set;

public interface MazeDataSource {
    void addMaze(Maze m) throws IOException;
 //   Maze getMaze(String name);
    int getSize();
    void deleteMaze(String Name);
    void close();
    Set<String> nameSet();
}
