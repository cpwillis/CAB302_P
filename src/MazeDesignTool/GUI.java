package MazeDesignTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GUI extends JFrame implements ActionListener {

    public static final int width = 800;
    public static final int height = 700;
    JMenuItem mNew, mOpen, mSave, mExit, mImport, mHowTo, mAbout;
    JFrame masterWindow = new JFrame("Maze Design Tool (Group_282)");

    private void initFrame() {
        masterWindow.setLayout(new BorderLayout());
        masterWindow.setPreferredSize(new Dimension(width, height));
        masterWindow.getContentPane().add(menu(),BorderLayout.NORTH);
        masterWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        masterWindow.pack();
        masterWindow.setLocationRelativeTo(null);
        masterWindow.setVisible(true);
    }

    private JMenuBar menu() {
        // Create MenuBar
        JMenuBar menuBar = new JMenuBar();

        // Create Options
        mNew = new JMenuItem("New"); mNew.addActionListener(this);
        mOpen = new JMenuItem("Open"); mOpen.addActionListener(this);
        mSave = new JMenuItem("Save"); mSave.addActionListener(this);
        mExit = new JMenuItem("Exit"); mExit.addActionListener(this);
        mImport = new JMenuItem("Import Image"); mImport.addActionListener(this);
        mHowTo = new JMenuItem("How To?"); mHowTo.addActionListener(this);
        mAbout = new JMenuItem("About"); mAbout.addActionListener(this);

        // Create Heading and Add Respective Options
        JMenu mFile = new JMenu("File");
        mFile.add(mNew); mFile.add(mOpen); mFile.add(mSave); mFile.add(mExit);
        JMenu mEdit = new JMenu("Edit");
        mEdit.add(mImport);
        JMenu mHelp = new JMenu("Help");
        mHelp.add(mHowTo); mHelp.add(mAbout);

        // Attach Headings to MenuBar
        menuBar.add(mFile); menuBar.add(mEdit); menuBar.add(mHelp);

        // Return
        return menuBar;
    }

    private void fileDirectory() {
        // https://www.codejava.net/java-se/swing/show-simple-open-file-dialog-using-jfilechooser
        JFileChooser fileDirectory = new JFileChooser();
        fileDirectory.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileDirectory.showOpenDialog(this);
    }

    private void createMaze() {
        JDialog MazeDialogue = new JDialog();
        MazeDialogue.setLayout(new GridBagLayout());
        MazeDialogue.setSize(200,200);
        MazeDialogue.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        MazeDialogue.setLocationRelativeTo(null);

        String[] MazeProperties = {"Title", "Width", "Height", "Author"};
        int i = 0;
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5; c.fill = GridBagConstraints.HORIZONTAL;
        JTextField[] mazeInputs = new JTextField[4];
        for (String x: MazeProperties) {
            JLabel abc = new JLabel(x+":");
            c.gridx = 0; c.gridy = i;
            MazeDialogue.add(abc, c);

            mazeInputs[i] = new JTextField();
            c.gridwidth = 2; c.ipadx = 60; c.gridx = 1; c.gridy = i;
            MazeDialogue.add(mazeInputs[i], c);
            i++;
        }
        JButton submit = new JButton("Submit");
        c.weightx = 0.0; c.gridwidth = 3; c.gridx = 0; c.gridy = i;
        MazeDialogue.add(submit, c);
        submit.addActionListener(e -> {
            //todo: use values from dialogue to generate maze
            System.out.println(mazeInputs[0].getText());
            int mazeWidth = Integer.parseInt(mazeInputs[1].getText());
            int mazeHeight = Integer.parseInt(mazeInputs[2].getText());
            Maze maze = new Maze(mazeWidth,mazeHeight,mazeInputs[0].getText(), mazeInputs[3].getText());
            viewMaze(maze);
            MazeDialogue.dispose();
        });
        MazeDialogue.setVisible(true);

    }

    public void viewMaze(Maze currentMaze) {
        JInternalFrame slaveWindow = new JInternalFrame(currentMaze.getWindowName(), true, true); // Sits Inside masterWindow
        slaveWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        slaveWindow.setLayout(new BorderLayout());
        slaveWindow.setVisible(true);
        slaveWindow.add(new DrawMaze(currentMaze, true,slaveWindow));
        masterWindow.getContentPane().add(slaveWindow);
        masterWindow.revalidate();


    }

    @Override public void actionPerformed(ActionEvent e) {
        //todo: menubar actions
        if (e.getSource() == mNew) { createMaze(); }
        if (e.getSource() == mOpen) { fileDirectory(); }
        if (e.getSource() == mSave) { fileDirectory(); }
        if (e.getSource() == mExit) { System.exit(0); }
        if (e.getSource() == mImport) { } // start and finish images
        if (e.getSource() == mHowTo) { } // what it does/how
        if (e.getSource() == mAbout) { } // project name/course/year, team members

    }


    public void run() {
        initFrame();
    }
}
