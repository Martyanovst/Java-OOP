package VCS.Client;

import Abstractions.CommandPacket;
import Abstractions.ICommand;
import Abstractions.ICommandProvider;
import Abstractions.IDataProvider;
import Utils.ClientUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static Utils.ClientUtils.getAddress;
import static Utils.Constants.*;

public class Client {
    private final String name;
    private IDataProvider provider;
    private ICommandProvider commandProvider;

    public Client(String name, IDataProvider provider, ICommandProvider commandProvider) {
        this.name = name;
        this.provider = provider;
        this.commandProvider = commandProvider;
    }

    public void run() {
        try {
            Socket client = new Socket(getAddress(HOST), PORT);
            try (InputStream in = client.getInputStream();
                 OutputStream out = client.getOutputStream()) {
                while (!client.isClosed()) {
                    ICommand command = commandProvider.readCommand();
                    CommandPacket packet = new ClientPacket(command, name);
                    ClientUtils.SendPacket(packet, out);
                    CommandPacket response = ClientUtils.getRequest(in);
//                    response.command.execute(provider);
                    if(response.isSuccess)
                    provider.log().Success(response.message);
                    else
                      provider.log().Error(response.message);

                }
            } catch (IOException e) {
                provider.log().Fatal(e.getMessage());
            }
        } catch (IOException e) {
            provider.log().Fatal(e.getMessage());
        }
    }
}