package Utils;

import Abstractions.ICommandProvider;

import java.util.Scanner;

public class ConsoleProvider implements ICommandProvider {
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public String readCommand() {
        return scanner.nextLine();
    }

    @Override
    public void writeResult(String result) {
        System.out.println(result);
    }
}
