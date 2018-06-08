package Abstractions;

import Utils.FileItem;

public abstract class CommandPacket {
    public ICommand command;
    public Boolean isSuccess;
    public String message;
    public String source;
    public FileItem[] items;

    public CommandPacket(Boolean isSuccess,ICommand command, String source, String message,FileItem[] items) {
        this.isSuccess = isSuccess;
        this.command = command;
        this.source = source;
        this.message = message;
        this.items = items;
    }

    public CommandPacket(){}
}
