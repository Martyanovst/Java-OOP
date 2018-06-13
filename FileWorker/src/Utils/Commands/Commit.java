package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import ThreadDispatcher.ThreadDispatcher;
import Utils.ClientUtils;
import FileWorker.CreateNewVersion;
import VCS.Server.FileManager;
import VCS.Server.ServerResponse;

import java.io.IOException;

public class Commit implements ICommand {

    @Override
    public CommandPacket execute(FileManager manager) throws IOException {
        int portToReceive = ClientUtils.getEmptyPort();
        String message = String.format("Server can receive files on port %d", portToReceive);
        ICommand command = new PrepareFilesToSend(portToReceive);
        ThreadDispatcher.getInstance().Add(new CreateNewVersion(manager, portToReceive));
        return new ServerResponse(true, command, message);
    }

    public Commit() {
    }
}
