package Abstractions;

public interface ILogger {
    void Fatal(String message);
    void Error(String message);
    void Info(String message);
    void Success(String message);
}
