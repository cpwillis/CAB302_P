package MazeDesignTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI extends JFrame implements ActionListener {

    public static final int width = 800;
    public static final int height = 700;
    JMenuItem mNew, mOpen, mSave, mExit, mImportImage, mHowTo, mAbout;
    JFrame masterWindow = new JFrame("Maze Design Tool (Group_282)");
    JFrame slaveWindow = new JFrame("replace me"); // Sits Inside masterWindow

    private JFrame initFrame() {
        masterWindow.setLayout(new BorderLayout());
        masterWindow.setPreferredSize(new Dimension(width, height));

        masterWindow.getContentPane().add(menu(),BorderLayout.NORTH);

        masterWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        masterWindow.pack();
        masterWindow.setLocationRelativeTo(null);
        masterWindow.setVisible(true);

        return masterWindow;
    }

    private JMenuBar menu() {
        // Create MenuBar
        JMenuBar menuBar = new JMenuBar();

        // Create Options
        mNew = new JMenuItem("New");
        mNew.addActionListener(this);
        mOpen = new JMenuItem("Open");
        mOpen.addActionListener(this);
        mSave = new JMenuItem("Save");
        mSave.addActionListener(this);
        mExit = new JMenuItem("Exit");
        mExit.addActionListener(this);
        mImportImage = new JMenuItem("Import Image");
        mImportImage.addActionListener(this);
        mHowTo = new JMenuItem("How To?");
        mHowTo.addActionListener(this);
        mAbout = new JMenuItem("About");
        mAbout.addActionListener(this);

        // Create Heading and Add Respective Options
        JMenu mFile = new JMenu("File");
        mFile.add(mNew);
        mFile.add(mOpen);
        mFile.add(mSave);
        mFile.add(mExit);
        JMenu mEdit = new JMenu("Edit");
        mEdit.add(mImportImage);
        JMenu mHelp = new JMenu("Help");
        mHelp.add(mHowTo);
        mHelp.add(mAbout);

        // Attach Headings to MenuBar
        menuBar.add(mFile);
        menuBar.add(mEdit);
        menuBar.add(mHelp);

        return menuBar;
    }

    private void fileDirectory() {
        // https://www.codejava.net/java-se/swing/show-simple-open-file-dialog-using-jfilechooser
        JFileChooser fileDirectory = new JFileChooser();
        fileDirectory.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileDirectory.showOpenDialog(this);
    }

    public void createMaze() {
        // todo hardcoded maze
        // new maze dialogue
        // create slaveWindow for maze
        // draw maze
    }

    @Override public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mNew) { createMaze(); }
        if (e.getSource() == mOpen) { fileDirectory(); }
        if (e.getSource() == mSave) { fileDirectory(); }
        if (e.getSource() == mExit) { System.exit(0); }
        if (e.getSource() == mImportImage) {  }
        if (e.getSource() == mHowTo) {
            // what it does/how
        }
        if (e.getSource() == mAbout) {
            // project name/course/year, team members
        }
    }

    public void run() {
        initFrame();
    }
}
