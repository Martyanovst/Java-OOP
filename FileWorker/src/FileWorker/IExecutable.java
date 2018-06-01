package FileWorker;

import java.io.File;
import java.io.IOException;

public interface IExecutable {
 void process(File file) throws IOException;
}
