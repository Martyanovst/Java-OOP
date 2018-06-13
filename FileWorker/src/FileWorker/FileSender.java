package FileWorker;

import ThreadDispatcher.ThreadedTask;
import Utils.ClientUtils;
import Utils.FileItem;
import VCS.Server.FileManager;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipOutputStream;

public class FileSender extends ThreadedTask {
    private final String[] files;
    private final FileManager manager;
    private final int receiverPort;
    private boolean isServer;

    public FileSender(String[] files, FileManager manager, int receiverPort, boolean isServer) {
        this.files = files;
        this.manager = manager;
        this.receiverPort = receiverPort;
        this.isServer = isServer;
    }

    @Override
    public void run() {
        if (files.length == 0) return;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            manager.provider.log().Fatal(e.toString());
        }
        try {
            Socket socket = new Socket(manager.client.address, receiverPort);
            FileItem item;
            try (ZipOutputStream stream = new ZipOutputStream(socket.getOutputStream())) {
                FileCounter counter = new FileCounter();
                manager.worker.execute(counter);
                if (files.length == 0)
                    counter.count = 0;
                FileItem count = new FileItem(Integer.toString(counter.count), false, new byte[0]);
                ClientUtils.sendFile(stream, count);
                for (String filename : files) {
                    boolean isDirectory = manager.provider.isDirectory(filename);
                    byte[] data = manager.provider.readFile(filename);
                    item = new FileItem(getPath(filename), isDirectory, data);
                    ClientUtils.sendFile(stream, item);
                }
            }
        } catch (IOException e) {
            manager.provider.log().Fatal(e.toString());
        }
    }

    private String getPath(String filename) {
        if (isServer) return getServerPath(filename);
        else return getUserPath(filename);
    }

    private String getUserPath(String filename) {
        return Paths.get(manager.getRepositoryPath()).relativize(Paths.get(filename)).toString();
    }

    private String getServerPath(String filename) {
        Path path = Paths.get(manager.getRepositoryPath()).relativize(Paths.get(filename));
        path = path.subpath(1, path.getNameCount());
        return path.toString();
    }
}
