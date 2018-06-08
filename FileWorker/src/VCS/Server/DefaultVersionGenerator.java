package VCS.Server;

import Abstractions.IVersionGenerator;

public class DefaultVersionGenerator implements IVersionGenerator {

    @Override
    public String increase(String version, Boolean isFullCopy) {
        double value = Double.parseDouble(version);
        value += 0.1;
        if (isFullCopy)
            return Double.toString(value) + "f";
        else
            return Double.toString(value);
    }
}
