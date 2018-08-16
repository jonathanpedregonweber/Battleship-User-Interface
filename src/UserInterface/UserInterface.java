package UserInterface;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class UserInterface extends JFrame
{
    private JButton[][] OpponentButtons = new JButton[10][10];
    private JPanel OpponentPanel;
    private JButton[][] UserButtons = new JButton[10][10];
    private JPanel UserPanel;

    public UserInterface()
    {
        JPanel boardPanel = new JPanel(new GridLayout(1,2));
        JPanel mainPanel = new JPanel(new GridLayout(2,1));
        GetOpponentPanel();
        GetUserPanel();
        boardPanel.add(OpponentPanel);
        boardPanel.add(UserPanel);
        mainPanel.add(boardPanel);

        setName("Battleship User Interface");
        setSize(1500,900);
        setContentPane(mainPanel);
        setVisible(true);
    }

    private void GetUserPanel()
    {
        UserPanel = new JPanel(new GridLayout(10,10));
        UserPanel.setBorder(new LineBorder(Color.BLUE, 7));
        UserButtons = CreateUserButtons();
    }

    private JButton[][] CreateUserButtons()
    {
        JButton[][] buttonArray = new JButton[10][10];
        for(int row = 0; row < 10; row++)
        {
            for(int column = 0; column < 10; column++)
            {
                JButton opponentButton = new JButton();
                opponentButton.setText(GetButtonText(column, row));
                UserPanel.add(opponentButton);
                buttonArray[row][column] = new JButton();
            }
        }
        return buttonArray;
    }

    private void GetOpponentPanel()
    {
        OpponentPanel = new JPanel(new GridLayout(10,10));
        OpponentPanel.setBorder(new LineBorder(Color.BLUE, 7));
        OpponentButtons = CreateOpponentButtons();
    }

    private JButton[][] CreateOpponentButtons()
    {
        JButton[][] buttonArray = new JButton[10][10];
        for(int row = 0; row < 10; row++)
        {
            for(int column = 0; column < 10; column++)
            {
                JButton opponentButton = new JButton();
                opponentButton.setText(GetButtonText(column, row));
                OpponentPanel.add(opponentButton);
                buttonArray[row][column] = new JButton();
            }
        }
        return buttonArray;
    }

    private String GetButtonText(int columnIndex, int rowIndex)
    {
        String[] letters = {"A","B","C","D","E","F","G","H","I","J"};
        return letters[columnIndex] + (rowIndex + 1);
    }
}
