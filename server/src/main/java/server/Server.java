package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.UserData;
import service.ChessService;
import service.ResponseException;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        //Needed this to get the web page to load
        Spark.init();
        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/db", this::clear);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGame);
        Spark.post("/game", this::createGame);
        Spark.put("game/:id", this::joinGame);
        Spark.exception(ResponseException.class, this::exceptionHandler);
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clear(Request req, Response res) {
        // Clear the database
        return "";
    }

    private Object register(Request req, Response res) throws ResponseException {
        try {
            var user = new Gson().fromJson(req.body(), UserData.class);
            ChessService service = new ChessService();
            var auth = service.register(user);
            res.status(200); // HTTP 200 OK
            res.type("application/json");
            return new Gson().toJson(auth);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            return e.getMessage();
        } catch (DataAccessException e) {
            res.status(500);
            return "{ \"message\": \"Error: Internal Server Error\" }";
        }
    }

    private Object login(Request req, Response res) throws ResponseException {
        try {
            var user = new Gson().fromJson(req.body(), UserData.class);
            ChessService service = new ChessService();
            var auth = service.login(user);
            res.status(200); // HTTP 200 OK
            res.type("application/json");
            return new Gson().toJson(auth);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            return e.getMessage();
        } catch (DataAccessException e) {
            res.status(500);
            return "{ \"message\": \"Error: Internal Server Error\" }";
        }
    }

    private Object logout(Request req, Response res) {
        // Clear the session
        return "";
    }

    private Object listGame(Request req, Response res) {
        // List all games
        return "";
    }

    private Object createGame(Request req, Response res) {
        // Create a new game
        return "";
    }

    private Object joinGame(Request req, Response res) {
        // Join a game
        return "";
    }

    private void exceptionHandler(ResponseException ex, Request req, Response res) {
        res.status(ex.StatusCode());
    }

}
