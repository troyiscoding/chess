package service;

import dataAccess.*;
import handler.JoinRequest;
import model.GameData;

import java.util.ArrayList;
import java.util.HashSet;

import handler.ListResponse;

public class GameService {
    //TO SWITCH BACK TO MEMORY do = new MemoryGameDAO();
    private final static GameDAO gameDAO = new DatabaseGameDAO();


    public GameData createGame(GameData game, String authToken) throws ResponseException, DataAccessException {
        if (game == null || authToken == null) {
            throw new ResponseException(400, "{ \"message\": \"Error: bad request\" }");
        }
        if (UserService.validateAuthTokenBoolean(authToken)) {
            if (gameDAO.getGames().contains(game)) {
                throw new DataAccessException("Game already exists");
            }
            game = new GameData(0, null, null, game.gameName(), new chess.ChessGame());
            int gameID = gameDAO.insertGame(game);
            return new GameData(gameID, null, null, game.gameName(), new chess.ChessGame());
        } else {
            throw new ResponseException(401, "{ \"message\": \"Error: unauthorized\" }");
        }
    }

    public void joinGame(JoinRequest request, String authToken) throws ResponseException, DataAccessException {
        if (authToken == null) {
            throw new ResponseException(401, "unauthorized");
        }
        if (request == null) {
            throw new ResponseException(400, "{ \"message\": \"Error: bad request\" }");
        }
        if (gameDAO.findGame(request.gameID()) == null) {
            throw new ResponseException(400, "{ \"message\": \"Error: bad request\" }");
        }
        if (request.gameID() == 0) {
            throw new ResponseException(400, "{ \"message\": \"Error: bad request\" }");
        }
        if (UserService.validateAuthTokenBoolean(authToken)) {
            GameData game = gameDAO.findGame(request.gameID());
            String playerColor = request.playerColor();
            int gameID = request.gameID();
            String username = UserService.validateAuthTokenString(authToken);
            if (playerColor == null || playerColor.isEmpty()) {
                //set spectator possibly in the future
            } else if (playerColor.equals("black") || playerColor.equals("BLACK")) {
                if (game.blackUsername() == null) {
                    gameDAO.updateGame(new GameData(gameID, game.whiteUsername(), username, game.gameName(), game.game()));
                } else {
                    throw new ResponseException(403, "{ \"message\": \"Error: already taken\" }");
                }
            } else if (playerColor.equals("white") || playerColor.equals("WHITE")) {
                if (game.whiteUsername() == null) {
                    gameDAO.updateGame(new GameData(gameID, username, game.blackUsername(), game.gameName(), game.game()));
                } else {
                    throw new ResponseException(403, "{ \"message\": \"Error: already taken\" }");
                }
            } else {
                throw new ResponseException(403, "{ \"message\": \"Error: already taken\" }");
            }
        } else {
            throw new ResponseException(401, "{ \"message\": \"Error: unauthorized\" }");
        }
    }

    public ArrayList<ListResponse> listGames(String authToken) throws ResponseException, DataAccessException {
        if (authToken == null) {
            throw new ResponseException(401, "Error: unauthorized");
        }
        if (UserService.validateAuthTokenBoolean(authToken)) {
            ArrayList<ListResponse> returnGame = new ArrayList<>();
            HashSet<GameData> games = gameDAO.getGames();
            if (games != null) {
                for (GameData game : games) {
                    returnGame.add(new ListResponse(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName()));
                }
            }
            return returnGame;
        } else {
            throw new ResponseException(401, "{ \"message\": \"Error: unauthorized\" }");
        }
    }

    public void clear() throws DataAccessException {
        gameDAO.clear();
    }

}
