package echoserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

public class EchoServer {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private String ip;
    private int port;

    public static void stopServer() {
        keepRunning = false;
    }

    private void runServer(String ip, int port) {
        this.port = port;
        this.ip = ip;

        System.out.println("Sever started. Listening on: " + port + ", bound to: " + ip);
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            do {
                Socket socket = serverSocket.accept(); //Important Blocking call
                System.out.println("Connected to a client");
                new ClientHandler(socket).start();
            } while (keepRunning);
        } catch (IOException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            Log.setLogFile("LogFile.txt", "ServerLog");
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Starting the Server");
            String ip = args[0];
            int port = Integer.parseInt(args[1]);
            new EchoServer().runServer(ip, port);
            
        } finally {
            Log.closeLogger();
        }

    }
}
