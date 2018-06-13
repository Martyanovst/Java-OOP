package Abstractions;

import java.io.*;
import java.io.InputStream;

public interface IDataProvider {
    void createNewDirectory(String path) throws IOException;

    void createFile(String path, byte[] data) throws IOException;

    boolean isDirectory(String path) throws IOException;

    String[] getAllDirectoriesFrom(String path) throws IOException;

    void deleteAllFilesFromDirectory(String path) throws IOException;

    String[] getAllFiles(String path) throws IOException;

    OutputStream getFileOutputStream(String path) throws IOException;

    InputStream getFileInputStream(String path) throws IOException;

    boolean exists(String path);

    byte[] readFile(String path) throws IOException;

    ILogger log();
}
