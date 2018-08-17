package Main;

import Main.Handlers.ServerHandler;
import Main.Models.*;
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

            }
            if(serverMessage.type.equals("Chat"))
            {

            }
            System.out.println(serverInput);

        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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

    private static String GetUserName()
    {
        return JOptionPane.showInputDialog(new JFrame(), "Enter your name: ");
    }
}
