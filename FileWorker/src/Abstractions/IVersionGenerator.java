package Abstractions;

public interface IVersionGenerator {
    String increase(String version, boolean isFullCopy);

    boolean isFull(String version);
}
