package VCS.Server;

import Abstractions.IDataProvider;
import Abstractions.IVersionGenerator;
import ThreadDispatcher.ThreadDispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static Utils.ClientUtils.getAddress;
import static Utils.Constants.HOST;
import static Utils.Constants.SERVER_PORT;

public class Server {
    private static ThreadDispatcher dispatcher = ThreadDispatcher.getInstance();
    private final int clientCount;
    private final IDataProvider provider;
    private IVersionGenerator versionGenerator;

    public Server(IDataProvider provider, int clientCount, IVersionGenerator versionGenerator) {
        this.provider = provider;
        this.clientCount = clientCount;
        this.versionGenerator = versionGenerator;
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(SERVER_PORT, clientCount, getAddress(HOST));
            while (true) {
                Socket socket = server.accept();
                ClientInfo client = new ClientInfo(socket.getInetAddress());
                FileManager manager = new FileManager(client, provider, versionGenerator);
                dispatcher.Add(new ProcessClient(socket, manager));
            }
        } catch (IOException e) {
            System.out.println("Socket is not available");
        }
    }
}
