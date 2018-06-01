package WebServer;

import FileWorker.FileWorker;
import FileWorker.Md5Executor;
import ThreadDispatcher.ThreadedTask;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class ProcessClientRequest extends ThreadedTask {
    private final Socket socket;
    private FileWorker worker;
    private static final String defaultDirectory = "C:\\Users\\Comp\\Desktop\\ServerTest";
    private static final String EOF = "\r\n.\r\n";
    private static final String separator = System.lineSeparator();
    private static final Charset charset = Charset.forName("UTF-8");

    ProcessClientRequest(Socket socket) throws FileNotFoundException {
        this.socket = socket;
        worker = new FileWorker(defaultDirectory);
    }

    private String list() {
        String path = worker.getDirectoryName();
        File[] files = new File(path).listFiles();
        StringBuilder builder = new StringBuilder();
        for (File file : files != null ? files : new File[0]) {
            builder.append(file.getName());
            builder.append(separator);
        }
        return builder.toString();
    }

    private String hash(String path) {
        File file = new File(Paths.get(worker.getDirectoryName(), path).toString());
        if (!file.exists()) return "This file doesn't exists";
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(new String(new Md5Executor().getHashOfFile(file), charset));
        } catch (IOException e) {
            return "This file doesn't exists";
        }
        return builder.toString();
    }

    private String createResponse(String request) throws IOException {
        String[] args = request.split(" ");
        switch (args[0].toLowerCase()) {
            case "list":
                return list();
            case "hash":
                if (args.length > 1) return hash(args[1]);
                else return "please, enter directory name";
            case "close":
                socket.close();
                return "socket closed";
        }
        return String.format("incorrect command: %s", request);
    }

    @Override
    public void run() {
        try (InputStream reader = socket.getInputStream();
             OutputStream writer = socket.getOutputStream()) {
            while (true) {
                byte[] buffer = new byte[1024];
                reader.read(buffer,0,buffer.length);
                String request = getMessageBody(new String(buffer, charset));
                if (request == null) break;
                String response = toMessage(createResponse(request));
                writer.write(response.getBytes(charset));
            }
        } catch (IOException e) {
            System.out.println("Socket is not available");
        }
    }

    private static String getMessageBody(String message){
        return message.replace(EOF,"").trim();
    }

    private static String toMessage(String message){
        return message + EOF;
    }

}
