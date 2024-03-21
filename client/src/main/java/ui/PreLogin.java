package ui;

import java.util.Arrays;

import static ui.EscapeSequences.*;


public class PreLogin {
    private final String serverUrl;
    public LoginState state = LoginState.SIGNED_OUT;

    public PreLogin(String serverUrl) {
        this.serverUrl = serverUrl;
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
                SET_TEXT_COLOR_BLUE + "register -" + SET_TEXT_COLOR_WHITE + " <USERNAME> <PASSWORD> <EMAIL>\n" +
                SET_TEXT_COLOR_BLUE + "login -" + SET_TEXT_COLOR_WHITE + " <USERNAME> <PASSWORD>\n" +
                SET_TEXT_COLOR_BLUE + "quit -" + SET_TEXT_COLOR_WHITE + " Exit the program.\n" +
                SET_TEXT_COLOR_BLUE + "help -" + SET_TEXT_COLOR_WHITE + " Help with possible commands\n";
    }


    //Prompts the user to input login information.
    //Calls the server login API to log in the user.
    //When successfully logged in, the client should transition to the Post login UI.
    public String login(String... params) {
        if (params.length >= 2) {
            System.out.println("You have logged in.");
            state = LoginState.SIGNED_IN;
            PostLoginRepl postLoginRepl = new PostLoginRepl(serverUrl, state);
            postLoginRepl.run();
            return "";
        }
        return "Expected: <username> <password>";
    }

    //Prompts the user to input registration information.
    //Calls the server register API to register and login the user.
    //When successfully registered and logged in, the client should be logged in and transition to the Post login UI.
    public String register(String... params) {
        if (params.length >= 2) {
            state = LoginState.SIGNED_IN;
            return "You have registered.";
        }
        return "Expected: <username> <password>";
    }
}
