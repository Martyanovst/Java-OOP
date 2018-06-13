package VCS.Client;

import Abstractions.ICommand;
import Utils.ClientUtils;
import Utils.Commands.*;

import static Utils.ClientUtils.*;

public class CommandFactory {

    public static ICommand createInstance(String input) throws IllegalArgumentException {
        String[] data = input.split("\\s");
        String commandType = data[0].toLowerCase();
        try {
            switch (commandType) {
                case "add":
                    if (data.length == 2)
                        return new Add(data[1]);
                case "clone":
                    if (data.length == 3)
                        return new Clone(data[1], data[2], 0, getEmptyPort());
                    if (data[3].equals("."))
                        return new Clone(data[1], data[2], 1, getEmptyPort());
                case "update":
                    if (data.length == 1)
                        return new Update(getEmptyPort());
                case "commit":
                    if (data.length < 2)
                        return new Commit();
                case "revert":
                    if (data.length == 1)
                        return new Revert(null, 1, getEmptyPort());
                    if (data.length == 2)
                        return new Revert(null, 0, getEmptyPort());
                    else if (data[2].equals("-hard"))
                        return new Revert(data[1], 1, getEmptyPort());
                    return new Revert(data[1], 0, getEmptyPort());
                case "log":
                    if (data.length == 1)
                        return new Log();
                default:
                    throw new IllegalArgumentException("incorrect command!");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("incorrect command!");
        }
    }

    private CommandFactory() {
    }
}
