package VCS.Server;

import Abstractions.IDataProvider;
import Abstractions.IVersionGenerator;
import FileWorker.FileWorker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class FileManager {
    public final IDataProvider provider;
    public final IVersionGenerator versionGenerator;
    public final ClientInfo client;
    public FileWorker worker;
    public String currentVersion = null;
    public String actualVersion= null;
    public HashMap<String, String> hashes = new HashMap<>();
    public  String repository;

    public FileManager(ClientInfo client, IDataProvider provider, IVersionGenerator versionGenerator) {
        this.client = client;
        this.provider = provider;
        this.versionGenerator = versionGenerator;
    }

    public void boundTo(String directoryPath) throws IOException {
        worker = new FileWorker(provider, directoryPath);
        worker.setIsRecursive(true);
    }

    public void updateActualVersion(){
        actualVersion = Collections.max(Arrays.asList(versions));
    }

    public String getRepositoryPath() {
        return worker.getDirectoryName();
    }
}
