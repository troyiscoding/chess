package webSocket;

import org.eclipse.jetty.websocket.api.*;

import java.io.IOException;

public class Connection {
    public String user;
    public Session session;

    public Connection(String user, Session session) {
        this.user = user;
        this.session = session;
    }

    public void send(String message) throws IOException {
        session.getRemote().sendString(message);
    }
}
