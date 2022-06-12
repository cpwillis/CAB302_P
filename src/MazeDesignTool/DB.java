package MazeDesignTool;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static org.mariadb.jdbc.client.DataType.JSON;


public class DB implements MazeDataSource {
    // todo: Database
    DefaultListModel listModel;
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS maze ("
                    + "idx INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE,"
                    + "name VARCHAR(60),"
                    + "author VARCHAR(30),"
                    + "created VARCHAR(20),"
                    + "modified VARCHAR(20)," +
                    "mazearray VARCHAR(10000)" + ");";

    private static final String INSERT_MAZE = "INSERT INTO maze (name, author, created, modified, mazearray) VALUES (?, ?, ?, ?, ?);";

    private static final String GET_AUTHOR = "SELECT author FROM maze";

    private static final String GET_MAZE = "SELECT * FROM maze WHERE name=?";

    private static final String GET_NAMES = "SELECT name FROM maze";

    private static final String DELETE_MAZE = "DELETE FROM maze WHERE name=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM maze";

    //private static final String INSERT_ARRAY = "INSERT INTO maze";

    private Connection connection;

    private PreparedStatement addMaze;

    private PreparedStatement insertMaze;

    private PreparedStatement getAuthor;

    private PreparedStatement getNameList;

    private PreparedStatement getMaze;

    private PreparedStatement deleteMaze;

    private PreparedStatement rowCount;

    public DB(){
        connection = DBConnection.getInstance();
        try{
            Statement ct = connection.createStatement();
            ct.execute(CREATE_TABLE);
            addMaze = connection.prepareStatement(INSERT_MAZE);
            getNameList = connection.prepareStatement(GET_NAMES);
            getAuthor = connection.prepareStatement(GET_AUTHOR);
            deleteMaze = connection.prepareStatement(DELETE_MAZE);
            getMaze = connection.prepareStatement(GET_MAZE);
            rowCount = connection.prepareStatement(COUNT_ROWS);

        }catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public Set<String> nameSet() {
        Set<String> names = new TreeSet<String>();
        ResultSet rs = null;


        try {
            rs = getNameList.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        return names;
    }


    public void addMaze(Maze m) throws IOException {
        //PreparedStatement statement = connection.prepareStatement("INSERT INTO maze VALUES( mazearray = ?)");

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add((Integer.toString(m.getHeight())));
        arrayList.add((Integer.toString(m.getWidth())));
        int H = m.getHeight();
        int W = m.getWidth();


        int[][] arr = m.gridArray;
        //int[] newarr = new int[H*W+2];

        //newarr[0] = H;
        //newarr[1] = W;
        for(int i=0; i!=H; ++i) {
            for(int j=0; j!=W; ++j) {

                arrayList.add(Integer.toString(arr[i][j]));
                //newarr[i*W+j+2] = arr[i][j];
            }
        }

//        for(var a : arrayList){
//            System.out.println(a);
//        }
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);

        objectStream.writeObject(arrayList);
        byte[] gabagool = byteStream.toByteArray();
//        addMaze.setBinaryStream(5, new ByteArrayInputStream(gabagool),
//                gabagool.length);
        //insertMaze.setString(2, name);
        //addMaze.execute();

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos;
//        try {
//            oos = new ObjectOutputStream(baos);
//            oos.writeObject(baos);
//        }
//        catch (Exception e){}
//        byte[] byteArray = baos.toByteArray();

//        String[][] newArr = new String[arrayList.get(0)][arrayList.get(1)];
//        for(int i=0; i!=arrayList.get(0); ++i) {
//            for(int j=0; j!=arrayList.get(1); ++j) {
//                newArr[i][j] = arrayList.get(i*arrayList.get(1)+j + 2 );
//            }
//        }
        try{
            System.out.println(m.getName());
            addMaze.setString(1, m.getName());

            addMaze.setString(2, m.getAuthor());
            addMaze.setString(3, m.getCreated());
            addMaze.setString(4, m.getModified());
            //addMaze.setString(5, arrayList);
            //insertMaze.execute();
            addMaze.setBinaryStream(5, new ByteArrayInputStream(gabagool),
                    gabagool.length);
            addMaze.execute();
        }catch(SQLException ex){ex.printStackTrace();}
    }



    public String DateToString(Date date){
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM HH:mm");
        String truedate = dt.format(date);
        return truedate;
    }
    public String[] getMaze(String name){
        //Maze m = new Maze();
        String[] Jeff = new String[4];
        ResultSet rs = null;

        try{
            getMaze.setString(1,name);
            rs = getMaze.executeQuery();

            rs.next();

            Jeff[0] = (rs.getString("name"));
            Jeff[1] = (rs.getString("author"));
            Jeff[2] = (rs.getString("created"));
            Jeff[3] = (rs.getString("modified"));

        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return Jeff;
    }


    public int getSize() {
        ResultSet rs = null;
        int rows = 0;


        try {
            rs = rowCount.executeQuery();
            rs.next();
            rows = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        return rows;
    }
    public void deleteMaze(String name){
        try {
            deleteMaze.setString(1, name);
            deleteMaze.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void close(){
        try{
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

