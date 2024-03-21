import chess.*;
import ui.PreLoginRepl;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new PreLoginRepl(serverUrl).run();
    }
}