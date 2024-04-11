package webSocket;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.*;
import service.GameService;
import service.ResponseException;
import service.UserService;
import webSocketMessages.serverMessages.*;
import webSocketMessages.userCommands.*;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebSockets {
    private final ConnectionManager connections = new ConnectionManager();
    private final GameService gameService = new GameService();
    private final AuthDAO authDAO = new DatabaseAuthDAO();
    private final UserDAO userDAO = new DatabaseUserDAO();
    private final GameDAO gameDAO = new DatabaseGameDAO();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case JOIN_OBSERVER -> joinObserver(message, session);
            case JOIN_PLAYER -> joinPlayer(message, session);
            case LEAVE -> leaveGame(message, session);
            case MAKE_MOVE -> makeMove(message, session);
            case RESIGN -> resignGame(message, session);
        }
    }

    private void joinObserver(String json, Session session) throws IOException {
        var joinObserver = new Gson().fromJson(json, webSocketMessages.userCommands.JoinObserver.class);
        int gameID = joinObserver.gameID;
        //Print GameID
        System.out.println(gameID);
        var auth = joinObserver.getAuthString();
        try {
            AuthData user = authDAO.getAuth(auth);
            GameData returnGame = gameDAO.findGame(gameID);
            System.out.println(new Gson().toJson(returnGame));
            if (returnGame == null) {
                connections.error(session, "Error failed to join game!");
            } else {
                if (UserService.validateAuthTokenBoolean(auth)) {
                    System.out.println(returnGame.game());
                    connections.add(user.authToken(), session, gameID);
                    connections.broadcast(user.authToken(), new Notification(user.username() + " is observing the game!"), gameID);
                    connections.respond(user.authToken(), gameID, returnGame);
                } else {
                    connections.error(session, "Error failed to join game! Invalid Auth");
                }
            }
        } catch (DataAccessException | ResponseException | IOException e) {
            connections.error(session, "Error failed to join game!");
        }
    }

    private void joinPlayer(String json, Session session) throws IOException {
        var join = new Gson().fromJson(json, JoinPlayer.class);
        int gameID = join.gameID;
        //Print GameID
        System.out.println(gameID);
        var auth = join.getAuthString();
        var playerColor = join.playerColor;
        try {
            GameData returnGame = gameDAO.findGame(gameID);
            AuthData user = authDAO.getAuth(auth);
            //System.out.println(returnGame.game());
            if (returnGame == null || returnGame.game() == null) {
                connections.error(session, "Error wrong gameID or have no game!");
            } else if (UserService.validateAuthTokenBoolean(auth)) {
                GameData game = gameDAO.findGame(gameID);
                String username = UserService.validateAuthTokenString(auth);
                if (playerColor == ChessGame.TeamColor.BLACK) {
                    if (Objects.equals(game.blackUsername(), username)) {
                        gameDAO.updateGame(new GameData(gameID, game.whiteUsername(), username, game.gameName(), game.game()));
                        connections.add(user.authToken(), session, gameID);
                        connections.broadcast(user.authToken(), new Notification(user.username() + " is joining the game!"), gameID);
                        connections.respond(user.authToken(), gameID, returnGame);
                    } else {
                        connections.error(session, "Error: black user error");
                    }
                } else if (playerColor == ChessGame.TeamColor.WHITE) {
                    if (Objects.equals(game.whiteUsername(), username)) {
                        gameDAO.updateGame(new GameData(gameID, username, game.blackUsername(), game.gameName(), game.game()));
                        connections.add(user.authToken(), session, gameID);
                        connections.broadcast(user.authToken(), new Notification(user.username() + " is joining the game!"), gameID);
                        connections.respond(user.authToken(), gameID, returnGame);
                    } else {
                        connections.error(session, "Error: white user error");

                    }
                } else {
                    connections.error(session, "Error: already taken");
                }
            } else {
                connections.error(session, "Error: unauthorized");
            }
        } catch (DataAccessException | IOException e) {
            connections.error(session, "Error failed to join game!");
        } catch (ResponseException e) {
            connections.error(session, e.getMessage());
        }
    }

    private void leaveGame(String json, Session session) throws IOException {
        var leave = new Gson().fromJson(json, webSocketMessages.userCommands.Leave.class);
        int gameID = leave.gameID;
        //Print GameID
        //System.out.println(gameID);
        var auth = leave.getAuthString();
        try {
            AuthData user = authDAO.getAuth(auth);
            GameData returnGame = gameDAO.findGame(gameID);
            connections.remove(user.authToken());
            connections.broadcast(user.authToken(), new Notification(user.username() + " has left the game!"), gameID);
            connections.respond(user.authToken(), gameID, returnGame);
        } catch (DataAccessException | IOException e) {
            connections.error(session, "Error failed to leave game!");
        }
    }

    private void resignGame(String json, Session session) throws IOException {
        var resign = new Gson().fromJson(json, webSocketMessages.userCommands.Resign.class);
        int gameID = resign.gameID;
        //Print GameID
        //System.out.println(gameID);
        var auth = resign.getAuthString();
        try {
            AuthData user = authDAO.getAuth(auth);
            GameData returnGame = gameDAO.findGame(gameID);
            if (returnGame == null) {
                connections.error(session, "Error failed to resign game!");
            }
            assert returnGame != null;
            if (!Objects.equals(returnGame.blackUsername(), user.username()) && !Objects.equals(returnGame.whiteUsername(), user.username())) {
                connections.error(session, "Error You are just an observer.");
            } else if (returnGame.game().isOver) {
                connections.error(session, "Error game is over.");
            } else {
                connections.broadcastResign(user.authToken(), new Notification(user.username() + " has resigned the game!"), gameID);
                returnGame.game().isOver = true;
                gameDAO.updateGame(returnGame);

                connections.remove(user.authToken());
                //connections.respond(user.authToken(), gameID, returnGame);
            }
        } catch (DataAccessException | IOException e) {
            connections.error(session, "Error failed to resign game!");
        }
    }

    private void makeMove(String json, Session session) throws IOException {
        var moveComm = new Gson().fromJson(json, MakeMove.class);
        int gameID = moveComm.gameID;
        //Print GameID
        //System.out.println(gameID);
        var auth = moveComm.getAuthString();
        var move = moveComm.move;
        try {
            AuthData user = authDAO.getAuth(auth);
            GameData returnGame = gameDAO.findGame(gameID);
            if (returnGame == null || returnGame.game() == null) {
                connections.error(session, "Error failed to make move! Return game empty.");
            }
            if (returnGame.game() == null) throw new InvalidMoveException();
            if (move.getStartPosition() == null) throw new InvalidMoveException();
            var startingPiece = returnGame.game().getBoard().getPiece(move.getStartPosition());
            if (startingPiece != null) {
                ChessGame.TeamColor startingColor = startingPiece.getTeamColor();
                if (Objects.equals(returnGame.blackUsername(), user.username()) && startingColor == ChessGame.TeamColor.WHITE) {
                    connections.error(session, "You tried to make move for opponent.");
                } else if (Objects.equals(returnGame.whiteUsername(), user.username()) && startingColor == ChessGame.TeamColor.BLACK) {
                    connections.error(session, "You tried to make move for opponent.");
                } else if (returnGame.game().isInStalemate(ChessGame.TeamColor.WHITE) || returnGame.game().isInCheckmate(ChessGame.TeamColor.WHITE) || returnGame.game().isInStalemate(ChessGame.TeamColor.BLACK) || returnGame.game().isInCheckmate(ChessGame.TeamColor.BLACK)) {
                    connections.error(session, "Error Game over.");
                    returnGame.game().isOver = true;
                    gameDAO.updateGame(returnGame);
                } else if (!Objects.equals(returnGame.blackUsername(), user.username()) && !Objects.equals(returnGame.whiteUsername(), user.username())) {
                    connections.error(session, "Error You are just an observer.");
                } else if (returnGame.game().isOver) {
                    connections.error(session, "Error game is over.");
                } else {
                    returnGame.game().makeMove(move);
                    gameDAO.updateGame(returnGame);
                    connections.sendMove(user.authToken(), gameID, returnGame.game());
                    connections.broadcast(user.authToken(), new Notification(user.username() + " has made a move"), gameID);
                }
            } else {
                connections.error(session, "Error you choose a blank square!");
            }
        } catch (DataAccessException | IOException e) {
            connections.error(session, "Error failed to make move!");
        } catch (InvalidMoveException e) {
            connections.error(session, "Error Invalid Move.");
        }
    }
}
