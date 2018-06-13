package VCS.Server;

import Utils.FileLogger;
import VCS.Client.FolderProvider;
import Utils.Logger;

import java.io.File;
import java.io.FileNotFoundException;

public class Program {

    public static void main(String[] args) {
        File log = new File("log.txt");
        try {
            new Server(new FolderProvider(new FileLogger(log)), 10, new DefaultVersionGenerator()).run();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
