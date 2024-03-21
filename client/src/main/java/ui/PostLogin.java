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
            case "logout" -> logout(); //Logs the user out and transitions to the Pre login UI.
            case "create" -> create(params); //Prompts the user to input a game name and creates a new game.
            case "list" -> listGames(); //Lists all the games that currently exist on the server.
            case "join" ->
                    joinGame(params); //Prompts the user to specify which game they want to join and what color they want to play.
            case "observe" -> observeGame(params); //Prompts the user to specify which game they want to observe.
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

    //Logs out the user
    //Calls the sercer logout API to log out the user.
    //When successfully logged out, the client should transition to the Pre login UI.
    public String logout() {
        states = LoginState.SIGNED_OUT;
        PreLoginRepl preLoginRepl = new PreLoginRepl(serverUrl);
        preLoginRepl.run();
        return "";
    }

    //Allows the user to input a name for the new game.
    //Calls the server create API to create the game. -ONLY CREATES GAME
    public String create(String... params) {
        if (params.length >= 1) {
            return "You have created a game.";
        }
        return "Expected: <game name>";
    }

    //Lists all the games that currently exist on the server.
    //Calls the server list API to get all the game data
    //Displays the games in a numbered list, including game name and players (not observers).
    //The numbering for the list should be independent of the game ID.
    public String listGames() {
        return "List of games";
    }

    //Allows the user to specify which game they want to join and what color they want to play.
    //They should be able to enter the number of the desired game.
    //Your client will need to keep track of which number corresponds to which game from the last time it listed the games
    //Calls the server join API to join the user to the game.
    public String joinGame(String... params) {
        if (params.length >= 1) {
            return "You have joined a game.";
        }
        return "Expected: <game number> [WHITE|BLACK|<EMPTY>]";
    }

    //Allows the user to specify which game they want to observe.
    //They should be able to enter the number of the desired game.
    //Your client will need to keep track of which number corresponds to which game from the last time it listed the games.
    //Calls the server join API to verify that the game exists.
    public String observeGame(String... params) {
        if (params.length >= 1) {
            return "You have joined a game as an observer.";
        }
        return "Expected: <game number>";
    }

}
