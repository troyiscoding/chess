package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.*;
import webSocketMessages.serverMessages.*;
import webSocketMessages.userCommands.*;
import webSocketMessages.userCommands.UserGameCommand;

@WebSocket
public class WebSockets {
    private final ConnectionManager connections = new ConnectionManager();


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
        System.out.println(gameID);
    }

    private void JoinPlayer(String Json, Session session) {
        var Join = new Gson().fromJson(Json, JOIN_PLAYER.class);
        int gameID = Join.gameID;
        //Print GameID
        System.out.println(gameID);
    }
}
