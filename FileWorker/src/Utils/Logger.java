package Utils;

import Abstractions.ILogger;

import java.io.*;

import static Utils.Constants.*;
import static java.lang.System.in;

public class Logger implements ILogger {
    private final PrintWriter out;
    private final InputStream reader;

    public Logger(OutputStream stream, InputStream in) {
        this.out = new PrintWriter(stream, true);
        this.reader = in;
    }

    public Logger(File file) throws FileNotFoundException {
        this.out = new PrintWriter(new FileOutputStream(file), true);
        this.reader = new FileInputStream(file);
    }

    @Override
    public void Fatal(String message) {
        out.println(String.format("FATAL: %s", message));
    }

    @Override
    public void Error(String message) {
        out.println(String.format("ERROR: %s", message));
    }

    @Override
    public void Info(String message) {
        out.println(String.format("INFO: %s", message));
    }

    @Override
    public void Success(String message) {
        out.println(String.format("SUCCESS: %s", message));
    }

    @Override
    public String getHistory() throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[] buffer = new byte[2048];
        while (reader.read(buffer) > 0)
            builder.append(new String(buffer, CHARSET));
        return builder.toString();
    }
}
