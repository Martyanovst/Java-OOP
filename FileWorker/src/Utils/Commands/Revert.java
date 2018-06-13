package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import FileWorker.FileSender;
import FileWorker.getVersion;
import ThreadDispatcher.ThreadDispatcher;
import VCS.Server.FileManager;
import VCS.Server.ServerResponse;

import java.io.IOException;

public class Revert implements ICommand {
    public String version;
    public int flags;
    private int port;

    public Revert(String version, int flags, int port) {
        this.version = version;
        this.flags = flags;
        this.port = port;
    }

    private Revert() {
    }

    @Override
    public CommandPacket execute(FileManager manager) throws IOException {
        String[] actualFiles;
        try {
            if (version == null) {
                getVersion operation = new getVersion(manager, null);
                manager.worker.execute(operation);
                actualFiles = operation.files.values().toArray(new String[operation.files.size()]);
                version = manager.getActualVersion();
            } else {
                if (flags == 0)
                    actualFiles = manager.provider.getAllFiles(version);
                else {
                    getVersion operation = new getVersion(manager, version);
                    manager.worker.execute(operation);
                    actualFiles = operation.files.values().toArray(new String[operation.files.size()]);
                }
            }
            manager.currentVersion = version;
            ThreadDispatcher.getInstance().Add(new FileSender(actualFiles, manager, port, true));
            ICommand command = new SaveFiles(port, version, true);
            String message = String.format("Revert success,", manager.currentVersion);
            return new ServerResponse(true, command, message);
        } catch (IOException e) {
            return new ServerResponse(false, new Pass(), e.toString());
        }
    }
}
