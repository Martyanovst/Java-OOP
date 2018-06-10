package VCS.Server;

import Abstractions.IDataProvider;
import Abstractions.IVersionGenerator;
import FileWorker.FileWorker;

import java.io.FileNotFoundException;

public class FileManager {
    public final IDataProvider provider;
    public final IVersionGenerator versionGenerator;
    public FileWorker worker;

    public FileManager(IDataProvider provider, IVersionGenerator versionGenerator) {
        this.provider = provider;
        this.versionGenerator = versionGenerator;
    }

    public void boundTo(String directoryPath) throws FileNotFoundException {
        worker = new FileWorker(directoryPath);
        worker.setIsRecursive(true);
    }
}
