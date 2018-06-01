package FileWorker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileWorker {
    private boolean isRecursive = false;
    private final File path;

    public boolean getIsRecursive() {
        return isRecursive;
    }
    public void setIsRecursive(boolean value) {
        isRecursive = value;
    }

    public FileWorker(String path) throws FileNotFoundException {
        this.path = new File(path);
        if (!this.path.exists())
            throw new FileNotFoundException("This path doesn't exists");
        if (!this.path.isDirectory())
            throw new FileNotFoundException("This file isn't a directory");
    }


    public void execute(IExecutable command) throws IOException {
        execute(command, path);
    }

    private void execute(IExecutable command, File path) throws IOException {
        File[] files = path.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory() && isRecursive)
                execute(command, file);
            //if (!file.isDirectory())
                command.process(file);
        }
    }

    public String getDirectoryName(){
        return path.getAbsolutePath();
    }
}
