package Main;

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

        System.out.println(GetUserName());

        try (Socket socket = new Socket(serverName, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {

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
