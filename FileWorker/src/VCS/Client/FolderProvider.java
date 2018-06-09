package VCS.Client;

import Abstractions.IDataProvider;
import Abstractions.ILogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class FolderProvider implements IDataProvider {
    private final ILogger logger;

    public FolderProvider(ILogger logger) {
        this.logger = logger;
    }

    @Override
    public void createNewDirectory(String path) throws IOException {
        Path directory = Paths.get(path);
        if (Files.isDirectory(directory))
            throw new IOException("Directory is already exists");
        else
            Files.createDirectory(directory);
    }

    @Override
    public void CreateFile(String path, byte[] data) throws IOException {
        FileOutputStream out = new FileOutputStream(path);
        out.write(data);
        out.close();
    }


    @Override
    public String[] getAllDirectoriesFrom(String path) {
        ArrayList<String> result = new ArrayList<>();
        File directory = new File(path);
        for (File subdirectory : directory.listFiles()) {
            if (Files.isDirectory(subdirectory.toPath()))
                result.add(subdirectory.getName());
        }
        return result.toArray(new String[result.size()]);
    }

    @Override
    public void deleteAllFilesFromDirectory(String path) {
        Arrays.stream(new File(path).listFiles()).forEach(File::delete);
    }

    @Override
    public ILogger log() {
        return logger;
    }
}
