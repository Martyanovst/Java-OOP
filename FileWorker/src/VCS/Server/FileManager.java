package VCS.Server;

import Abstractions.IDataProvider;
import Abstractions.IVersionGenerator;
import FileWorker.FileWorker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class FileManager {
    public final IDataProvider provider;
    public final IVersionGenerator versionGenerator;
    public final ClientInfo client;
    public FileWorker worker;
    public String currentVersion = null;
    public HashMap<String, String> hashes = new HashMap<>();
    public String repository;
    private static ConcurrentHashMap<String, String> actualRepoVersions = new ConcurrentHashMap<>();

    public FileManager(ClientInfo client, IDataProvider provider, IVersionGenerator versionGenerator) {
        this.client = client;
        this.provider = provider;
        this.versionGenerator = versionGenerator;
    }

    public void boundTo(String directoryPath) throws IOException {
        worker = new FileWorker(provider, directoryPath);
        worker.setIsRecursive(true);
    }

    public void updateActualVersion(String version) {
        currentVersion = version;
        String old = actualRepoVersions.get(repository);
        if (old == null || version.compareTo(old) > 0) {
            actualRepoVersions.put(repository, version);
        }
    }

    public String getActualVersion() {
        return actualRepoVersions.get(repository);
    }

    public String getRepositoryPath() {
        return worker.getDirectoryName();
    }
}
