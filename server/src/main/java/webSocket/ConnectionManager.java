package webSocket;


import com.google.gson.Gson;
import model.GameData;
import org.eclipse.jetty.websocket.api.*;
import webSocketMessages.serverMessages.ERROR;
import webSocketMessages.serverMessages.LOAD_GAME;
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

    public void respond(String user, int gameID, GameData data) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (c.user.equals(user) && c.gameID == gameID) {
                    Gson gson = new Gson();
                    System.out.println(data.game());
                    c.send(gson.toJson(new LOAD_GAME(data.game())));
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
        ERROR error = new ERROR(errorMessage);
        String errorJson = gson.toJson(error);
        session.getRemote().sendString(errorJson);
    }
}
