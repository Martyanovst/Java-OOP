package WebServer;

import ThreadDispatcher.ThreadDispatcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;

import static Utils.SocketUtils.*;

public class Server {
    public static final int PORT = 10000;
    public static final String HOST  = "localhost";
    private static final int clientCount = 10;
    public static final Charset CHARSET = Charset.forName("UTF-8");


    public static void main(String[] args) {
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance();
        try {
            ServerSocket server = new ServerSocket(PORT, clientCount, getAddress(HOST));
            while (true){
                Socket socket = server.accept();
                dispatcher.Add(new ProcessClientRequest(socket));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("This directory doesn't exists");
        }
        catch (IOException e) {
            System.out.println("Socket is not available");
        }
    }
}
