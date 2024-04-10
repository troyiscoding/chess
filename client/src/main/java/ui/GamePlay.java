package ui;

import chess.ChessGame;
import handler.JoinRequest;
import handler.ListResponse;
import webSocket.WebSocketFacade;
import webSocketMessages.userCommands.LEAVE;

import java.util.Arrays;
import java.util.Scanner;

import static ui.DrawChessBoard.drawChessBoard;
import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;
import static ui.EscapeSequences.SET_TEXT_COLOR_WHITE;

public class GamePlay {
    private final String serverUrl;
    public LoginState state = LoginState.SIGNED_IN;
    public ServerFacade facade;
    public String authToken;
    public WebSocketFacade websocket;
    public int gameID;

    public boolean forfeit;

    public GamePlay(String serverUrl, String token, int gameID) {
        this.serverUrl = serverUrl;
        facade = new ServerFacade(serverUrl);
        authToken = token;
        forfeit = false;
        this.gameID = gameID;
        try {
            websocket = new WebSocketFacade(serverUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "help" -> helpGame(); //Displays text informing the user what actions they can take.
            case "redraw" -> redraw();
            case "leave" -> leave();
            case "move" -> move(params);
            case "resign" -> resign();
            case "highlight" -> highlight();
            default -> helpGame();
        };
    }

    //Help - Displays text informing the user what actions hey can take
    public String helpGame() {
        return "Available commands:\n" +
                SET_TEXT_COLOR_BLUE + "Help -" + SET_TEXT_COLOR_WHITE + " Help with possible commands\n" +
                SET_TEXT_COLOR_BLUE + "Redraw -" + SET_TEXT_COLOR_WHITE + " Redraws te chess board.\n" +
                SET_TEXT_COLOR_BLUE + "Leave -" + SET_TEXT_COLOR_WHITE + " Removes you from the game.\n" +
                SET_TEXT_COLOR_BLUE + "move -" + SET_TEXT_COLOR_WHITE + "Make Move <Piece Chosen> <Desired Position>\n" +
                SET_TEXT_COLOR_BLUE + "Resign -" + SET_TEXT_COLOR_WHITE + " Forfeit and end the game.\n" +
                SET_TEXT_COLOR_BLUE + "Highlight -" + SET_TEXT_COLOR_WHITE + "Highlights Legal Moves <Piece To highlight>\n";
    }

    //Redraw Chess Board - Redraws the chess board upon the user's request
    public String redraw() {
        //drawChessBoard();
        DrawBoardNew.drawBoardNew(WebSocketFacade.chessBoard, ChessGame.TeamColor.WHITE);
        return "";
    }

    //Leave - Removes the user from the game (whether they are playing or observing the game).
    public String leave() {
        System.out.println("Leaving Game.");
        try {
            websocket.leaveGame(new LEAVE(authToken, gameID));
        } catch (Exception e) {
            return e.getMessage();
        }
        //The client transitions back to the Post-Login UI.
        PostLoginRepl postLoginRepl = new PostLoginRepl(serverUrl, state, authToken);
        postLoginRepl.run();
        return "Leaving";
    }


    //Make Move - Allow the user to input what move they want to make.
    //The board is updated to reflect the result of the move.
    //The board automatically updates on all clients in the game.
    public String move(String... params) {
        if (params.length >= 1) {
            try {

                return "print board here";
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        }
        return "Expected: <Piece Moving> <Position Moving to> EX: A2 A3";
    }

    //Resign - Prompts the user to confirm they want to resign.
    //If the user confirms, they forfeit the game and the game is over.
    //Does not cause the user to leave the game.
    public String resign() {
        var result = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("ARE YOU SURE YOU WANT TO FORFEIT? Enter (Y/N).");
        result = scanner.nextLine();
        if (result.equals("Y") || result.equals("y")) {
            try {
                //ServerFacade.resign(authToken);

                forfeit = true;
                return "You have forfeited the game.";
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        } else {
            return "You have chosen not to forfeit the game.";
        }
    }

    //Highlight Legal Moves - Allows the user to input what piece for which they want to highlight legal moves
    //The selected pieces current square and all squares it can legally move to are highlighted.
    //This is a local operation and has no effect on remote users screens.
    public String highlight() {
        drawChessBoard();
        return "";
    }

}
