package VCS.Client;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import Abstractions.ICommandProvider;
import Utils.ClientUtils;
import VCS.Server.FileManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static Utils.ClientUtils.getAddress;
import static Utils.Constants.HOST;
import static Utils.Constants.SERVER_PORT;

public class Client {
    private final String name;
    private FileManager manager;
    private ICommandProvider commandProvider;

    public Client(String name, FileManager manager, ICommandProvider commandProvider) {
        this.name = name;
        this.manager = manager;
        this.commandProvider = commandProvider;
    }

    public void run() {
        try {
            Socket client = new Socket(getAddress(HOST), SERVER_PORT);
            try (InputStream in = client.getInputStream();
                 OutputStream out = client.getOutputStream()) {
                while (!client.isClosed()) {
                    ICommand command = commandProvider.readCommand();
                    CommandPacket packet = new ClientPacket(command, name);
                    ClientUtils.SendPacket(packet, out);
                    CommandPacket response;
                    response = ClientUtils.getRequest(in);
                    if (response.command != null)
                        response.command.execute(manager);
                    else
                        manager.provider.log().Error("Bad server response,maybe your command doesn't valid");
                    if (response.isSuccess)
                        manager.provider.log().Success(response.message);
                    else
                        manager.provider.log().Error(response.message);

                }
            } catch (IOException e) {
                manager.provider.log().Fatal(e.getMessage());
            }
        } catch (IOException e) {
            manager.provider.log().Fatal(e.getMessage());
        }
    }
}