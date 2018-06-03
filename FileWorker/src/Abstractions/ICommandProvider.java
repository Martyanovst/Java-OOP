package Abstractions;

public interface ICommandProvider {
    String readCommand();

    void writeResult(String result);
}
