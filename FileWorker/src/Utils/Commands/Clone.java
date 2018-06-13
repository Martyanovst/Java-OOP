package Utils.Commands;

import FileWorker.getVersion;
import ThreadDispatcher.ThreadDispatcher;
import Abstractions.ICommand;
import Abstractions.CommandPacket;
import FileWorker.FileSender;
import VCS.Server.FileManager;
import VCS.Server.ServerResponse;

import java.io.IOException;

public class Clone implements ICommand {
    private String path;
    private String name;
    private int flags;
    private int clientPort;

    private Clone() {
    }

    public Clone(String path, String name, int flags, int clientPort) {
        this.path = path;
        this.name = name;
        this.flags = flags;
        this.clientPort = clientPort;
    }


    @Override
    public CommandPacket execute(FileManager manager) {
        try {
            getVersion operation = new getVersion(manager, null);
            manager.boundTo(name);
            manager.repository = name;
            manager.worker.execute(operation);
            String[] actualFiles = operation.files.values().toArray(new String[operation.files.size()]);
            String version = manager.getActualVersion();
            manager.currentVersion = version;
            ThreadDispatcher.getInstance().Add(new FileSender(actualFiles, manager, clientPort, true));
            ICommand command = new SaveFiles(path, flags, name, clientPort, version, true);
            String message = String.format("Clone files success, your version is %s", version);
            return new ServerResponse(true, command, message);

        } catch (IOException e) {
            return new ServerResponse(false, new Pass(), e.toString());
        }
    }
}
