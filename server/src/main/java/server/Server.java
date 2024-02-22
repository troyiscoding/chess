package server;

import com.google.gson.Gson;
import model.UserData;
import service.ChessService;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;

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

    private Object register(Request req, Response res) {
        try {
            var user = new Gson().fromJson(req.body(), UserData.class);
            ChessService service = new ChessService();
            var auth = service.register(user);
            res.type("application/json");
            return new Gson().toJson(auth);
        } catch (Exception e) {
            // Handle exceptions appropriately
            res.status(400); // Example error handling
            return new Gson().toJson(Map.of("error", "Error registering user: " + e.getMessage()));
        }
    }

    private Object login(Request req, Response res) {
        try {
            var user = new Gson().fromJson(req.body(), UserData.class);
            ChessService service = new ChessService();
            var auth = service.login(user);
            res.type("application/json");
            return new Gson().toJson(auth);
        } catch (Exception e) {
            // Handle exceptions appropriately
            res.status(400); // Example error handling
            return new Gson().toJson(Map.of("error", "Error logging in user: " + e.getMessage()));
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

}
