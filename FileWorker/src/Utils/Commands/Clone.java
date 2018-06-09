package Utils.Commands;

import Abstractions.ICommand;
import Abstractions.CommandPacket;
import Abstractions.IDataProvider;
import FileWorker.IExecutable;
import Utils.FileItem;
import Utils.getCurrentVersion;
import VCS.Server.FileManager;
import VCS.Server.ServerResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
            if (flags == 0) {
                path = Paths.get(path, name).toString();
                manager.provider.createNewDirectory(path);
            }
            getCurrentVersion operation = new getCurrentVersion();
            manager.worker.execute(operation);
            Collection<String> actualFiles = operation.files.values();
            FileItem[] filesData = new FileItem[actualFiles.size()];
            int index = 0;
            for (String filename : actualFiles) {
                Path path = Paths.get(name).relativize(Paths.get(filename));
                byte[] data = Files.readAllBytes(path);
                filesData[index++] = new FileItem(path.toString(), data);
            }
            return new ServerResponse(true, new SaveFiles(path, filesData), "Files successfully cloned");
        } catch (IOException e) {
            return new ServerResponse(false, new Pass(), e.toString());
        }
    }
}
