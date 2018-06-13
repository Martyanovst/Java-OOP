package FileWorker;

import Abstractions.IDataProvider;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class FileWorker {
    private boolean isRecursive = false;
    private final IDataProvider provider;
    private final String path;

    public boolean getIsRecursive() {
        return isRecursive;
    }

    public void setIsRecursive(boolean value) {
        isRecursive = value;
    }

    public FileWorker(IDataProvider provider, String path) throws IOException {
        this.provider = provider;
        this.path = path;
        if (!provider.exists(path))
            throw new FileNotFoundException("This path doesn't exists");
        if (!provider.isDirectory(path))
            throw new FileNotFoundException("This file isn't a directory");
    }


    public void execute(IExecutable command) throws IOException {
        execute(command, path);
    }

    private void execute(IExecutable command, String path) throws IOException {
        String[] files = provider.getAllFiles(path);
        Arrays.sort(files);
        Collections.reverse(Arrays.asList(files));
        if (files == null) return;
        for (String file : files) {
            if (provider.isDirectory(file) && isRecursive)
                execute(command, file);
            //if (!file.isDirectory())
            command.process(file);
        }
    }

    public String getDirectoryName() {
        return path;
    }
}
