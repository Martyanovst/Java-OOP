package VCS.Server;

import Abstractions.ICommand;
import Abstractions.CommandPacket;
import Utils.Constants;


public class ServerResponse extends CommandPacket {

    public ServerResponse() {
    }

    public ServerResponse(Boolean isSuccess, ICommand command, String message) {
        super(isSuccess, command, Constants.HOST, message);
    }
}
