package FileWorker;

import java.io.IOException;

public class FileCounter implements IExecutable {
    public int count = 0;

    @Override
    public void process(String file) throws IOException {
        count++;
    }
}
