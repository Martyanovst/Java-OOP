package FileWorker;

import FileWorker.IExecutable;

import java.io.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Executor implements IExecutable {

    private final File output = new File("md5Hashes.txt");
    private final Charset charset = Charset.forName("UTF-8");

    @Override
    public void process(File file) throws IOException {
        byte[] hash;
        hash = file.isDirectory() ? getHashOfTheDirectory(file) : getHashOfFile(file);
        WriteHashToOutputFile(file, hash);
    }

    private byte[] getHashOfTheDirectory(File file) throws IOException {
        File[] files = file.listFiles();
        StringBuilder builder = new StringBuilder();
        if (files == null) return new byte[0];
        for (File f : files) {
            byte[] hash = getHashOfFile(f);
            builder.append(new String(hash, charset));
            WriteHashToOutputFile(f, hash);
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

    private byte[] GetHashOfString(String input) {
        MessageDigest m = getInstance();
        byte[] data = input.getBytes();
        m.update(data, 0, data.length);
        return m.digest();
    }

     public byte[] getHashOfFile(File file) throws IOException {
        MessageDigest md = getInstance();
        byte[] buffer = new byte[8192];
        try (InputStream input = new FileInputStream(file)) {
            int numRead;
            do {
                numRead = input.read(buffer);
                if (numRead > 0) {
                    md.update(buffer, 0, numRead);
                }
            } while (numRead != -1);
        }
        return md.digest();
    }

    private String Hex(byte[] hash) {
        StringBuilder builder = new StringBuilder();
        for (byte b : hash) {
            builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return builder.toString();
    }

    private void WriteHashToOutputFile(File path, byte[] hash) throws IOException {
        String result = Hex(hash);
        try (FileWriter writer = new FileWriter(output, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.println(path.getPath() + " : " + result);
        }
    }
}

