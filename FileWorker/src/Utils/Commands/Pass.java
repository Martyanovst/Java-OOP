package Utils.Commands;

import Abstractions.ICommand;
import Abstractions.CommandPacket;
import Abstractions.IDataProvider;
import VCS.Server.FileManager;

public class Pass implements ICommand {

    @Override
    public CommandPacket execute(FileManager provider) {
        return null;
    }
}
