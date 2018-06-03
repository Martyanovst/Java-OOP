package VCS;

import ThreadDispatcher.ThreadDispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static Utils.ClientUtils.getAddress;
import static Utils.Constants.HOST;
import static Utils.Constants.PORT;

public class Server {
    private static ThreadDispatcher dispatcher = ThreadDispatcher.getInstance();
    private static final int clientCount = 10;


    public static void run() {
        try {
            ServerSocket server = new ServerSocket(PORT, clientCount, getAddress(HOST));
            while (true){
                Socket socket = server.accept();
                dispatcher.Add(new ProcessClient(socket));
            }
        }
        catch (IOException e) {
            System.out.println("Socket is not available");
        }
    }
}
