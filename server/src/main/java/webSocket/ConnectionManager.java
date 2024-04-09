package webSocket;


import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.*;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String user, Session session) {
        var connection = new Connection(user, session);
        connections.put(user, connection);
    }

    public void remove(String user) {
        connections.remove(user);
    }

    public void broadcast(String user, ServerMessage message) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.user.equals(user)) {
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
