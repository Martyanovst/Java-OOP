package FileWorker;

import Abstractions.IDataProvider;

import java.io.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Md5Executor implements IExecutable {

    public final HashMap<String, String> hashes = new HashMap<>();
    private final IDataProvider provider;
    private final Charset charset = Charset.forName("UTF-8");

    public Md5Executor(IDataProvider provider) {
        this.provider = provider;
    }

    @Override
    public void process(String file) throws IOException {
        String hash;
        hash = provider.isDirectory(file) ? getHashOfTheDirectory(file) : getHashOfFile(file);
        WriteHashToOutput(file, hash);
    }

    private String getHashOfTheDirectory(String file) throws IOException {
        String [] files = provider.getAllFiles(file);
        StringBuilder builder = new StringBuilder();
        if (files == null) return "";
        for (String f : files) {
            String hash = getHashOfFile(f);
            builder.append(hash);
            WriteHashToOutput(f, hash);
        }
        return GetHashOfString(builder.toString());
    }

    private static MessageDigest getInstance() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String GetHashOfString(String input) {
        MessageDigest m = getInstance();
        byte[] data = input.getBytes();
        m.update(data, 0, data.length);
        return hex(m.digest());
    }

    public String getHashOfFile(String file) throws IOException {
        MessageDigest md = getInstance();
        byte[] buffer = new byte[8192];
        try (InputStream input = provider.getFileInputStream(file)) {
            int numRead;
            do {
                numRead = input.read(buffer);
                if (numRead > 0) {
                    md.update(buffer, 0, numRead);
                }
            } while (numRead != -1);
        }
        return hex(md.digest());
    }

    private String hex(byte[] hash) {
        StringBuilder builder = new StringBuilder();
        for (byte b : hash) {
            builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return builder.toString();
    }

    private void WriteHashToOutput(String path, String hash) throws IOException {
        hashes.put(path, hash);
    }
}

