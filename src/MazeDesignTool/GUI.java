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
    private final int [][] defaultMaze = { // Hardcoded for Testing (0=Path, 1=Wall, 2=Start, 3=Finish)
            {1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,2,1,0,1,0,1,0,0,0,0,0,1},
            {1,0,1,0,0,0,1,0,1,1,1,0,1},
            {1,0,0,0,1,1,1,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,1,1,1,0,1},
            {1,0,1,0,1,1,1,0,1,0,0,0,1},
            {1,0,1,0,1,0,0,0,1,1,1,0,1},
            {1,0,1,0,1,1,1,0,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,1,3,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1}};

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
        // todo: maze creation dialogue for user to pick options (e.g. size)
        Maze tst = new Maze(5,5,"Tester", "Chris");
        viewMaze(tst);
    }

    private void viewMaze(Maze currentMaze) {
        JInternalFrame slaveWindow = new JInternalFrame(currentMaze.getWindowName()); // Sits Inside masterWindow
        slaveWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        slaveWindow.setLayout(new BorderLayout());
        slaveWindow.add(new JLabel("If you see me, maze didn't paint.")); //TESTER
        slaveWindow.setClosable(true);
        slaveWindow.setVisible(true);

        masterWindow.getContentPane().add(slaveWindow, BorderLayout.CENTER);
        masterWindow.revalidate();


        // todo: draw maze

    }

    @Override public void paint(Graphics g) {
        super.paint(g);
        for (int row = 0; row < defaultMaze.length; row++) {
            for (int col = 0; col < defaultMaze[0].length; col++) {
                Color color = switch (defaultMaze[row][col]) {
                    case 1 -> Color.BLACK;
                    case 2 -> Color.BLUE;
                    case 3 -> Color.RED;
                    default -> Color.WHITE;
                };
                g.setColor(color);
                g.fillRect(30 * col, 30 * row, 30, 30);
                g.setColor(Color.BLACK);
                g.drawRect(30 * col, 30 * row, 30, 30);
            }
        }
    }

    @Override public void actionPerformed(ActionEvent e) {
        //todo: menubar actions
        if (e.getSource() == mNew) { createMaze(); }
        if (e.getSource() == mOpen) { fileDirectory(); }
        if (e.getSource() == mSave) { fileDirectory(); }
        if (e.getSource() == mExit) { System.exit(0); }
        if (e.getSource() == mImportImage) { } // start and finish images
        if (e.getSource() == mHowTo) { } // what it does/how
        if (e.getSource() == mAbout) { } // project name/course/year, team members
    }

    public void run() {
        initFrame();
        createMaze();
    }
}
