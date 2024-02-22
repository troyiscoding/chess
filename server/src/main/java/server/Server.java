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

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
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

}
