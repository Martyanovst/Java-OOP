package Abstractions;

import VCS.Server.FileManager;

public interface ICommand {
    CommandPacket execute(FileManager provider);
}
