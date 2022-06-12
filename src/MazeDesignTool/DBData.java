package MazeDesignTool;


import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class DBData {
    DefaultListModel listModel;

    MazeDataSource mazeData;

    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     *
     */
    public DBData() {
        listModel = new DefaultListModel();

        mazeData = new DB();


        // add the retrieved data to the list model
        for (String name : mazeData.nameSet()) {
            listModel.addElement(name);
        }

    }

    /**
     * Adds a person to the address book.
     *
     * @param m A Person to add to the address book.
     */
    public void add(Maze m) throws SQLException, IOException {

        // check to see if the person is already in the book
        // if not add to the address book and the list model
        if (!listModel.contains(m.getName())) {
            listModel.addElement(m.getName());
            mazeData.addMaze(m);
        }
    }

    /**
     * Based on the name of the person in the address book, delete the person.
     *
     * @param key
     */
    public void remove(Object key) {

        // remove from both list and map
        listModel.removeElement(key);
        mazeData.deleteMaze((String) key);
    }

    /**
     * Saves the data in the address book using a persistence
     * mechanism.
     */
    public void persist() {
        mazeData.close();
    }

    /**
     * Retrieves Person details from the model.
     *
     * @param key the name to retrieve.
     * @return the Person object related to the name.
     */
//    public Maze get(Object key) {
//        return mazeData.getMaze((String) key);
//    }

    /**
     * Accessor for the list model.
     *
     * @return the listModel to display.
     */
    public ListModel getModel() {
        return listModel;
    }

    /**
     * @return the number of names in the Address Book.
     */
    public int getSize() {
        return mazeData.getSize();
    }
}
