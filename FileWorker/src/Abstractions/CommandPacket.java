package Abstractions;

import Utils.FileItem;

public abstract class CommandPacket {
    public ICommand command;
    public boolean isSuccess;
    public String message;
    public String source;

    public CommandPacket(Boolean isSuccess, ICommand command, String source, String message) {
        this.isSuccess = isSuccess;
        this.command = command;
        this.source = source;
        this.message = message;
    }

    public CommandPacket() {
    }
}
