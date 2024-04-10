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
            case JOIN_OBSERVER -> JoinObserver(message, session);
            case JOIN_PLAYER -> JoinPlayer(message, session);
            case LEAVE -> leaveGame(message, session);
            case MAKE_MOVE -> makeMove(message, session);
            case RESIGN -> resignGame(message, session);
        }
    }

    private void JoinObserver(String Json, Session session) throws IOException {
        var JoinObserver = new Gson().fromJson(Json, JOIN_OBSERVER.class);
        int gameID = JoinObserver.gameID;
        //Print GameID
        System.out.println(gameID);
        var auth = JoinObserver.getAuthString();
        try {
            AuthData user = authDAO.getAuth(auth);
            GameData returnGame = gameDAO.findGame(gameID);
            System.out.println(new Gson().toJson(returnGame));
            if (returnGame == null) {
                connections.error(session, "Error failed to resign game!");
            } else {
                if (UserService.validateAuthTokenBoolean(auth)) {
                    System.out.println(returnGame.game());
                    connections.add(user.authToken(), session, gameID);
                    connections.broadcast(user.authToken(), new Notification(user.username() + " is observing the game!"), gameID);
                    connections.respond(user.authToken(), gameID, returnGame);
                } else {
                    connections.error(session, "Error failed to resign game! Invalid Auth");
                }
            }
        } catch (DataAccessException | ResponseException | IOException e) {
            connections.error(session, "Error failed to join game!");
        }
    }

    private void JoinPlayer(String Json, Session session) throws IOException {
        var Join = new Gson().fromJson(Json, JOIN_PLAYER.class);
        int gameID = Join.gameID;
        //Print GameID
        System.out.println(gameID);
        var auth = Join.getAuthString();
        var playerColor = Join.playerColor;
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
                    if (game.blackUsername() == null) {
                        gameDAO.updateGame(new GameData(gameID, game.whiteUsername(), username, game.gameName(), game.game()));
                        connections.add(user.authToken(), session, gameID);
                        connections.broadcast(user.authToken(), new Notification(user.username() + " is joining the game!"), gameID);
                        connections.respond(user.authToken(), gameID, returnGame);
                    } else {
                        connections.error(session, "Error: already taken");
                    }
                } else if (playerColor == ChessGame.TeamColor.WHITE) {
                    if (game.whiteUsername() == null) {
                        gameDAO.updateGame(new GameData(gameID, username, game.blackUsername(), game.gameName(), game.game()));
                        connections.add(user.authToken(), session, gameID);
                        connections.broadcast(user.authToken(), new Notification(user.username() + " is joining the game!"), gameID);
                        connections.respond(user.authToken(), gameID, returnGame);
                    } else {
                        connections.error(session, "Error: already taken");

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

    private void leaveGame(String Json, Session session) throws IOException {
        var Leave = new Gson().fromJson(Json, LEAVE.class);
        int gameID = Leave.gameID;
        //Print GameID
        //System.out.println(gameID);
        var auth = Leave.getAuthString();
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

    private void resignGame(String Json, Session session) throws IOException {
        var Resign = new Gson().fromJson(Json, RESIGN.class);
        int gameID = Resign.gameID;
        //Print GameID
        //System.out.println(gameID);
        var auth = Resign.getAuthString();
        try {
            AuthData user = authDAO.getAuth(auth);
            GameData returnGame = gameDAO.findGame(gameID);
            if (returnGame == null) {
                connections.error(session, "Error failed to resign game!");
            }
            connections.remove(user.authToken());
            connections.broadcast(user.authToken(), new Notification(user.username() + " has resigned the game!"), gameID);
            connections.respond(user.authToken(), gameID, returnGame);
        } catch (DataAccessException | IOException e) {
            connections.error(session, "Error failed to resign game!");
        }
    }

    private void makeMove(String Json, Session session) throws IOException {
        var Move = new Gson().fromJson(Json, MAKE_MOVE.class);
        int gameID = Move.gameID;
        //Print GameID
        //System.out.println(gameID);
        var auth = Move.getAuthString();
        var move = Move.move;
        try {
            AuthData user = authDAO.getAuth(auth);
            GameData returnGame = gameDAO.findGame(gameID);
            if (returnGame == null || returnGame.game() == null) {
                connections.error(session, "Error failed to make move!");
            } else {
                returnGame.game().makeMove(move);
                connections.MakeMove(user.authToken(), gameID, returnGame.game());
            }
        } catch (DataAccessException | InvalidMoveException | IOException e) {
            connections.error(session, "Error failed to make move!");
        }
    }
}
