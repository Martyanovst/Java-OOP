package Utils;

public class FileItem {
    public String name;
    public byte[] data;
    public boolean isDirectory;

    public FileItem(String name, boolean isDirectory, byte[] data) {
        this.name = name;
        this.data = data;
        this.isDirectory = isDirectory;
    }

    public FileItem() {
    }
}
