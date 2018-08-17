package UserInterface;

import Main.Handlers.ServerHandler;
import Models.Coordinates;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.Socket;

public class UserInterface extends JFrame
{
    private JButton[][] OpponentButtons = new JButton[10][10];
    private JPanel OpponentPanel;
    private JButton[][] UserButtons = new JButton[10][10];
    private JPanel UserPanel;
    private TextArea ChatReceived;
    private TextArea ChatSend;
    private JButton SendChatButton;
    private JButton StartGameButton;
    private JButton RandomizeShipsButton;
    private ServerHandler ServerHandler;
    public Coordinates[] ShipCoordinates;
    private Coordinates[] PlayerGuessCoordinates;

    public UserInterface(Socket socket)
    {
        ServerHandler = new ServerHandler(socket);
        JPanel boardPanel = new JPanel(new GridLayout(1,2));
        JPanel lowerPanel = new JPanel(new GridLayout(1,2));
        JPanel mainPanel = new JPanel(new GridLayout(2,1));
        GetOpponentPanel();
        GetUserPanel();
        boardPanel.add(OpponentPanel);
        boardPanel.add(UserPanel);
        lowerPanel.add(GetChatPanel());
        lowerPanel.add(GetButtonPanel());
        mainPanel.add(boardPanel);
        mainPanel.add(lowerPanel);


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
                buttonArray[row][column] = new JButton();
                JButton currentButton = buttonArray[row][column];
                currentButton.setText(GetButtonText(column, row));
                currentButton.setEnabled(false);
                currentButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
                UserPanel.add(currentButton);
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
                opponentButton.setEnabled(false);
                opponentButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
                opponentButton.setActionCommand(column + "_" + row);
                opponentButton.addActionListener(e -> {
                    Coordinates coordinates = new Coordinates(e.getActionCommand());
                    JButton selectedButton = OpponentButtons[coordinates.YCoordinate][coordinates.XCoordinate];
                    selectedButton.setBackground(Color.YELLOW);
                    DisableOpponentButtons();
                    ServerHandler.SendMoveMessage(coordinates.XCoordinate, coordinates.YCoordinate);
                });
                buttonArray[row][column] = opponentButton;
                OpponentPanel.add(opponentButton);
            }
        }
        PlayerGuessCoordinates = new Coordinates[100];
        return buttonArray;
    }

    private String GetButtonText(int columnIndex, int rowIndex)
    {
        String[] letters = {"A","B","C","D","E","F","G","H","I","J"};
        return letters[columnIndex] + (rowIndex + 1);
    }

    private JPanel GetChatPanel()
    {
        JPanel chatPanel = new JPanel(new GridLayout(3,1));
        ChatReceived = new TextArea();
        ChatReceived.setEnabled(false);
        ChatSend = new TextArea();
        SendChatButton = new JButton("Send Chat");
        SendChatButton.addActionListener(e ->
        {
            String chatMessage = ChatSend.getText();
            AppendToTextArea(chatMessage);
            ServerHandler.SendChatMessage(chatMessage);
            ChatSend.setText("");
        });
        chatPanel.add(ChatReceived);
        chatPanel.add(ChatSend);
        chatPanel.add(SendChatButton);
        chatPanel.setBorder(new LineBorder(Color.BLUE, 7));
        return chatPanel;
    }

    private JPanel GetButtonPanel()
    {
        JPanel buttonPanel = new JPanel(new GridLayout(6,1));
        StartGameButton = new JButton("Start Game");
        StartGameButton.setEnabled(false);
        StartGameButton.addActionListener(e ->
        {
            ServerHandler.SendStartMessage();
            StartGameButton.setEnabled(false);
            RandomizeShipsButton.setEnabled(false);
            EnableOpponentButtons();//normally I wouldn't enable these until I receive a start message but I'm
            //not getting one right now.
        });
        RandomizeShipsButton = new JButton("Randomize Ships");
        RandomizeShipsButton.addActionListener(e ->
        {
            SetShips();
            StartGameButton.setEnabled(true);
        });
        buttonPanel.add(StartGameButton);
        buttonPanel.add(RandomizeShipsButton);
        buttonPanel.setBorder(new LineBorder(Color.BLUE, 7));
        return buttonPanel;
    }

    private void SetShips()
    {
        //Eventually this would be actually randomized.
        ShipCoordinates = new Coordinates[17];
        //Carrier
        ShipCoordinates[0] = new Coordinates(0,0);

        ShipCoordinates[1] = new Coordinates(0,1);
        ShipCoordinates[2] = new Coordinates(0,2);
        ShipCoordinates[3] = new Coordinates(0,3);
        ShipCoordinates[4] = new Coordinates(0,4);

        //4 Ship
        ShipCoordinates[5] = new Coordinates(2,3);
        ShipCoordinates[6] = new Coordinates(3,3);
        ShipCoordinates[7] = new Coordinates(4,3);
        ShipCoordinates[8] = new Coordinates(5,3);

        //First 3 Ship
        ShipCoordinates[9] = new Coordinates(7,9);
        ShipCoordinates[10] = new Coordinates(8,9);
        ShipCoordinates[11] = new Coordinates(9,9);

        //Second 3 Ship
        ShipCoordinates[12] = new Coordinates(9,3);
        ShipCoordinates[13] = new Coordinates(9,4);
        ShipCoordinates[14] = new Coordinates(9,5);

        //2 Ship
        ShipCoordinates[15] = new Coordinates(7,3);
        ShipCoordinates[16] = new Coordinates(7,4);

        for(Coordinates coordinates: ShipCoordinates)
        {
            HighlightUserButton(coordinates, Color.green);
        }
    }

    public void AppendToTextArea(String text)
    {
        String currentText = ChatReceived.getText();
        ChatReceived.setText(currentText + text);
    }

    public void HighlightUserButton(Coordinates buttonCoordinates, Color buttonColor)
    {
        int xCoordinate = buttonCoordinates.XCoordinate;
        int yCoordinate = buttonCoordinates.YCoordinate;

        UserButtons[yCoordinate][xCoordinate].setBackground(buttonColor);
    }

    public void EnableOpponentButtons()
    {
        for(int column = 0; column < 10; column++)
        {
            for(int row = 0; row < 10; row++)
            {
                Color buttonColor = OpponentButtons[row][column].getBackground();
                if(!buttonColor.equals(Color.RED) && !buttonColor.equals(Color.BLUE))
                {
                    OpponentButtons[row][column].setEnabled(true);
                }
            }
        }
    }

    private void DisableOpponentButtons()
    {
        for(int column = 0; column < 10; column++)
        {
            for(int row = 0; row < 10; row++)
            {
                OpponentButtons[row][column].setEnabled(false);
            }
        }
    }

    public void ShowHit(boolean hit)
    {
        for(int column = 0; column < 10; column++)
        {
            for(int row = 0; row < 10; row++)
            {
                Color buttonColor = OpponentButtons[row][column].getBackground();
                if(buttonColor.equals(Color.YELLOW))
                {
                    Color newButtonColor;
                    if(hit)
                    {
                        newButtonColor = Color.RED;
                    }
                    else
                    {
                        newButtonColor = Color.BLUE;
                    }
                    OpponentButtons[row][column].setBackground(newButtonColor);
                }
            }
        }
    }

    public void DisplayOpponentMove(Coordinates moveCoordinates, boolean hit)
    {
        Color newButtonColor;
        if(hit)
        {
            newButtonColor = Color.RED;
        }
        else
        {
            newButtonColor = Color.BLUE;
        }
        UserButtons[moveCoordinates.YCoordinate][moveCoordinates.XCoordinate].setBackground(newButtonColor);
    }

    public void ShowMessage(String message)
    {
        JOptionPane.showMessageDialog(null, message);
    }
}
