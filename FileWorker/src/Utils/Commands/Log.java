package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import VCS.Server.FileManager;
import VCS.Server.ServerResponse;

import java.io.IOException;

public class Log implements ICommand {
    @Override
    public CommandPacket execute(FileManager manager) throws IOException {
        String message = manager.provider.log().getHistory();
        return new ServerResponse(true, new Pass(), message);
    }
}
