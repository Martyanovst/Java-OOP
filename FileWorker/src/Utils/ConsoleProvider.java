package Utils;

import Abstractions.ICommand;
import Abstractions.ICommandProvider;
import VCS.Client.CommandFactory;

import java.io.*;

public class ConsoleProvider implements ICommandProvider {
    private final BufferedReader in;

    public ConsoleProvider(InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in));
    }

    @Override
    public ICommand readCommand() throws IOException {
        String input = in.readLine();
        return CommandFactory.createInstance(input);
    }


}
