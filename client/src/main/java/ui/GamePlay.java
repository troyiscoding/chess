package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import handler.JoinRequest;
import handler.ListResponse;
import webSocket.WebSocketFacade;
import webSocketMessages.userCommands.LEAVE;
import webSocketMessages.userCommands.MAKE_MOVE;
import webSocketMessages.userCommands.RESIGN;

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

    public boolean lockout;

    public boolean forfeit;

    public GamePlay(String serverUrl, String token, int gameID, boolean observer) {
        this.serverUrl = serverUrl;
        facade = new ServerFacade(serverUrl);
        authToken = token;
        forfeit = false;
        this.gameID = gameID;
        lockout = observer;
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
                SET_TEXT_COLOR_BLUE + "Redraw -" + SET_TEXT_COLOR_WHITE + " Redraws the chess board.\n" +
                SET_TEXT_COLOR_BLUE + "Leave -" + SET_TEXT_COLOR_WHITE + " Removes you from the game.\n" +
                SET_TEXT_COLOR_BLUE + "Move -" + SET_TEXT_COLOR_WHITE + "Make Move <Piece Chosen> <Desired Position>\n" +
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
        if (lockout) {
            return "You are an observer and cannot resign.";
        }
        if (forfeit) {
            return "No moves after game completion due to resignation, checkmate, or stalemate.";
        }

        if (params.length >= 1) {
            try {
                var startPosition = new ChessPosition(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                var endPosition = new ChessPosition(Integer.parseInt(params[2]), Integer.parseInt(params[3]));
                websocket.makeMove(new MAKE_MOVE(authToken, gameID, new ChessMove(startPosition, endPosition, null)));
                return "Move made.";
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return "Expected: <Piece Moving> <Position Moving to> EX: A2 A3";
    }

    //Resign - Prompts the user to confirm they want to resign.
    //If the user confirms, they forfeit the game and the game is over.
    //Does not cause the user to leave the game.
    public String resign() {
        if (lockout) {
            return "You are an observer and cannot resign.";
        }
        var result = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("ARE YOU SURE YOU WANT TO FORFEIT? Enter (Y/N).");
        result = scanner.nextLine();
        if (result.equals("Y") || result.equals("y")) {
            try {
                //ServerFacade.resign(authToken);
                websocket.resignGame(new RESIGN(authToken, gameID));
                forfeit = true;
                return "You have forfeited the game.";
            } catch (Exception e) {
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
        if (lockout) {
            return "You are an observer and cannot resign.";
        }
        ChessBoard board = WebSocketFacade.chessBoard;
        drawChessBoard();
        return "";
    }

}
