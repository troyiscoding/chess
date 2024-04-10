package webSocket;

import com.google.gson.Gson;
import service.ResponseException;
import webSocketMessages.serverMessages.NOTIFICATION;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JOIN_OBSERVER;
import webSocketMessages.userCommands.JOIN_PLAYER;
import webSocketMessages.userCommands.UserGameCommand;


import javax.websocket.*;
import java.io.IOException;
import java.net.URI;


public class WebSocketFacade extends Endpoint {
    public javax.websocket.Session session;

    public WebSocketFacade() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                ServerMessage action = new Gson().fromJson(message, ServerMessage.class);
                switch (action.getServerMessageType()) {
                    case NOTIFICATION -> notification(message);
                    case LOAD_GAME -> loadGame(message);
                    //case ERROR ->
                }
            }
        });
    }

    private void notification(String message) {
        System.out.println("Notification received");
        var notification = new Gson().fromJson(message, NOTIFICATION.class);
        System.out.println(notification.getMessage());
        System.out.println("[SIGNED_IN] >>>");
    }

    private void loadGame(String message) {
        System.out.println("Game loaded");

        System.out.println("[GAME_LOADED] >>>");
    }


    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void joinPlayer(JOIN_PLAYER join) throws Exception {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(join));

        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void observePlayer(JOIN_OBSERVER join) throws Exception {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(join));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
