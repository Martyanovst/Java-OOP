package Utils;

public class FileItem {
    public String name;
    public byte[] data;

    public FileItem(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    private FileItem() {
    }
}
