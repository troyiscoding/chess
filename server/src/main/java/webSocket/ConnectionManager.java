package webSocket;


import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import model.GameData;
import org.eclipse.jetty.websocket.api.*;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    //Track authtoken instead
    public void add(String user, Session session, int gameID) {
        var connection = new Connection(user, session, gameID);
        connections.put(user, connection);
    }

    public void remove(String user) {
        connections.remove(user);
    }


    public void broadcast(String user, ServerMessage message, int gameID) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.user.equals(user) && c.gameID == gameID) {
                    Gson gson = new Gson();
                    c.send(gson.toJson(message));
                }
            } else {
                removeList.add(c);
            }
        }
        // Clean up on isle 5 ... I mean, clean up any connections that were left open.
        for (var c : removeList) {
            if (c != null) {
                connections.remove(c.user);
            }
        }

    }

    //Load Game For just the user
    public void respond(String user, int gameID, GameData data) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (c.user.equals(user) && c.gameID == gameID) {
                    Gson gson = new Gson();
                    System.out.println(data.game());
                    c.send(gson.toJson(new LoadGame(data.game())));
                    System.out.println("Sent game data to " + user);
                }
            } else {
                removeList.add(c);
            }
        }
        // Clean up on isle 5 ... I mean, clean up any connections that were left open.
        for (var c : removeList) {
            if (c != null) {
                connections.remove(c.user);
            }
        }
    }

    public void error(Session session, String errorMessage) throws IOException {
        Gson gson = new Gson();
        Error error = new Error(errorMessage);
        String errorJson = gson.toJson(error);
        session.getRemote().sendString(errorJson);
    }

    public void MakeMove(String user, int gameID, ChessGame move) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (c.gameID == gameID) {
                    Gson gson = new Gson();
                    c.send(gson.toJson(new LoadGame(move)));
                }
            } else {
                removeList.add(c);
            }
        }
        // Clean up on isle 5 ... I mean, clean up any connections that were left open.
        for (var c : removeList) {
            if (c != null) {
                connections.remove(c.user);
            }
        }
    }

    public void broadcastResign(String user, ServerMessage message, int gameID) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (c.gameID == gameID) {
                    Gson gson = new Gson();
                    c.send(gson.toJson(message));
                }
            } else {
                removeList.add(c);
            }
        }
        // Clean up on isle 5 ... I mean, clean up any connections that were left open.
        for (var c : removeList) {
            if (c != null) {
                connections.remove(c.user);
            }
        }

    }
}
