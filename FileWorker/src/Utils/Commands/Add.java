package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import Abstractions.IDataProvider;
import VCS.Server.FileManager;
import VCS.Server.ServerResponse;

import java.io.IOException;

public class Add implements ICommand {
    private String name;

    public Add(String name) {
        this.name = name;
    }

    private Add() {
    }

    @Override
    public CommandPacket execute(FileManager manager) {
        try {
            manager.provider.createNewDirectory(name);
            manager.BoundTo(name);
        } catch (IOException e) {
            return new ServerResponse(false, new Pass(), e.toString());
        }
        return new ServerResponse(true, new Pass(), "New repository successfully created");
    }
}
