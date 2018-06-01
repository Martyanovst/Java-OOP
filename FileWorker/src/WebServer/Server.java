package WebServer;

import ThreadDispatcher.ThreadDispatcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;

public class Server {
    private static final int port = 10000;
    private static final String host  = "localhost";
    private static final int clientCount = 1;
    private static InetAddress getAddress()
    {
        InetAddress addr = null;
        try {
            addr = Inet4Address.getByName(host);
        } catch (UnknownHostException ignored){}
        return addr;
    }

    public static void main(String[] args) {
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance();
        try {
            ServerSocket server = new ServerSocket(port, clientCount, getAddress());
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
