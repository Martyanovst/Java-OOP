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
            Files.createDirectory(directory);
    }

    @Override
    public void createFile(String path, byte[] data) throws IOException {
        File targetFile = new File(path);
        File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        if(Files.isDirectory(Paths.get(path))) return;
        Files.write(Paths.get(path),data);
    }

    @Override
    public boolean isDirectory(String path) {
        return Files.isDirectory(Paths.get(path));
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
    public byte[] readFile(String path) throws IOException {
        if (Files.isDirectory(Paths.get(path))) return new byte[0];
        return Files.readAllBytes(Paths.get(path));
    }

    @Override
    public ILogger log() {
        return logger;
    }
}
