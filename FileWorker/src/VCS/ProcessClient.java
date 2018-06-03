package VCS;

import Abstractions.ICommand;
import Abstractions.ICommandPacket;
import ThreadDispatcher.ThreadedTask;
import Utils.ClientUtils;

import java.net.Socket;

public class ProcessClient extends ThreadedTask {
    private final Socket socket;


    ProcessClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            ICommandPacket packet = ClientUtils.getRequest(socket);
            ICommand command = CommandFactory.createInstance(packet);
            ICommandPacket response = command.execute();
            ClientUtils.sendResponse(response, socket);
        }
    }
}
