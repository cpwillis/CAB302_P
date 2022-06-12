package MazeDesignTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

public class DB implements MazeDataSource {
    // todo: Database
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS maze ("
                    + "idx INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE,"
                    + "author VARCHAR(30),"
                    + "name VARCHAR(30),"
                    + "date created VARCHAR(10),"
                    + "date last edited VARCHAR(10);";
    //+ "maze array VARCHAR(30)" + ");";

    private static final String INSERT_MAZE = "INSERT INTO maze (author, name, date created, date last edited,) VALUES (?, ?, ?, ?);";

    private static final String GET_AUTHOR = "SELECT author FROM maze";

    private static final String GET_MAZE = "SELECT * FROM maze WHERE name=?";

    private static final String GET_NAME = "SELECT name FROM maze";

    private static final String DELETE_MAZE = "DELETE FROM maze WHERE name=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM address";

    private Connection connection;

    private PreparedStatement addMaze;

    private PreparedStatement getNameList;

    private PreparedStatement getMaze;

    private PreparedStatement deleteMaze;

    private PreparedStatement rowCount;

    public DB(){
        connection = DBConnection.getInstance();
        try{
            Statement ct = connection.createStatement();
            ct.execute(CREATE_TABLE);

            //addMaze = connection.preparestatement(INSERT_MAZE);


        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        //Connection connection = DriverManager.getConnection

        //thinking of making it a table of maze name + the actual array. both of these can be searched through the database
        // table needs to contain
        //showing the author, maze name, date and time the maze was first created and the date and time the maze was last edited for each maze. will also need to include the array of the maze
        //
        //
    }

    public Set<String> nameSet() {
        Set<String> names = new TreeSet<String>();
        ResultSet rs = null;

        /* BEGIN MISSING CODE */
        try {
            rs = getNameList.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return names;
    }
    public void addMaze(Maze m){
        //try{

        //}catch(SQLException ex){ex.printStackTrace();}
    }
    public Maze getMaze(String name){
        return null;
    }


    public int getSize() {
        return 0;
    }
    public void deleteMaze(String name){

    }
    public void close(){
        try{
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
