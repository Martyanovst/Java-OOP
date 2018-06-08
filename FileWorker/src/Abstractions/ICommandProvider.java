package Abstractions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ICommandProvider {
    ICommand readCommand() throws IOException;
}
