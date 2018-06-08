package VCS.Server;

import Abstractions.ICommand;
import Abstractions.CommandPacket;
import Utils.Constants;
import Utils.FileItem;


public class ServerResponse extends CommandPacket {

    public ServerResponse(Boolean isSuccess, ICommand command, FileItem[] items, String message) {
        super(isSuccess, command, Constants.HOST, message, items);
    }

    public ServerResponse(Boolean isSuccess, ICommand command, String message) {
        super(isSuccess, command, Constants.HOST, message, null);
    }

    private ServerResponse() {
    }
}
