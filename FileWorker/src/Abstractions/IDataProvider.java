package Abstractions;

import java.io.*;

public interface IDataProvider {
    void createNewDirectory(String path) throws IOException;

    void createFile(String path, byte[] data) throws IOException;

    boolean isDirectory(String path) throws IOException;

    String[] getAllDirectoriesFrom(String path) throws FileNotFoundException;

    void deleteAllFilesFromDirectory(String path) throws IOException;

    byte[] readFile(String path) throws IOException;

    ILogger log();
}
