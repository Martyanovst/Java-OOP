package Utils.Commands;

import Abstractions.CommandPacket;
import Utils.FileItem;
import Abstractions.ICommand;
import VCS.Server.FileManager;

import java.io.IOException;
import java.nio.file.Paths;

public class SaveFiles implements ICommand {
    private String path;
    private FileItem[] files;
    private int flags;
    private String name;

    public SaveFiles(String path, FileItem[] files, int flags, String name) {
        this.path = path;
        this.files = files;
        this.flags = flags;
        this.name = name;
    }

    public SaveFiles() {
    }

    @Override
    public CommandPacket execute(FileManager manager) throws IOException {
        if (flags == 0) {
            path = Paths.get(path, name).toString();
            manager.provider.createNewDirectory(path);
        }
        try {
            manager.provider.deleteAllFilesFromDirectory(path);
        } catch (IOException e) {
            manager.provider.log().Fatal(e.toString());
            return null;
        }
        for (FileItem item : files)
            try {
                manager.provider.createFile(Paths.get(path, item.name).toString(), item.data);
            } catch (IOException e) {
                manager.provider.log().Fatal(e.toString());
            }
        return null;
    }
}
