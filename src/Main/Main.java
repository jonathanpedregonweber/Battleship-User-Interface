package Main;

import Main.Handlers.ServerHandler;
import Main.Models.*;
import Models.Coordinates;
import UserInterface.UserInterface;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main
{
    private static String ServerName = "ec2-18-207-150-67.compute-1.amazonaws.com";
    private static int Port = 8989;
    private ServerHandler ServerHandler;
    private UserInterface UI;
    private int HitCount = 0;

    public static void main(String[] args)
    {
        Main program = new Main();
        program.StartProgram();
    }

    private void StartProgram()
    {
        try (Socket socket = new Socket(ServerName, Port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            ServerHandler = new ServerHandler(socket);
            ServerHandler.SendLoginMessage(GetUserName());
            UI = new UserInterface(socket);

            String serverInput = reader.readLine();
            System.out.println(serverInput);
            try
            {
                ProcessServerMessage(serverInput);
            }
            catch (Exception e)
            {
                System.out.println("Unable to process input from server. " + e.getMessage());
            }

        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void ProcessServerMessage(String serverInput)
    {
        Message serverMessage = MessageFactory.parse(serverInput);

        switch (serverMessage.type)
        {
            case "Chat":
                HandleChatMessage(serverMessage);
                break;
            case "Start":
                HandleStartMessage(serverMessage);
                break;
            case "Hit":
                HandleHitMessage(serverMessage);
                break;
            case "Move":
                HandleMoveMessage(serverMessage);
                break;
            case "Win":
                HandleWinMessage();
                break;
            default:
                System.out.println(serverInput);
                break;
        }
    }

    private void HandleWinMessage()
    {
        UI.ShowMessage("You won!");
    }

    private void HandleChatMessage(Message serverMessage)
    {
        ChatMessage chat = (ChatMessage)  serverMessage;
        UI.AppendToTextArea(chat.chatMessage);
    }

    private void HandleStartMessage(Message serverMessage)
    {
        UI.EnableOpponentButtons();
    }

    private void HandleHitMessage(Message serverMessage)
    {
        HitMessage hitMessage = (HitMessage) serverMessage;
        UI.ShowHit(hitMessage.hit);
    }

    private void HandleMoveMessage(Message serverMessage)
    {
        MoveMessage moveMessage = (MoveMessage) serverMessage;
        Coordinates moveCoordinates = new Coordinates(moveMessage.xCoordinate, moveMessage.yCoordinate);
        Boolean isHit = IsHit(moveCoordinates);
        UI.DisplayOpponentMove(moveCoordinates, isHit);
        ServerHandler.SendHitMessage(isHit);
        if(isHit)
        {
            HitCount++;
            if(IsWin())
            {
                MessageFactory.getWinMessage();
                ServerHandler.WinMessage();
                UI.ShowMessage("You lost!");
            }
        }

    }

    private static String GetUserName()
    {
        return JOptionPane.showInputDialog(new JFrame(), "Enter your name: ");
    }

    private boolean IsHit(Coordinates moveCoordinates)
    {
        for(Coordinates coordinates : UI.ShipCoordinates)
        {
            if(coordinates.Equals(moveCoordinates))
            {
                return true;
            }
        }
        return false;
    }

    private boolean IsWin()
    {
        return HitCount == 17;
    }
}
