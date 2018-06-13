package FileWorker;

import ThreadDispatcher.ThreadedTask;
import VCS.Server.FileManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CreateNewVersion extends ThreadedTask {
    private FileManager manager;
    private final int port;

    public CreateNewVersion(FileManager manager, int port) {
        this.manager = manager;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            String newVersion;
            try (ZipInputStream stream = new ZipInputStream(in)) {
                int length = Integer.parseInt(stream.getNextEntry().getName());
                if (length == 0)
                    return;
                boolean isFullCopy = length < manager.provider.getAllFiles(manager.getRepositoryPath()).length;
                newVersion = manager.versionGenerator.increase(manager.currentVersion, isFullCopy);
                String path = Paths.get(manager.getRepositoryPath(), newVersion).toString();
                ZipEntry entry;
                manager.provider.createNewDirectory(path);
                while ((entry = stream.getNextEntry()) != null) {
                    String filePath = Paths.get(path, entry.getName()).toString();
                    manager.provider.createFile(filePath, null);
                    OutputStream out = manager.provider.getFileOutputStream(filePath);
                    byte[] buffer = new byte[2048];
                    int len;
                    while ((len = stream.read(buffer)) > 0)
                        out.write(buffer, 0, len);
                    out.close();
                }
            }
            manager.currentVersion = newVersion;
        } catch (IOException e) {
            manager.provider.log().Error(e.toString());
        }
    }
}
