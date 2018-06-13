package Utils;


import java.nio.charset.Charset;

public class Constants {
    public static String EOF = "\r\n.\r\n";
    public static final int SERVER_PORT = 10000;
    public static final int RECEIVER_PORT = 10001;
    public static final String HOST = "localhost";
    public static final Charset CHARSET = Charset.forName("UTF-8");

    private Constants() {
    }
}
