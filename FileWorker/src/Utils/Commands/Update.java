package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import FileWorker.FileSender;
import FileWorker.getCurrentVersion;
import ThreadDispatcher.ThreadDispatcher;
import VCS.Server.FileManager;
import VCS.Server.ServerResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

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
            getCurrentVersion operation = new getCurrentVersion(manager);
            manager.worker.execute(operation);
            String[] actualFiles = operation.files.values().toArray(new String[operation.files.size()]);
            String[] versions = manager.provider.getAllDirectoriesFrom(manager.getRepositoryPath());
            String version = manager.actualVersion;
            ThreadDispatcher.getInstance().Add(new FileSender(actualFiles, manager, port, true));
            ICommand command = new SaveFiles(port, version);
            return new ServerResponse(true, command, "Files successfully cloned");
        } catch (IOException e) {
            return new ServerResponse(false, new Pass(), e.toString());
        }
    }
}

