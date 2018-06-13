package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import FileWorker.FileSender;
import FileWorker.getVersion;
import ThreadDispatcher.ThreadDispatcher;
import VCS.Server.FileManager;
import VCS.Server.ServerResponse;

import java.io.IOException;

public class Update implements ICommand {

    private int port;

    public Update(int port) {
        this.port = port;
    }

    private Update() {
    }

    @Override
    public CommandPacket execute(FileManager manager) {
        try {
            if (manager.repository == null) throw new IOException("You should clone repository before updating");
            getVersion operation = new getVersion(manager, null);
            manager.worker.execute(operation);
            String[] actualFiles = operation.files.values().toArray(new String[operation.files.size()]);
            String version = manager.getActualVersion();
            ThreadDispatcher.getInstance().Add(new FileSender(actualFiles, manager, port, true));
            ICommand command = new SaveFiles(port, version, false);
            String message = String.format("Update success, your version is %s", version);
            return new ServerResponse(true, command, message);

        } catch (IOException e) {
            return new ServerResponse(false, new Pass(), e.toString());
        }
    }
}

