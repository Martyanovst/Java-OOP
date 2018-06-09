package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import Utils.FileItem;
import VCS.Server.FileManager;

import java.io.IOException;
import java.nio.file.Paths;

public class SaveFiles implements ICommand {
    private final String path;
    private final FileItem[] files;

    public SaveFiles(String path, FileItem[] files) {
        this.path = path;
        this.files = files;
    }

    @Override
    public CommandPacket execute(FileManager manager) {
        try {
            manager.provider.deleteAllFilesFromDirectory(path);
        } catch (IOException e) {
            manager.provider.log().Fatal(e.toString());
            return null;
        }
        for (FileItem item : files)
            try {
                manager.provider.CreateFile(Paths.get(path, item.name).toString(), item.data);
            } catch (IOException e) {
                manager.provider.log().Fatal(e.toString());
            }
        return null;
    }
}
