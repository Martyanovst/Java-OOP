package Abstractions;

import Utils.Commands.Update;
import Utils.FileItem;

import java.io.*;
import java.util.Collection;

public interface IDataProvider {
    void createNewDirectory(String path) throws IOException;

    void CreateFile(String path, byte[] data) throws IOException;

    String[] getAllDirectoriesFrom(String path) throws FileNotFoundException;

    ILogger log();
}
