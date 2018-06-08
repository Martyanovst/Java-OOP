package WebServer;

import Utils.ConsoleProvider;
import Abstractions.ICommandProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

import static Utils.ClientUtils.*;
import static Utils.Constants.HOST;
import static Utils.Constants.PORT;
import static Utils.Constants.EOF;

public class Client {
    private static Charset charset = Server.CHARSET;


    public static void main(String[] args) throws IOException {
        Socket client = new Socket(getAddress(HOST), PORT);
        StringBuilder builder = new StringBuilder();
        byte[] buffer = new byte[1024];
        try (InputStream sin = client.getInputStream();
             OutputStream sout = client.getOutputStream()) {
            ICommandProvider commandProvider = new ConsoleProvider(System.in);
            while (!client.isClosed()) {
                byte[] input = (commandProvider.readCommand() + EOF).getBytes();
                sout.write(input, 0, input.length);
                while (!builder.toString().contains(EOF)) {
                    sin.read(buffer, 0, buffer.length);
                    String str = new String(buffer, charset);
                    int index = str.indexOf(EOF);
                    builder.append(index >= 0 ? str.substring(0, index + EOF.length()) : str);
                }
                builder = new StringBuilder();
            }
        }
    }
}
