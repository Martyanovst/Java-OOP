package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import VCS.Server.FileManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class SaveFiles implements ICommand {
    private String path;
    private int flags;
    private String name;
    private int port;
    private String version;

    public SaveFiles(String path, int flags, String name, int port, String version) {
        this.path = path;
        this.flags = flags;
        this.name = name;
        this.port = port;
        this.version = version;
    }

    public SaveFiles() {
    }

    public SaveFiles(int port, String version) {
        this.port = port;
        this.version = version;
    }

    @Override
    public CommandPacket execute(FileManager manager) throws IOException {
        if (flags == 0) {
            if (path != null)
                path = Paths.get(path, name).toString();
            else path = manager.getRepositoryPath();
            try {
                manager.provider.createNewDirectory(path);
            } catch (IOException ignored) {
            }
        }
        if (manager.repository == null) {
            manager.boundTo(path);
            manager.repository = name;
        }
        manager.provider.deleteAllFilesFromDirectory(path);
        ServerSocket socket = new ServerSocket(port);
        Socket server = socket.accept();
        ZipEntry entry;
        try (ZipInputStream stream = new ZipInputStream(server.getInputStream())) {
            stream.getNextEntry();
            while ((entry = stream.getNextEntry()) != null) {
                String filePath = Paths.get(path, entry.getName()).toString();
                manager.provider.createFile(filePath, null);
                OutputStream out = manager.provider.getFileOutputStream(filePath);
                int len;
                byte[] buffer = new byte[2048];
                while ((len = stream.read(buffer)) > 0)
                    out.write(buffer, 0, len);
                out.close();
            }
        }
        manager.currentVersion = version;
        return null;
    }
}
