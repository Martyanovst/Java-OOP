package Utils.Commands;

import ThreadDispatcher.ThreadDispatcher;
import Abstractions.ICommand;
import Abstractions.CommandPacket;
import FileWorker.FileSender;
import FileWorker.getCurrentVersion;
import VCS.Server.FileManager;
import VCS.Server.ServerResponse;

import java.io.IOException;
import java.util.Collections;

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
            getCurrentVersion operation = new getCurrentVersion(manager);
            manager.boundTo(name);
            manager.repository = name;
            manager.worker.execute(operation);
            String[] actualFiles = operation.files.values().toArray(new String[operation.files.size()]);
            String version = manager.actualVersion;
            ThreadDispatcher.getInstance().Add(new FileSender(actualFiles, manager, clientPort, true));
            ICommand command = new SaveFiles(path, flags, name, clientPort, version);
            return new ServerResponse(true, command, "Files successfully cloned");
        } catch (IOException e) {
            return new ServerResponse(false, new Pass(), e.toString());
        }
    }
}
