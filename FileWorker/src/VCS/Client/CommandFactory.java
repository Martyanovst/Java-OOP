package VCS.Client;

import Abstractions.ICommand;
import Utils.Commands.Add;
import Utils.Commands.Clone;
import Utils.Commands.Update;

public class CommandFactory {

   public static ICommand createInstance(String input) throws IllegalArgumentException{
       String[] data = input.split("\\s");
       String commandType = data[0].toLowerCase();
       try {
           switch (commandType) {
               case "add":
                   if (data.length == 2)
                       return new Add(data[1]);
               case "clone":
                   if (data.length == 3)
                       return new Clone(data[1], data[2], 0);
                   if (data[3].equals("."))
                       return new Clone(data[1], data[2], 1);
               case "update":
                   if (data.length == 1)
                       return new Update();
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
