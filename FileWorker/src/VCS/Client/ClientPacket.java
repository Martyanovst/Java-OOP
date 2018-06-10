package VCS.Client;

import Abstractions.CommandPacket;
import Utils.FileItem;
import Abstractions.ICommand;

public class ClientPacket extends CommandPacket {
    public ClientPacket(ICommand command, String clientName) {
        super(true, command, clientName, null);
    }

    public ClientPacket() {
    }
}
