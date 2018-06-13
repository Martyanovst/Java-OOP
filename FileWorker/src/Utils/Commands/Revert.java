package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import VCS.Server.FileManager;

import java.io.IOException;

public class Revert implements ICommand {
    public String version;
    public int flags;

    public Revert(String version, int flags) {
        this.version = version;
        this.flags = flags;
    }

    private Revert() {
    }

    @Override
    public CommandPacket execute(FileManager manager) throws IOException {
        return null;
    }
}
