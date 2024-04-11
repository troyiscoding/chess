package webSocket;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import ui.DrawBoardNew;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;


import javax.websocket.*;
import java.io.IOException;
import java.net.URI;


public class WebSocketFacade extends Endpoint {
    public Session session;
    public static ChessBoard chessBoard = new ChessBoard();

    public WebSocketFacade(String url) throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                ServerMessage action = new Gson().fromJson(message, ServerMessage.class);
                switch (action.getServerMessageType()) {
                    case NOTIFICATION -> notification(message);
                    case LOAD_GAME -> loadGame(message);
                    case ERROR -> error(message);
                }
            }
        });
    }

    private void notification(String message) {
        System.out.println("Notification received");
        var notification = new Gson().fromJson(message, Notification.class);
        System.out.println(notification.getMessage());
        System.out.println("[IN_GAME] >>>");
    }

    private void loadGame(String message) {
        System.out.println("Game loaded");
        var loadGame = new Gson().fromJson(message, LoadGame.class);
        DrawBoardNew.drawBoardNew(loadGame.game.getBoard(), ChessGame.TeamColor.WHITE);
        chessBoard = loadGame.game.getBoard();
        System.out.print("[SIGNED_IN] >>>");
    }

    private void error(String message) {
        var error = new Gson().fromJson(message, Error.class);
        System.out.println("Error received");
        System.out.println(error.errorMessage);
        System.out.print("[IN_GAME] >>>");
    }


    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void joinPlayer(JoinPlayer join) throws Exception {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(join));

        } catch (IOException ex) {
            System.out.println("Websocket error");
        }
    }

    public void observePlayer(JoinObserver join) throws Exception {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(join));
        } catch (IOException ex) {
            System.out.println("Websocket error");
        }
    }

    public void leaveGame(Leave leave) throws Exception {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(leave));
        } catch (IOException ex) {
            System.out.println("Websocket error");
        }
    }

    public void resignGame(Resign resign) throws Exception {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(resign));
        } catch (IOException ex) {
            System.out.println("Websocket error");
        }
    }

    public void makeMove(MakeMove move) throws Exception {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(move));
        } catch (IOException ex) {
            System.out.println("Websocket error");
        }
    }
}
