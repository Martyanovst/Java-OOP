package VCS.Client;

import Abstractions.ICommandProvider;
import Abstractions.IDataProvider;
import Abstractions.ILogger;
import Utils.ConsoleProvider;
import Utils.Logger;

public class ClientEntryPoint {
    public static void main(String[] args) {
        ILogger logger = new Logger(System.out);
        IDataProvider provider = new FolderProvider(logger);
        ICommandProvider commandProvider = new ConsoleProvider(System.in);
        new Client("Martyanovst", provider, commandProvider).run();
    }
}
