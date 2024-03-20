import chess.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }
        System.out.println("♕ Welcome to 240 chess. Type Help to get started. ♕");
        //new Repl(serverUrl).run();
    }
}