package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.GameData;
import model.UserData;
import service.GameService;
import service.ResponseException;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Server {
    private final UserService userService;
    private final GameService gameService;

    public Server() {
        this.userService = new UserService();
        this.gameService = new GameService();
    }

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
        try {
            userService.clear();
            res.status(200);
            return "";
        } catch (DataAccessException e) {
            res.status(500);
            return "{ \"message\": \"Error: Internal Server Error\" }";
        }
    }

    private Object register(Request req, Response res) throws ResponseException {
        try {
            var user = new Gson().fromJson(req.body(), UserData.class);
            var auth = userService.register(user);
            res.status(200); // HTTP 200 OK
            res.type("application/json");
            res.body(new Gson().toJson(auth));
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
            var auth = userService.login(user);
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

    private Object logout(Request req, Response res) throws ResponseException, DataAccessException {
        try {
            //UserService service = new UserService();
            String authString = req.headers("Authorization");
            userService.logout(authString);
            res.status(200); // HTTP 200 OK
            return "";

        } catch (ResponseException e) {
            res.status(e.StatusCode());
            return e.getMessage();
        } catch (DataAccessException e) {
            res.status(500);
            return "{ \"message\": \"Error: Internal Server Error\" }";
        }
    }

    private Object listGame(Request req, Response res) {


        return "";
    }

    private Object createGame(Request req, Response res) throws ResponseException, DataAccessException {
        try {
            String authString = req.headers("Authorization");
            var gameData = new Gson().fromJson(req.body(), GameData.class);
            var returnGameData = gameService.createGame(gameData, authString);
            res.status(200);
            res.type("application/json");
            res.body(new Gson().toJson(returnGameData));
            return new Gson().toJson(returnGameData);
            //LIKE WILL THAT EVEN WORK
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            return e.getMessage();
        } catch (DataAccessException e) {
            res.status(500);
            return "{ \"message\": \"Error: Internal Server Error\" }";
        }
    }

    private Object joinGame(Request req, Response res) {
        try {
            String authString = req.headers("Authorization");
            int gameID = Integer.parseInt(req.params(":id"));
            gameService.joinGame(gameID, authString);
            res.status(200);
            return "";
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            return e.getMessage();
        }
    }

    private void exceptionHandler(ResponseException ex, Request req, Response res) {
        res.status(ex.StatusCode());
    }


}
