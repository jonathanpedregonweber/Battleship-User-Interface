package Main;

import Main.Handlers.ServerHandler;
import Main.Models.ChatMessage;
import Main.Models.Message;
import Main.Models.MessageFactory;
import UserInterface.UserInterface;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main
{

    public static void main(String[] args)
    {
        String serverName = "ec2-18-207-150-67.compute-1.amazonaws.com";
        int port = 8989;
        ServerHandler ServerHandler;
        UserInterface UI;

        try (Socket socket = new Socket(serverName, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            ServerHandler = new ServerHandler(socket);
            ServerHandler.SendLoginMessage(GetUserName());
            UI = new UserInterface(socket);


            String serverInput = reader.readLine();
            Message serverMessage = MessageFactory.parse(serverInput);

            if(serverMessage.type.equals("Chat"))
            {
                ChatMessage chat = (ChatMessage)  serverMessage;
                UI.AppendToTextArea(chat.chatMessage);
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

    public static String GetUserName()
    {
        return JOptionPane.showInputDialog(new JFrame(), "Enter your name: ");
    }
}
