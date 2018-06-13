package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import FileWorker.Md5Executor;
import ThreadDispatcher.ThreadDispatcher;
import FileWorker.FileSender;
import VCS.Server.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PrepareFilesToSend implements ICommand {

    private int port;

    public PrepareFilesToSend(int port) {
        this.port = port;
    }

    private PrepareFilesToSend() {
    }

    @Override
    public CommandPacket execute(FileManager manager) throws IOException {
        if (manager.currentVersion == null)
            throw new IOException("You should clone repository before committing");
        Md5Executor hasher = new Md5Executor(manager.provider);
        manager.worker.execute(hasher);
        HashMap<String, String> newHashes = hasher.hashes;
        ArrayList<String> filesToSend = new ArrayList<>();
        for (String key : newHashes.keySet()) {
            String oldHash = manager.hashes.get(key);
            String newHash = newHashes.get(key);
            if (oldHash == null || !oldHash.equals(newHash)) filesToSend.add(key);
        }
        String[] files = filesToSend.toArray(new String[filesToSend.size()]);
        manager.hashes = newHashes;
        ThreadDispatcher.getInstance().Add(new FileSender(files, manager, port, false));
        return null;
    }
}
