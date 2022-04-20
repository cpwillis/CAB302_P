import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Start extends BorderPage{

    private JPanel panels[];

    public Start() {
        super("Border Layout Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        Color backgroundColours[] = {Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY};
        int backgroundBoundaries[] = {150, 50, 400, 100, 0};


        panels = BorderPanel.setBorderPanel(backgroundColours, backgroundBoundaries);



        add(panels[0], BorderLayout.NORTH);
        add(panels[1], BorderLayout.SOUTH);
        add(panels[2], BorderLayout.WEST);
        add(panels[3], BorderLayout.EAST);
        add(panels[4], BorderLayout.CENTER);

        panels[2].setLayout(new GridLayout(10, 4));

        for (int i = 0; i < 40; i++){

            if (i == 21){
                JButton browseB = new JButton("Browse");
                browseB.addActionListener(this::browseBClicked);
                panels[2].add(browseB);
            }
            else if (i == 29){
                JButton mazeInfoB = new JButton("Create Maze");
                panels[2].add(mazeInfoB);
            }
            else{
                JPanel emptyPanel = new JPanel();
                emptyPanel.setBackground(Color.LIGHT_GRAY);
                panels[2].add(emptyPanel);
            }
        }




        // Display the window.
        super.setWindow();
    }


    public void browseBClicked(ActionEvent actionEvent){
        dispose();
        new MazePage();
    }








    public static void main(String[] args){
        new Start();


    }

}
