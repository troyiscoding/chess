package ui;

import java.util.Arrays;

import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;
import static ui.EscapeSequences.SET_TEXT_COLOR_WHITE;

public class PostLogin {
    private final String serverUrl;
    public LoginState states;

    public PostLogin(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "help" -> help(); //Displays text informing the user what actions they can take.
            default -> help();
        };
    }

    //Displays text informing the user what actions they can take now that they are logged in.
    public String help() {
        return "Available commands:\n" +
                SET_TEXT_COLOR_BLUE + "create <NAME> -" + SET_TEXT_COLOR_WHITE + " Create a new game.\n" +
                SET_TEXT_COLOR_BLUE + "list -" + SET_TEXT_COLOR_WHITE + " List all current games.\n" +
                SET_TEXT_COLOR_BLUE + "join <ID> [WHITE|BLACK|<EMPTY>] -" + SET_TEXT_COLOR_WHITE + " Play a game.\n" +
                SET_TEXT_COLOR_BLUE + "observe <ID> -" + SET_TEXT_COLOR_WHITE + " Join game as a watcher\n" +
                SET_TEXT_COLOR_BLUE + "logout -" + SET_TEXT_COLOR_WHITE + " Logout when you are done.\n" +
                SET_TEXT_COLOR_BLUE + "quit -" + SET_TEXT_COLOR_WHITE + " Exit the program.\n" +
                SET_TEXT_COLOR_BLUE + "help -" + SET_TEXT_COLOR_WHITE + " Help with possible commands\n";
    }


}
