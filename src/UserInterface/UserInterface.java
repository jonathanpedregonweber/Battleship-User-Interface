package UserInterface;

import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame
{
    private JButton[][] EnemyButtons = new JButton[10][10];

    public UserInterface()
    {
        JPanel mainPanel = new JPanel(new GridLayout(2,2));
        setName("Battleship User Interface");
        setSize(1500,900);
        setContentPane(mainPanel);
        setVisible(true);
    }
}
