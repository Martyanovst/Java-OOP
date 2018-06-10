package Utils.Commands;

import Utils.FileItem;
import Abstractions.ICommand;
import Abstractions.CommandPacket;
import Utils.getCurrentVersion;
import VCS.Server.FileManager;
import VCS.Server.ServerResponse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class Clone implements ICommand {
    private String path;
    private String name;
    private int flags;

    private Clone() {
    }

    public Clone(String path, String name, int flags) {
        this.path = path;
        this.name = name;
        this.flags = flags;
    }

    @Override
    public CommandPacket execute(FileManager manager) {
        try {
            getCurrentVersion operation = new getCurrentVersion();
            manager.boundTo(name);
            manager.worker.execute(operation);
            Collection<String> actualFiles = operation.files.values();
            FileItem[] filesData = new FileItem[actualFiles.size()];
            int index = 0;
            for (String filename : actualFiles) {
                Path path = Paths.get(name).relativize(Paths.get(filename));
                int count = path.getNameCount();
                path = path.subpath(1, count);
                boolean isDirectory = manager.provider.isDirectory(filename);
                byte[] data = manager.provider.readFile(filename);
                filesData[index++] = new FileItem(path.toString(), isDirectory, data);
            }
            return new ServerResponse(true, new SaveFiles(path, filesData, flags, name), "Files successfully cloned");
        } catch (IOException e) {
            return new ServerResponse(false, new Pass(), e.toString());
        }
    }
}
