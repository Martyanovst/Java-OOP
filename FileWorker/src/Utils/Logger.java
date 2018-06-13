package Utils;

import Abstractions.ILogger;

import java.io.OutputStream;
import java.io.PrintWriter;

public class Logger implements ILogger {
    private final PrintWriter out;

    public Logger(OutputStream stream) {
        this.out = new PrintWriter(stream, true);
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
    public String getHistory() {
        return null;
    }
}