package Abstractions;

import java.io.IOException;

public interface ILogger {
    void Fatal(String message);

    void Error(String message);

    void Info(String message);

    void Success(String message);

    String getHistory() throws IOException;
}
