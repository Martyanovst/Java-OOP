package FileWorker;

import FileWorker.IExecutable;
import VCS.Server.FileManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class getCurrentVersion implements IExecutable {
    private Map<String, String> versions = new HashMap<>();
    public Map<String, String> files = new HashMap<>();
    private String currentFullVersion = null;
    private final FileManager manager;

    public getCurrentVersion(FileManager manager) {
        this.manager = manager;
    }

    @Override
    public void process(String file) {
        Path path = Paths.get(manager.repository).relativize(Paths.get(file));
        String filename = path.toString();
        try {
            if (manager.provider.isDirectory(file))
                try {
                    Double.parseDouble(filename);
                    return;
                } catch (NumberFormatException ignored) {
                    try {
                        Double.parseDouble(path.toString().substring(0, filename.length() - 2));
                        if (filename.compareTo(currentFullVersion) > 0)
                            currentFullVersion = filename;
                        return;
                    } catch (NumberFormatException ignored1) {
                    }
                }
        } catch (IOException e) {
            manager.provider.log().Error(e.toString());
        }
        String f = path.subpath(1, path.getNameCount()).toString();
        String version = versions.get(f);
        String currentVersion = path.getParent().toString();
        if (currentFullVersion != null && !currentVersion.equals(currentFullVersion)) return;
        if (version == null || currentVersion.compareTo(version) > 0) {
            if (manager.currentVersion == null || manager.currentVersion.compareTo(currentVersion) >= 0) {
                if (manager.versionGenerator.isFull(currentVersion))
                    currentFullVersion = currentVersion;
                versions.put(f, currentVersion);
                files.put(f, file);
            }
        }
    }
}
