package VCS.Server;

import Abstractions.ICommand;
import Abstractions.CommandPacket;
import ThreadDispatcher.ThreadedTask;
import Utils.ClientUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProcessClient extends ThreadedTask {
    private final Socket socket;
    private final FileManager fileManager;

    ProcessClient(Socket socket, FileManager fileManager) {
        this.socket = socket;
        this.fileManager = fileManager;
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {
            while (!socket.isClosed()) {
                CommandPacket packet = ClientUtils.getRequest(in);
                ICommand command = packet.command;
                CommandPacket response = command.execute(fileManager);
                ClientUtils.SendPacket(response, out);
                fileManager.provider.log().Success(response.message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
