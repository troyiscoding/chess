package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

import static ui.EscapeSequences.*;


public class PreLoginClient {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 8;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        out.print(SET_TEXT_COLOR_WHITE);
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "help" -> helpLogin(); //Displays text informing the user what actions they can take.
            case "quit" -> "quit"; // Exits the program.
            case "login" -> login(params); //Prompts the user to input login information.
            case "register" -> register(params); //Prompts the user to input registration information.
            default -> helpLogin();
        };
    }

    //Displays text informing the user what actions they can take.
    public String helpLogin() {
        return "Available commands:\n" +
                "register - <USERNAME> <PASSWORD> <EMAIL>\n" +
                "login - <USERNAME> <PASSWORD>\n" +
                "quit - Exit the program.\n" +
                "help - Help with possible commands\n";
    }

    //Prompts the user to input login information.
    //Calls the server login API to log in the user.
    //When successfully logged in, the client should transition to the Post login UI.
    public String login(String... params) {
        if (params.length >= 2) {
            return "You have logged in.";
        }
        return "Expected: <username> <password>";
    }

    //Prompts the user to input registration information.
    //Calls the server register API to register and login the user.
    //When successfully registered and logged in, the client should be logged in and transition to the Post login UI.
    public String register(String... params) {
        if (params.length >= 2) {
            return "You have registered.";
        }
        return "Expected: <username> <password>";
    }
}
