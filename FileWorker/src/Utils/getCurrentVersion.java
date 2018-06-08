package Utils;

import FileWorker.IExecutable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class getCurrentVersion implements IExecutable {
    private Map<String, String> versions = new HashMap<>();
    public Map<String, String> files = new HashMap<>();

    @Override
    public void process(File file) {
        String filename = file.getName();
        String version = versions.get(filename);
        String currentVersion = file.getParent();
        if (version == null || currentVersion.compareTo(version) > 0) {
            versions.put(file.getName(), currentVersion);
            files.put(file.getName(), file.getAbsolutePath());
        }
    }
}
