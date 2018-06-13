package VCS.Client;

import Abstractions.ICommandProvider;
import Abstractions.IDataProvider;
import Abstractions.ILogger;
import Utils.ClientUtils;
import Utils.ConsoleProvider;
import Utils.Constants;
import Utils.Logger;
import VCS.Server.ClientInfo;
import VCS.Server.DefaultVersionGenerator;
import VCS.Server.FileManager;

import static Utils.ClientUtils.*;
import static Utils.Constants.*;

public class ClientEntryPoint {
    public static void main(String[] args) {
        ILogger logger = new Logger(System.out, System.in);
        IDataProvider provider = new FolderProvider(logger);
        ClientInfo client = new ClientInfo(getAddress(HOST));
        FileManager manager = new FileManager(client, provider, new DefaultVersionGenerator());
        ICommandProvider commandProvider = new ConsoleProvider(System.in);
        new Client("Martyanovst", manager, commandProvider).run();
    }
}
