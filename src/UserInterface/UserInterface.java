package UserInterface;

import javax.swing.*;

public class UserInterface extends JFrame
{
    private JButton[][] EnemyButtons = new JButton[10][10];

    public UserInterface()
    {
        setName("Battleship User Interface");
        setSize(1920,1080);
    }
}
