package Abstractions;

import VCS.Server.FileManager;

import java.io.IOException;

public interface ICommand {
    CommandPacket execute(FileManager manager) throws IOException;
}
