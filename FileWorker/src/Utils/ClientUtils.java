package Utils;

import Abstractions.CommandPacket;
import Serializer.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

import static Utils.Constants.CHARSET;
import static Utils.Constants.EOF;

public class ClientUtils {
    public static Serializer serializer = new Serializer();

    public static CommandPacket getRequest(InputStream in) throws IOException {
        byte[] request = readMessage(in);
        return (CommandPacket) serializer.deserialize(request);
    }

    public static int getEmptyPort() {
        try {
            ServerSocket emptySockets = new ServerSocket(0);
            return emptySockets.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
            return Constants.RECEIVER_PORT;
        }
    }

    public static void sendFile(OutputStream stream, FileItem item){
        StringBuilder builder = new StringBuilder();
    }

    public static FileItem ReceiveFile(){

    }

    public static ServerSocket getSocketReceiver() throws IOException {
        return new ServerSocket(Constants.RECEIVER_PORT);
    }

    public static void SendPacket(CommandPacket packet, OutputStream out) throws IOException {
        byte[] response = serializer.serialize(packet);
        out.write(response);
    }

    public static InetAddress getAddress(String HOST) {
        InetAddress addr = null;
        try {
            addr = Inet4Address.getByName(HOST);
        } catch (UnknownHostException ignored) {
        }
        return addr;
    }

    public static byte[] readMessage(InputStream in) throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[] buffer = new byte[1024];
        int pointer = 0;
        int index = -1;
        while (index < 0 && pointer != -1) {
            pointer = in.read(buffer, 0, buffer.length);
            String str = new String(buffer, CHARSET);
            index = str.indexOf(EOF);
            builder.append(index >= 0 ? str.substring(0, index + EOF.length()) : str);
        }
        return builder.toString().getBytes(CHARSET);
    }

    private ClientUtils() {
    }
}
