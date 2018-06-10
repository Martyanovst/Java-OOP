package VCS.Client;

import Abstractions.ICommandProvider;
import Abstractions.IDataProvider;
import Abstractions.ILogger;
import Utils.ConsoleProvider;
import Utils.FileItem;
import Utils.Logger;
import VCS.Server.DefaultVersionGenerator;
import VCS.Server.FileManager;

public class ClientEntryPoint {
    public static void main(String[] args) {
        ILogger logger = new Logger(System.out);
        IDataProvider provider = new FolderProvider(logger);
        FileManager manager = new FileManager(provider,new DefaultVersionGenerator());
        ICommandProvider commandProvider = new ConsoleProvider(System.in);
        new Client("Martyanovst", manager, commandProvider).run();
    }
}
