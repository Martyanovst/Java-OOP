package VCS.Server;

import Abstractions.IVersionGenerator;

public class DefaultVersionGenerator implements IVersionGenerator {

    @Override
    public synchronized String increase(String version, boolean isFullCopy) {
        if (version == null)
            return "1.0";
        double value = Double.parseDouble(version);
        value += 0.1;
        if (isFullCopy)
            return Double.toString(value) + "f";
        else
            return Double.toString(value);
    }

    @Override
    public boolean isFull(String version) {
        return version == null || version.charAt(version.length() - 1) == 'f';
    }
}
