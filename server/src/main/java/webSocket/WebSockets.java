package webSocket;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseAuthDAO;
import handler.JoinRequest;
import model.AuthData;
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

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case JOIN_OBSERVER -> JoinObserver(message, session);
            case JOIN_PLAYER -> JoinPlayer(message, session);
            //case LEAVE -> connections.exit(action.visitorName());
            //case MAKE_MOVE -> connections.makeMove(action.visitorName(), action.getMove());
            //case RESIGN -> connections.resign(action.visitorName());
        }
    }

    private void JoinObserver(String Json, Session session) {
        var JoinObserver = new Gson().fromJson(Json, JOIN_OBSERVER.class);
        int gameID = JoinObserver.gameID;
        //Print GameID
        //System.out.println(gameID);
        var auth = JoinObserver.getAuthString();

        try {
            gameService.joinGame(new JoinRequest(null, gameID), auth);
            AuthData user = authDAO.getAuth(auth);
            connections.respond(user.username(), gameID);
            connections.add(user.username(), session, gameID);
            connections.broadcast(user.username(), new NOTIFICATION(user.username() + " is observing the game!"), gameID);
        } catch (ResponseException | DataAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void JoinPlayer(String Json, Session session) {
        var Join = new Gson().fromJson(Json, JOIN_PLAYER.class);
        int gameID = Join.gameID;
        //Print GameID
        System.out.println(gameID);
    }
}
