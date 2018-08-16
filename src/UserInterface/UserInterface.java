package UserInterface;

import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame
{
    private JButton[][] OpponentButtons = new JButton[10][10];
    private JPanel OpponentPanel;

    public UserInterface()
    {
        JPanel mainPanel = new JPanel(new GridLayout(2,2));
        GetOpponentPanel();
        mainPanel.add(OpponentPanel);
        setName("Battleship User Interface");
        setSize(1500,900);
        setContentPane(mainPanel);
        setVisible(true);
    }

    private void GetOpponentPanel()
    {
        OpponentPanel = new JPanel(new GridLayout(10,10));
        OpponentButtons = CreateButtons();
    }

    private JButton[][] CreateButtons()
    {
        JButton[][] buttonArray = new JButton[10][10];
        for(int row = 0; row < 10; row++)
        {
            for(int column = 0; column < 10; column++)
            {
                JButton opponentButton = new JButton();
                opponentButton.setText("A");
                OpponentPanel.add(opponentButton);
                buttonArray[row][column] = new JButton();
            }
        }
        return buttonArray;
    }

    private void AddOpponentButtonsToPanel(JPanel panel)
    {

    }
}
