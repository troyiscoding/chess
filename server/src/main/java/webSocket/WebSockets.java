package webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.*;
import handler.JoinRequest;
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
            //case MAKE_MOVE -> connections.makeMove(action.visitorName(), action.getMove());
            //case RESIGN -> connections.resign(action.visitorName());
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

            System.out.println(returnGame.game());
            connections.add(user.username(), session, gameID);
            connections.broadcast(user.username(), new NOTIFICATION(user.username() + " is observing the game!"), gameID);
            connections.respond(user.username(), gameID, returnGame);
        } catch (DataAccessException | IOException e) {
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
            //System.out.println(returnGame.game());

            AuthData user = authDAO.getAuth(auth);
            if (UserService.validateAuthTokenBoolean(auth)) {
                GameData game = gameDAO.findGame(gameID);
                String username = UserService.validateAuthTokenString(auth);
                if (playerColor == ChessGame.TeamColor.BLACK) {
                    if (game.blackUsername() == null) {
                        gameDAO.updateGame(new GameData(gameID, game.whiteUsername(), username, game.gameName(), game.game()));
                        connections.add(user.username(), session, gameID);
                        connections.broadcast(user.username(), new NOTIFICATION(user.username() + " is joining the game!"), gameID);
                        connections.respond(user.username(), gameID, returnGame);
                    } else {
                        connections.error(session, "Error: already taken");
                    }
                } else if (playerColor == ChessGame.TeamColor.WHITE) {
                    if (game.whiteUsername() == null) {
                        gameDAO.updateGame(new GameData(gameID, username, game.blackUsername(), game.gameName(), game.game()));
                        connections.add(user.username(), session, gameID);
                        connections.broadcast(user.username(), new NOTIFICATION(user.username() + " is joining the game!"), gameID);
                        connections.respond(user.username(), gameID, returnGame);
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
        System.out.println(gameID);
        var auth = Leave.getAuthString();
        try {
            AuthData user = authDAO.getAuth(auth);
            GameData returnGame = gameDAO.findGame(gameID);
            connections.remove(user.username());
            connections.broadcast(user.username(), new NOTIFICATION(user.username() + " has left the game!"), gameID);
            connections.respond(user.username(), gameID, returnGame);
        } catch (DataAccessException | IOException e) {
            connections.error(session, "Error failed to leave game!");
        }
    }
}
