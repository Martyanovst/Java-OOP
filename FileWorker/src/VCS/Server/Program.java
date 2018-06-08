package VCS.Server;

import VCS.Client.FolderProvider;
import Utils.Logger;

public class Program {

    public static void main(String[] args) {
        new Server(new FolderProvider(new Logger(System.out)), 10, new DefaultVersionGenerator()).run();
    }
}
