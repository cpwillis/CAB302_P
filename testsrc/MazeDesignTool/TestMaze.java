package MazeDesignTool;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


public class TestMaze {
    int[] max_maze_dimensions ={100,100};
    int[] min_maze_dimensions = {5,5};
    Maze[] test_mazes = new Maze[3];
    @BeforeEach
    void setup(){
        //Create a number of dummy Maze objects
        test_mazes[0] = new Maze(max_maze_dimensions[0],max_maze_dimensions[1],"Max Maze","dummy");
        test_mazes[1] =new Maze(min_maze_dimensions[0],min_maze_dimensions[1],"Minimum Maze","dummy");
        test_mazes[2]= new Maze((max_maze_dimensions[0]-max_maze_dimensions[0]/2),
                                 (max_maze_dimensions[1]-max_maze_dimensions[1]/2),"Moderate Maze","dummy");

    }
    @Test
    void testMazeDimensions(){
        assertThrows(Exception.class, ()->{
            new Maze(max_maze_dimensions[0]+1,max_maze_dimensions[1],"dummy","dummy");
        });
        assertThrows(Exception.class, ()->{
            new Maze(max_maze_dimensions[0],max_maze_dimensions[1]+1,"dummy","dummy");
        });
        assertThrows(Exception.class, ()->{
            new Maze(min_maze_dimensions[0]-1,min_maze_dimensions[1],"dummy","dummy");
        });
        assertThrows(Exception.class, ()->{
            new Maze(min_maze_dimensions[0],min_maze_dimensions[1]-1,"dummy","dummy");
        });
    }
    @Test
    void testRandomMazeHasOneEntranceOneExit(){ //According to spec maze must one entrance and exit

        for(Maze maze: test_mazes){
            maze.generateRandomMaze();
        }
        //Test that randomly generated maze has only one entrance and exit

        int number_of_entrances=0;
        int number_of_exits = 0;
        for(Maze maze: test_mazes){
            for(int row=0;row!=maze.getHeight();++row){
                for(int column=0; column!=maze.getWidth();++column){
                    if(maze.gridArray[row][column]==2){
                        ++number_of_entrances;
                    }
                    if(maze.gridArray[row][column]==3){
                        ++number_of_exits;
                    }
                }
            }
            assertEquals(1,number_of_entrances, String.format("More or less than one entrance in randomly" +
                    " generated %dx%d maze.",maze.getWidth(), maze.getHeight()));
            assertEquals(1,number_of_exits, String.format("More or less than one exit in randomly" +
                    " generated %dx%d maze.",maze.getWidth(), maze.getHeight()));
            number_of_entrances=0;
            number_of_exits=0;
        }
    }
    @Test
    void testRandomMazeIsEnclosed(){ //According to spec maze be surrounded by wall except entrance and exit
        //Generate a number of random mazes
        for(Maze maze: test_mazes){
            maze.generateRandomMaze();
        }
        //Test that randomly generated maze has only one entrance and exit
        for(Maze maze: test_mazes){
            for(int row=0;row!=maze.getHeight();++row){
                for(int column=0; column!=maze.getWidth();++column){
                    if((row==0 || column==0 || row== maze.getHeight()-1 || column ==maze.getWidth()-1)){
                        assertNotEquals(0,maze.gridArray[row][column], String.format("Randomly" +
                                " generated %dx%d maze is not enclosed.",maze.getWidth(), maze.getHeight()));
                    }
                }
            }

        }
    }
    @Test
    void testRandomMazeIsSolvable(){
        //Generate a number of random mazes
        for(Maze maze: test_mazes){
            maze.generateRandomMaze();
        }
        //Test that test mazes are solvable
        for(Maze maze: test_mazes) {
            assertNotEquals(maze.generate_solution(new Point(0,1),new Point( maze.getHeight()-1,maze.getWidth()-2)), null,String.format("Randomly generated %dx%d maze was not solvable",
                    maze.getWidth(),maze.getHeight()));
        }
    }
    @Test
    void testDeadEndCell(){
        //For the minimum sized maze, 5x5, there can only be one dead end cell and there must always be 7 cells
        test_mazes[1].generateRandomMaze();
        double deadCells = test_mazes[1].percentDeadEndCells();
        double tol = Math.pow(1,-10);
        assertTrue(Math.abs((double)100/7-deadCells)<tol,"The percent of dead cells for a 5x5 maze" +
                " is incorrect.");
    }
    @Test
    void testPercentCellsReachedInSolution(){
        //For the minimum sized maze, 5x5, the optimal solution visits 5 cells
        test_mazes[1].generateRandomMaze();
        double reachedCells = test_mazes[1].percentCellsReachedInSolution();
        double tol = Math.pow(1,-10);
        assertTrue(Math.abs((double)500/7-reachedCells)<tol,"The percent of visited cells in an optimal" +
                " solution for a 5x5 maze is incorrect.");
    }
}
