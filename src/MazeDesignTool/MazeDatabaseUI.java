package MazeDesignTool;


import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Initiates user interface for the AddressBook application. All listeners for
 * the application are included as inner classes of this class.
 *
 * @author Malcolm Corney
 * @version $Id: Exp $
 *
 */
public class MazeDatabaseUI extends JFrame {
   private JList nameList;

   private JTextField mazeAuthor;

   private JTextField mazeName;

   private JTextField mazeDateAndTime;

   private JTextField mazeLastEditDateAndTime;

   private JTextField mazeIsSolvable;

   private JButton loadMaze;

   private JButton saveMaze;

   private JButton deleteMaze;

   DB data;


   /**
    * Constructor sets up user interface, adds listeners and displays.
    *
    */
   public MazeDatabaseUI() {
      data = new DB();


      initUI();
      checkListSize();

      // add listeners to interactive components
      addButtonListeners(new ButtonListener());
      addNameListListener(new NameListListener());
      addClosingListener(new ClosingListener());

      // decorate the frame and make it visible
      setTitle("Maze Database");
      setMinimumSize(new Dimension(400, 300));
      pack();
      setVisible(true);

   }

   /**
    * Places the detail panel and the button panel in a box layout with vertical
    * alignment and puts a 20 pixel gap between the components and the top and
    * bottom edges of the frame.
    */
   private void initUI() {
      Container contentPane = this.getContentPane();
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

      contentPane.add(Box.createVerticalStrut(20));
      contentPane.add(makeDetailsPanel());
      contentPane.add(Box.createVerticalStrut(20));
      contentPane.add(makeButtonsPanel());
      contentPane.add(Box.createVerticalStrut(20));
   }

   public void run2() {
      initUI();
   }
   /**
    * Makes a JPanel consisiting of (1) the list of names and (2) the address
    * fields in a box layout with horizontal alignment and puts a 20 pixel gap
    * between the components and the left and right edges of the panel.
    *
    * @return the detail panel.
    */
   private JPanel makeDetailsPanel() {
      JPanel detailsPanel = new JPanel();
      detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.X_AXIS));
      detailsPanel.add(Box.createHorizontalStrut(20));
      detailsPanel.add(makeNameListPane());
      detailsPanel.add(Box.createHorizontalStrut(20));
      detailsPanel.add(createChooseMazeDisplay());
      detailsPanel.add(Box.createHorizontalStrut(20));
      return detailsPanel;
   }

   /**
    * Makes a JScrollPane that holds a JList for the list of names in the
    * address book.
    *
    * @return the scrolling name list panel
    */
   private JScrollPane makeNameListPane() {
      DefaultListModel listModel = new DefaultListModel();
      for (String name : data.nameSet()) {
         listModel.addElement(name);
      }
      nameList = new JList(listModel );

      nameList.setFixedCellWidth(200);

      JScrollPane scroller = new JScrollPane(nameList);
      scroller.setViewportView(nameList);
      scroller
              .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scroller
              .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scroller.setMinimumSize(new Dimension(200, 150));
      scroller.setPreferredSize(new Dimension(250, 150));
      scroller.setMaximumSize(new Dimension(250, 200));

      return scroller;
   }


   private JPanel createChooseMazeDisplay() {
      JPanel savedMazes = new JPanel();
      GroupLayout layout = new GroupLayout(savedMazes);
      savedMazes.setLayout(layout);

      // Turn on automatically adding gaps between components
      layout.setAutoCreateGaps(true);

      // Turn on automatically creating gaps between components that touch
      // the edge of the container and the container.
      layout.setAutoCreateContainerGaps(true);

      JLabel mazeAuthorLabel = new JLabel("Maze Author:");
      JLabel mazeNameLabel = new JLabel("Maze Name:");
      JLabel mazeDateAndTimeLabel = new JLabel("Created On:");
      JLabel mazeLastEditDateAndTimeLabel = new JLabel("Last Edited:");
      JLabel mazeIsSolvableLabel = new JLabel("Maze is Solvable:");

      mazeAuthor = new JTextField(20);
      mazeAuthor.setBorder(BorderFactory.createEmptyBorder());

      mazeName = new JTextField(20);
      mazeName.setBorder(BorderFactory.createEmptyBorder());

      mazeDateAndTime = new JTextField(20);
      mazeDateAndTime.setBorder(BorderFactory.createEmptyBorder());

      mazeLastEditDateAndTime = new JTextField(20);
      mazeLastEditDateAndTime.setBorder(BorderFactory.createEmptyBorder());
      mazeIsSolvable = new JTextField(20);
      mazeIsSolvable.setBorder(BorderFactory.createEmptyBorder());
      mazeAuthor.setEditable(false);
      mazeName.setEditable(false);
      mazeDateAndTime.setEditable(false);
      mazeLastEditDateAndTime.setEditable(false);
      mazeIsSolvable.setEditable(false);

      // Create a sequential group for the horizontal axis.
      GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

      // The sequential group in turn contains two parallel groups.
      // One parallel group contains the labels, the other the text fields.
      hGroup.addGroup(layout.createParallelGroup().addComponent(mazeAuthorLabel)
              .addComponent(mazeNameLabel).addComponent(mazeDateAndTimeLabel).addComponent(
                      mazeLastEditDateAndTimeLabel).addComponent(mazeIsSolvableLabel));
      hGroup.addGroup(layout.createParallelGroup().addComponent(mazeAuthor)
              .addComponent(mazeName).addComponent(mazeDateAndTime).addComponent(mazeLastEditDateAndTime)
              .addComponent(mazeIsSolvable));
      layout.setHorizontalGroup(hGroup);

      // Create a sequential group for the vertical axis.
      GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

      // The sequential group contains five parallel groups that align
      // the contents along the baseline. The first parallel group contains
      // the first label and text field, and the second parallel group contains
      // the second label and text field etc. By using a sequential group
      // the labels and text fields are positioned vertically after one another.
      vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
              .addComponent(mazeAuthorLabel).addComponent(mazeAuthor));

      vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
              .addComponent(mazeNameLabel).addComponent(mazeName));
      vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
              .addComponent(mazeDateAndTimeLabel).addComponent(mazeDateAndTime));
      vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
              .addComponent(mazeLastEditDateAndTimeLabel).addComponent(mazeLastEditDateAndTime));
      vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
              .addComponent(mazeIsSolvableLabel).addComponent(mazeIsSolvable));
      layout.setVerticalGroup(vGroup);

      return savedMazes;
   }

   /**
    * Adds the New, Save and Delete buttons to a panel
    */
   private JPanel makeButtonsPanel() {
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
      loadMaze = new JButton("Load");
      saveMaze = new JButton("Save");
      //saveMaze.setEnabled(false);
      deleteMaze = new JButton("Delete");
      buttonPanel.add(Box.createHorizontalStrut(50));
      buttonPanel.add(loadMaze);
      buttonPanel.add(Box.createHorizontalStrut(50));
      buttonPanel.add(saveMaze);
      buttonPanel.add(Box.createHorizontalStrut(50));
      buttonPanel.add(deleteMaze);
      buttonPanel.add(Box.createHorizontalStrut(50));
      return buttonPanel;
   }

   /**
    * Adds a listener to the new, save and delete buttons 
    */
   private void addButtonListeners(ActionListener listener) {
      loadMaze.addActionListener(listener);
      saveMaze.addActionListener(listener);
      deleteMaze.addActionListener(listener);
   }

   /**
    * Adds a listener to the name list 
    */
   private void addNameListListener(ListSelectionListener listener) {
      nameList.addListSelectionListener(listener);
   }

   /**
    * Adds a listener to the JFrame 
    */
   private void addClosingListener(WindowListener listener) {
      addWindowListener(listener);
   }

   /**
    * Sets the text in the address text fields to the empty string. 
    */
   private void clearFields() {
      mazeAuthor.setText("");
      mazeName.setText("");
      mazeDateAndTime.setText("");
      mazeLastEditDateAndTime.setText("");
      mazeIsSolvable.setText("");
   }


   private void displayMaze(String[] string) {
      if (string != null) {
         mazeAuthor.setText(string[1]);
         mazeName.setText(string[0]);
         mazeDateAndTime.setText(string[2]);
         mazeLastEditDateAndTime.setText(string[3]);
         mazeIsSolvable.setText("Yes");
      }
   }

   private Maze loadPressed() throws SQLException {
      ArrayList<String> arr = data.getArrayMaze();
      int height = Integer.parseInt(arr.get(0));
      int width = Integer.parseInt(arr.get(1));
      int[][] newArr = new int[height][width];
      for (int i = 0; i != height; ++i) {
         for (int j = 0; j != width; ++j) {
            newArr[i][j] = Integer.parseInt(arr.get(i * width + j + 2));
            System.out.println(newArr[i][j]);
         }
      }
      String[] mazeInfo = data.getMaze((String) nameList.getSelectedValue());
      Maze maze = new Maze(width, height, mazeInfo[0], mazeInfo[1]);

      return maze;
   }
   /**
    * Checks size of data/model and enables/disables the delete button
    *
    */
   private void checkListSize() {
      //If there are any mazes in the list then set deleteMaze.setEnabled(true)
   }

   private class ButtonListener implements ActionListener {

      /**
       * @see ActionListener#actionPerformed(ActionEvent)
       */
      public void actionPerformed(ActionEvent e) {
         JButton source = (JButton) e.getSource();
         if (source == loadMaze) {
            try {
               loadPressed();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         } else if (source == saveMaze) {
            //savePressed();
         } else if (source == deleteMaze) {
            deletePressed();
         }
      }

      private void newPressed() {
         //clearFields();
         //saveMaze.setEnabled(true);
         //if(mazeAuthor.getText != null)
      }

      private void deletePressed() {
         int index = nameList.getSelectedIndex();
         data.remove(nameList.getSelectedValue());
         clearFields();
         index--;
         if (index == -1) {
            if (data.getSize() != 0) {
               index = 0;
            }
         }
         nameList.setSelectedIndex(index);
         checkListSize();
         //remove the maze at index
      }
   }
//   private void savePressed(Maze m) {
//     data.addMaze(m);
//   }
   /**
    * Implements a ListSelectionListener for making the UI respond when a
    * different name is selected from the list.
    */
   private class NameListListener implements ListSelectionListener {

      public void valueChanged(ListSelectionEvent e) {
         if (nameList.getSelectedValue() != null
                 && !nameList.getSelectedValue().equals("")) {
            displayMaze(data.getMaze((String) nameList.getSelectedValue()));
         }
      }
   }

   /**
    * Implements the windowClosing method from WindowAdapter/WindowListener to
    * persist the contents of the data/model.
    */
   private class ClosingListener extends WindowAdapter {

      /**
       * @see WindowAdapter#windowClosing(WindowEvent)
       */
      public void windowClosing(WindowEvent e) {
         //data.persist();
      }
   }
}
