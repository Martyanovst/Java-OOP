package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import Abstractions.IDataProvider;
import VCS.Server.FileManager;

public class Update implements ICommand {

    public Update(){}

    @Override
    public CommandPacket execute(FileManager provider) {
        return null;
    }
}
