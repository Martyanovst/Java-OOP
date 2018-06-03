package Utils;

import Abstractions.ICommandPacket;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientUtils {
    public static ICommandPacket getRequest(Socket client) {
        return null;
    }

    public static void sendResponse(ICommandPacket response, Socket client) {
    }

    public static InetAddress getAddress(String HOST)
    {
        InetAddress addr = null;
        try {
            addr = Inet4Address.getByName(HOST);
        } catch (UnknownHostException ignored){}
        return addr;
    }

    private ClientUtils(){}
}
