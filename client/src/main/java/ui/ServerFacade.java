package ui;
//import javax.websocket.Endpoint;

import com.google.gson.Gson;
import handler.JoinRequest;
import handler.ListResponse;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public AuthData register(String username, String password, String email) {
        var path = "/user";
        var request = new UserData(username, password, email);
        return this.sendRequest("POST", path, request, null, AuthData.class);
    }

    public AuthData login(String username, String password) {
        var path = "/session";
        var request = Map.of("username", username, "password", password);
        return this.sendRequest("POST", path, request, null, AuthData.class);
    }

    public void logout(String AuthToken) {
        var path = "/session";
        this.sendRequest("DELETE", path, null, AuthToken, null);
    }

    public void clearData() {
        var path = "/db";
        this.sendRequest("DELETE", path, null, null, null);
    }

    public GameData createGame(String AuthToken, String gameName) {
        var path = "/game";
        var request = new GameData(0, null, null, gameName, new chess.ChessGame());
        return this.sendRequest("POST", path, request, AuthToken, GameData.class);
    }

    public ListResponse listGames(String AuthToken) {
        var path = "/game";
        return this.sendRequest("GET", path, null, AuthToken, ListResponse.class);
    }

    public void joinGame(String AuthToken, JoinRequest request) {
        var path = "/game";
        this.sendRequest("PUT", path, request, AuthToken, null);
    }


    private <T> T sendRequest(String method, String path, Object req, String header, Class<T> format) {
        try {
            URI url = new URI(serverUrl + path);
            HttpURLConnection http = (HttpURLConnection) url.toURL().openConnection();
            http.setRequestMethod(method);
            if (header != null) {
                http.setRequestProperty("Authorization", header);
            }
            writeRequestBody(req, http);
            http.connect();
            throwFailure(http);
            return readBody(http, format);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void writeRequestBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.setDoOutput(true);
            try (var outputStream = http.getOutputStream()) {
                outputStream.write(new Gson().toJson(request).getBytes());
            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> format) throws IOException {
        T res = null;
        if (http.getContentLength() < 0) {
            try (InputStream body = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(body);
                if (format != null) {
                    res = new Gson().fromJson(reader, format);
                }
            }
        }
        return res;
    }

    private static void throwFailure(HttpURLConnection http) throws IOException {
        if (http.getResponseCode() >= 400) {
            try (InputStream errorStream = http.getErrorStream()) {
                var reader = new InputStreamReader(errorStream);
                var error = new Gson().fromJson(reader, Map.class);
                throw new RuntimeException(error.get("message").toString());
            }
        }
    }

}
