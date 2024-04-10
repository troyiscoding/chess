package webSocket;

import org.eclipse.jetty.websocket.api.*;

import java.io.IOException;

public class Connection {
    public String user;
    public Session session;
    public int gameID;

    public Connection(String user, Session session, int gameID) {
        this.user = user;
        this.session = session;
        this.gameID = gameID;
    }

    public void send(String message) throws IOException {
        session.getRemote().sendString(message);
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
