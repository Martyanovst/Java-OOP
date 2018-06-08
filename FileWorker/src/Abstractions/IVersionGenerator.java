package Abstractions;

public interface IVersionGenerator {
    String increase(String version, Boolean isFullCopy);
}
