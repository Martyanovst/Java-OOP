import java.io.IOException;

public class Program {

    public static void main(String[] args) {
        try {
            FileWorker worker = new FileWorker("folder");
            worker.setIsRecursive(true);
            worker.execute(new Md5Executor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}