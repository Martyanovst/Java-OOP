package Utils;

import Abstractions.ILogger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static Utils.Constants.*;

public class FileLogger implements ILogger {
    private File file;

    public FileLogger(File file) throws FileNotFoundException {
        this.file = file;
    }

    @Override
    public void Fatal(String message) {
        try {
            write(String.format("FATAL: %s\n", message));
        } catch (IOException ignored) {

        }
    }

    private void write(String format) throws IOException {
        Files.write(file.toPath(), format.getBytes(), StandardOpenOption.APPEND);
    }

    @Override
    public void Error(String message) {
        try {
            write(String.format("ERROR: %s\n", message));
        } catch (IOException ignored) {

        }
    }

    @Override
    public void Info(String message) {
        try {
            write(String.format("INFO: %s", message));
        } catch (IOException ignored) {

        }
    }

    @Override
    public void Success(String message) {
        try {
            write(String.format("SUCCESS: %s\n", message));
        } catch (IOException ignored) {

        }
    }

    @Override
    public String getHistory() {
        try {
            return new String(Files.readAllBytes(file.toPath()), CHARSET);
        } catch (IOException ignored) {
            return null;
        }
    }
}
