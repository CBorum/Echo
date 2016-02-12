package echoserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

/**
 *
 * @author ChristopherBorum
 */
public class ClientHandler extends Thread {

    Socket socket;
    Scanner input;
    PrintWriter writer;
    
    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            input = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        String message = input.nextLine(); //IMPORTANT blocking call
        System.out.println(String.format("Received the message: %1$S ", message));
        while (!message.equals(ProtocolStrings.STOP)) {
            writer.println(message.toUpperCase());
            System.out.println(String.format("Received the message: %1$S ", message.toUpperCase()));
            message = input.nextLine(); //IMPORTANT blocking call
        }
        writer.println(ProtocolStrings.STOP);//Echo the stop message back to the client for a nice closedown
        try {
            socket.close();
            System.out.println("Closed a Connection");
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
