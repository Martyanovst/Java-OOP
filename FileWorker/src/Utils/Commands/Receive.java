package Utils.Commands;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import Utils.ClientUtils;
import VCS.Server.FileManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receive implements ICommand {
    private String[] files;

    public Receive(String[] files) {
        this.files = files;
    }

    @Override
    public CommandPacket execute(FileManager provider) {
        try {
            ServerSocket receiver = ClientUtils.getSocketReceiver();
            byte[] buffer = new byte[8192];
            Socket socket = receiver.accept();
            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
